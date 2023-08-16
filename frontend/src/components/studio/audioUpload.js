
import React, { useEffect, useState, useRef } from "react";
// import "./style.css"; // 스타일 파일 경로를 맞게 수정해주세요
import "./Studio.css"

const Studio = () => {
  const [blockCounter, setBlockCounter] = useState(0);  //블록의 개수를 저장하는 상태 변수
  const [playInterval, setPlayInterval] = useState(null);   // 플레이 인터벌 관리하는 상태변수
  const [selectedBlock, setSelectedBlock] = useState(null); //선택된 블록을 저장하는 상태 변수
  const [audioCtx, setAudioCtx] = useState(new (window.AudioContext || window.webkitAudioContext)()); //오디오 컨텍스트를 저장하는 상태변수
  const [enhancerNode, setEnhancerNode] = useState(null);//이퀄라이저 노드를 저장하는 상태변수
  const [playhead, setPlayhead] = useState(null);//플레이 헤드 요소를 저장하는 상태변수
  const [container, setContainer] = useState(null); //컨테이너 요소를 저장하는 상태변수

  useEffect(() => {
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

    return () => {
      if (playInterval) {
        clearInterval(playInterval);
      }
    };
  }, []);

  // 드롭 이벤트 발생 시 실행할 함수-------------------------------------------
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
        const container = document.getElementById("container"); //블록 추가할 컨테이너 가져와서
        setContainer(container);
        const newBlock = document.createElement("div"); //새로운 div생성
        //새로운 블럭 관련 설정--------
        newBlock.id = blockId;
        newBlock.className = "block";
        newBlock.style.backgroundColor = color;
        newBlock.style.width = width + "px";
        newBlock.style.left = ((blockCounter * 100) % (container.clientWidth - 100)) + "px";
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
        } else {
          audio.pause();  //일시정지
        }
      });
  }


  //음악 블록 재생
  //플레이 헤드 이동
  //블록/플레이 헤드의 조건 비교를 통해 음악 제어
  const handlePlay = () => {
    const blocks = document.querySelectorAll(".block");//block이라는 클래스를 가진 모든 HTML요소 저장

    blockPlay(blocks);
    // blocks.forEach((block) => {
    //   const audio = document.getElementById("audio" + block.id);
    //   if (//플레이 헤드가 현재 블록 내 위치 시
    //     block.offsetLeft <= playhead.offsetLeft &&
    //     playhead.offsetLeft <= block.offsetLeft + block.offsetWidth
    //   ) {
    //     //플레이 헤드가 현재 블록 내 어느정도 위치인지 계산
    //     const progress = (playhead.offsetLeft - block.offsetLeft) / block.offsetWidth;
    //     audio.currentTime = audio.duration * progress;
    //     if (audio.paused) { //음악이 일시정지인 경우 해당 음악을 재생
    //       audio.play();
    //     }
    //   } else {
    //     audio.pause();  //일시정지
    //   }
    // });

    playhead.style.display = "block";//플레이 헤드를 보이게 설정
    setPlayInterval(     //아래의 setInterval()함수를 사용하여
      setInterval(() => {//주기적으로 코드 블록을 실행(플레이 헤드 움직이는 역할)
        setPlayhead((prevPlayhead) => { //플레이 헤드를 한 픽셀씩 오른쪽으로 이동시키는 역할
          prevPlayhead.style.left = parseInt(prevPlayhead.style.left, 10) + 1 + "px";
          return prevPlayhead;
        });
        blockPlay(blocks);
        // blocks.forEach((block) => { //각 음원블록에 대해 실행할 코드
        //   const audio = document.getElementById("audio" + block.id);
        //   if (  //현재 플레이 헤드 위치가 음악블록 범위 내에 있는지 확인
        //         block.offsetLeft <= playhead.offsetLeft &&
        //         playhead.offsetLeft <= block.offsetLeft + block.offsetWidth
        //     ) {
        //         if (audio.paused) {// 이부분 수정 필요>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //             const progress = (playhead.offsetLeft - block.offsetLeft) / block.offsetWidth;
        //             audio.currentTime = audio.duration * progress;
        //             audio.play();
        //         }
        //   } else {
        //     audio.pause();//오디오 일시정지
        //   }
        // });

        // 플레이 헤드 위치를 벗어난 경우
        if (playhead.offsetLeft > container.clientWidth) {
          clearInterval(playInterval);
          playhead.style.display = "none";
          playhead.style.left = "0";
          blocks.forEach((block) => {
            document.getElementById("audio" + block.id).pause();
          });
        }
      }, 100)
    );
  };

  const handleEnhancer = () => {
    if (selectedBlock) {
      const audio = document.getElementById("audio" + selectedBlock.id);
      const source = audioCtx.createMediaElementSource(audio);
      source.connect(enhancerNode);
      enhancerNode.connect(audioCtx.destination);
      selectedBlock.classList.add("enhanced");
    }
  };

  const handleRemove = () => {
    if (selectedBlock) {
      const audio = document.getElementById("audio" + selectedBlock.id);
      audio.pause();
      selectedBlock.remove();
    }
  };

  return (
    <div className="container">
      <h1 className="text-center">Simple Web DAW</h1>
      <button id="playButton" className="btn btn-primary" onClick={handlePlay}>
        Play
      </button>
      <button id="enhancerButton" className="btn btn-danger" onClick={handleEnhancer}>
        Vocal Enhancer
      </button>
      <button id="removeButton" className="btn btn-warning" onClick={handleRemove}>
        Remove Block
      </button>
      <div id="container" className="mt-5" onDrop={handleDrop} onDragOver={(e) => e.preventDefault()}>
        <div id="playhead"></div>
        {/* Blocks will be dynamically created here */}
      </div>
    </div>
  );
};

export default Studio;