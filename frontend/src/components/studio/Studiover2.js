import React, { useEffect, useState } from 'react';
import * as Y from 'yjs';
import { WebsocketProvider } from 'y-websocket';
import "./Studio.css"
import { api } from "../../services/httpService"

const Studio = () => {
    const [blockCounter, setBlockCounter] = useState(0);
    const [playInterval, setPlayInterval] = useState(null);
    const [selectedBlock, setSelectedBlock] = useState(null);
    const [audioCtx, setAudioCtx] = useState(new (window.AudioContext || window.webkitAudioContext)());
    const [enhancerNode, setEnhancerNode] = useState(null);
    const [playhead, setPlayhead] = useState(null);
    const [container, setContainer] = useState(null); //컨테이너 요소를 저장하는 상태변수
    const [projectId, setProjectId] = useState("1");
    const [blockList, setBlockList] = useState(null);
    const apiInstance = api();

  useEffect(()=>{
    const init = async ()=> {
        //초기설정
        const newEnhancerNode = audioCtx.createBiquadFilter();//audio컨텍스트에서 이퀄라이저 노드를 생성
        newEnhancerNode.type = "highshelf"; //이퀄라이저 타입 :highshelf:고주파 영역을 강화시켜주는 타입
        newEnhancerNode.frequency.value = 1000; //주파수 값을 1000으로 설정
        newEnhancerNode.gain.value = 25;    //이퀄라이저 강도를 25로 설정
        setEnhancerNode(newEnhancerNode);

        const newPlayhead = document.getElementById("playhead");
        newPlayhead.draggable = true;
        newPlayhead.style.position = "absolute";
        newPlayhead.style.left = "0";
        setPlayhead(newPlayhead);

        //yjs관련 초기 설정
        const ydoc = new Y.Doc();
        const provider = new WebsocketProvider('ws://localhost:8080/chatt/', 1 , ydoc);
        const myMap = ydoc.getMap('myMap');

        const setblock = (id) => {
            let block = myMap.get(id);

            if(!block){
                block = {
                    blockId:1,
                    left : 10,
                    top: 10,
                }
            myMap.set(id, block)
            }
      
        //--------------해당 블럭을 찾아서 위치 조정 및 이벤트 추가--------------------
        const blockElement = document.getElementById(id);
        setLocation(blockElement, block.left, block.top);
        const dragStartHandler = e =>{
            const offsetLeft = e.clientX - e.target.offsetLeft;
            const offsetTop = e.clientY - e.target.offsetTop;

            const dragHandler = event => {
                if(event.clientX == 0 && event.clientY == 0){
                    return;//리팩토링 필요-----------------
                }
            const left = event.clientX - offsetLeft;
            const top = event.clientY - offsetTop;
          
            setLocation(blockElement, left, top);
            myMap.set(id, {left:left, top:top});
        }
        blockElement.ondrag = dragHandler;
      }
      blockElement.ondragstart = dragStartHandler;
      };//------------------블럭 초기화 end-----------------------------------------

    setblock("item1");
    setblock("item2");

    myMap.observe(()=>{
        myMap.forEach((block, id) =>{
            const blockElement = document.getElementById(id);
            if(blockElement){
                setLocation(blockElement, block.left, block.top);
            }
        })
      })
    }
    init();
    return () => {
        if (playInterval) {
          clearInterval(playInterval);
        }
      };
  },[]);

  // 드롭 이벤트 발생 시 실행할 함수
  const handleDrop = (e) => {
    e.preventDefault(); //기본동작 막기. 파일을 브라우저의 기본 동작대로 열지 않도록 설정
    const file = e.dataTransfer.files[0];   // 드롭된 파일 중 첫번째 파일 가져오기
    const reader = new FileReader();    // 파일을 읽기 위한 FileReader객체 생성

    //파일 성공적으로 읽으면 실행할 콜백 함수 정의
    reader.onload = (e) => {    
        const blockId = "block" + blockCounter;
        const audioElement = new Audio();
        audioElement.src = e.target.result;   // FileReader로 읽은 
        audioElement.id = "audio" + blockId;  // 블럭의 고유ID값 할당

        //오디오의 메타데이터 로드 완료시 실행되는 이벤트 리스너 추가
        audioElement.addEventListener("loadedmetadata", () => {
            const duration = audioElement.duration;     //오디오의 총 재생 시간
            const width = duration;                     //
            document.body.appendChild(audioElement);
            const color = "#" + Math.floor(Math.random() * 16777215).toString(16);  //랜덤한 색상 코드를 생성
            const container = document.getElementById("blockListArea"); //블록 추가할 컨테이너 가져와서
            setContainer(container);
            const newBlock = document.createElement("div"); //새로운 div생성
            //새로운 블럭 관련 설정--------
            newBlock.id = blockId;
            newBlock.className = "block";
            newBlock.style.backgroundColor = color;
            newBlock.style.width = width*1 + "px";
            //ondrag속성 지정해줘야함<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            
            console.log(width);
            newBlock.style.height = "50px";
            // newBlock.style.display = "inline-block";
            // newBlock.style.left = ((blockCounter * 100) % (container.clientWidth - 100)) + "px";
            newBlock.style.top = Math.floor(blockCounter / (container.clientWidth / 100)) * 100 + "px";
            newBlock.contentEditable = "true";  //블록의 내용을 편집 가능하도록 설정
            newBlock.innerHTML = "Track " + blockCounter;   //블록의 내용을 설정
            container.appendChild(newBlock);    //새로운 블록을 컨테이너에 추가

            newBlock.addEventListener("click", () => {
                setSelectedBlock(newBlock);
            });
        setBlockCounter(blockCounter + 1);//블록 카운터 수 증가
        });
    };
    reader.readAsDataURL(file);
};

//플레이 헤드와 블록에 위치에 따라 플레이 설정
const blockPlay = (blocks) =>{
    blocks.forEach((block) => {
        const audio = document.getElementById("audio" + block.id);
        if (//플레이 헤드가 현재 블록 내 위치 시
            block.offsetLeft <= playhead.offsetLeft &&
            playhead.offsetLeft <= block.offsetLeft + block.offsetWidth
        ) {
            //플레이 헤드가 현재 블록 내 어느정도 위치인지 계산
            const progress = (playhead.offsetLeft - block.offsetLeft) / block.offsetWidth;
            audio.currentTime = audio.duration * progress;
            if (audio.paused) { //음악이 일시정지인 경우 해당 음악을 재생
                audio.play();
            }
        }else {
      audio.pause();  //일시정지
    }
  });
};


//음악 블록 재생
//플레이 헤드 이동
//블록/플레이 헤드의 조건 비교를 통해 음악 제어
const handlePlay = () => {
    const blocks = document.querySelectorAll(".block");//block이라는 클래스를 가진 모든 HTML요소 저장
    blockPlay(blocks);

    playhead.style.display = "block";//플레이 헤드를 보이게 설정
    setPlayInterval(     //아래의 setInterval()함수를 사용하여
        setInterval(() => {//주기적으로 코드 블록을 실행(플레이 헤드 움직이는 역할)
            setPlayhead((prevPlayhead) => { //플레이 헤드를 한 픽셀씩 오른쪽으로 이동시키는 역할
                prevPlayhead.style.left = parseInt(prevPlayhead.style.left, 10) + 1 + "px";
                return prevPlayhead;
    });
    blockPlay(blocks);

    // 플레이 헤드가 편집위치를 벗어난 경우
    // if (playhead.offsetLeft > container.clientWidth) {
    //     clearInterval(playInterval);
    //     playhead.style.display = "none";
    //     playhead.style.left = "0";
    //     blocks.forEach((block) => {
    //     document.getElementById("audio" + block.id).pause();
    //     });
    // }
    }, 100));
};


  //저장된 음원들 가져오기-> back에서 구현
//   const getBlockList = async() => {
//     const res = await apiInstance.get(`/block/${projectId}`);
//     setBlockList(res.data.content);
//     console.log(res.data);
//   }


  //block 위치 조정---------------------------------------
  const setLocation = (element,left, top) =>{
    element.style.left = `${left}px`;
    element.style.top = `${top}px`;
  }



  return (
    <div className='studioArea' onDrop={handleDrop} onDragOver={(e) => e.preventDefault()}>
        <div className='inner'>
            <div className='buttonArea'>
                <button id='play' onClick={handlePlay} className='w-btn-neon2'>play!</button>
                <button className='w-btn-neon2'>음향효과1</button>
                <button className='w-btn-neon2'>음향효과2</button>
            </div>
            <div className='editArea'>
                <div id='playhead'></div>

            </div>
            <div id='blockListArea' className='blockListArea'>

            </div>
        </div>
        
      <div id="item1" className="draggable"></div>
      <div id="item2" className="draggable"></div>
    </div>
  );
};

export default Studio;