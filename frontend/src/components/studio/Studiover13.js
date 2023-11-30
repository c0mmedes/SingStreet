import React, { useEffect, useState } from "react";
import * as Y from "yjs";
import { WebsocketProvider } from "y-websocket";
import "./Studio.css";
import { api } from "../../services/httpService";

const Studio = () => {
  const [blockCounter, setBlockCounter] = useState(1);
  const [playInterval, setPlayInterval] = useState(null);
  const [selectedBlock, setSelectedBlock] = useState(null);
  const [audioCtx, setAudioCtx] = useState(
    new (window.AudioContext || window.webkitAudioContext)()
  );
  const [enhancerNode, setEnhancerNode] = useState(null);
  const [playhead, setPlayhead] = useState(null);
  const [container, setContainer] = useState(
    document.getElementById("editArea")
  ); //컨테이너 요소를 저장하는 상태변수
  const [blockListArea, setBlockListArea] = useState(
    document.getElementById("blockListArea")
  );
  const [blockList, setBlockList] = useState([]);
  const [provider, setProvider] = useState(null);
  // const container = document.getElementById("editArea");
  // const blockListArea = document.getElementById("blockListArea");
  const ydoc = new Y.Doc();
  // const provider = new WebsocketProvider('ws://localhost:8080/chatt/', 1 , ydoc);

  const apiInstance = api();
  // const [audioBlock, setAudioBlock] = useState(null); //업로드 오디오 파일
  const [userId, setUserId] = useState("1");
  const [projectId, setProjectId] = useState(1);
  const [blockId, setBlockId] = useState(null);
  const myMap = ydoc.getMap("myMap");
  const listMap = ydoc.getMap("listMap");

  //초기설정 -
  useEffect(() => {
    const init = async () => {
      const provider = new WebsocketProvider('wss://i9b110.p.ssafy.io/ws/chatt',1,ydoc);
      setProvider(provider);
      //초기설정
      const newEnhancerNode = audioCtx.createBiquadFilter(); //audio컨텍스트에서 이퀄라이저 노드를 생성
      newEnhancerNode.type = "highshelf"; //이퀄라이저 타입 :highshelf:고주파 영역을 강화시켜주는 타입
      newEnhancerNode.frequency.value = 1000; //주파수 값을 1000으로 설정
      newEnhancerNode.gain.value = 25; //이퀄라이저 강도를 25로 설정
      setEnhancerNode(newEnhancerNode);

      const container = document.getElementById("editArea");
      setContainer(container);

      const blockListArea = document.getElementById("blockListArea");
      setBlockListArea(blockListArea);

      const newPlayhead = document.getElementById("playhead");
      newPlayhead.draggable = true;
      newPlayhead.style.position = "absolute";
    //   newPlayhead.style.left = `${container.offsetLeft}px`;
        newPlayhead.style.left = `0px`;
        newPlayhead.style.height="100%";
      setPlayhead(newPlayhead);

      console.log("first UseEffect",myMap);
    };

    const loadAndCreateAudioBlocks = async() =>{
      console.log(blockList);
      if(blockList.length == 0){
        console.log("hi");
        await getBlockList();//전체 음원가져와서 바로 AudioBlock생성까지 해준다.
        console.log(blockList);
      }
    }

    loadAndCreateAudioBlocks();
    init();
    return () => {
      if (playInterval) {
        clearInterval(playInterval);
      }
    };
  }, []);



  //음원 블럭들 AudioBlock으로 만들기
  useEffect(() =>{
    //가져온 음원 블럭들을 AudioBlock으로 만들어주기
    if(blockList){
      blockList.forEach(block => {
        createAudioFile(block);
        // setblock(AudioFile);
        // setblock(new Audio(block.file_location));
      });
    }
   //2-1. ------------------------블럭 초기화 end----------------------------------
  },[blockList]);


  // 2.================================================Yjs관련 초기 설정=================================================================
  useEffect(() => {
    if(blockId){
      console.log(blockId);
      
      const updateBlockList = async() =>{
        let block = await getBlock(blockId);
        console.log(block);
        if(blockList){
          const prevBlockList = [...blockList, block.data];
          setBlockList(prevBlockList); // 기존 배열에 새로운 블록 추가
          console.log(blockList);
        }else{
          setBlockList([block.data]);
        }
        
      }
      updateBlockList();
    }

  }, [blockId]);
  //2.end=============================================Yjs초기설정end=========================================================================


      // 2-3. --------------------------<YMap업데이트 화면공유>--------------------
      myMap.observe(() => {//myMap의 내용이 변경될 때마다 실행
        console.log("myMapObserve");
        console.log(myMap);
        myMap.forEach((block, testId) => {    //MyMap의 모든 항목에 대해 반복, block:현재 순회중인 항목의 값/ id:항목의 고유 식별자
          if(testId){
              console.log("[observe] - block,id",block, testId);
              const blockElement = document.getElementById(testId);
              console.log("[observe]-blockElement",blockElement);
              if (blockElement) {
              setLocation(blockElement, block.left, block.top);
              } else {
                  createAudioFile();
              }
          }
        });
      }); // 2-3. end--------------------------------------------------------------








  //2-1. -----------------------------block초기설정-----------------------------
  const setblock = (insertBlock) => {
    console.log("------setBlock------");
    console.log("1[setBlock]-insertBlock",insertBlock);
    console.log(myMap);
    console.log(insertBlock);
    let block = myMap.get(insertBlock.testId);
    // console.log("2. insertBlock",block);

    //블록이 없다면 yMap에 (ID, 해당Block)업데이트
    console.log(block);
    if (!block) {
      myMap.set(insertBlock.testId, insertBlock); // -> yMap.oberve()실행됨
    }
    console.log("[useEffect]-[setBlock]-block",block);
    console.log("[useEffect]-[setBlock]-insertBlock",insertBlock);
    console.log("[useEffect]-[setBlock]-myMap.block",myMap.get(insertBlock.testId));
    // console.log("3. block:",insertBlock);

    //2-1-2. ------------해당 블럭을 찾아서 위치 조정 및 이벤트 추가-------------------
    const blockElement = document.getElementById(insertBlock.testId);
    console.log(blockElement);
    blockElement.setAttribute("draggable", "true");
    setLocation(blockElement, insertBlock.left, insertBlock.top);
    // 드래그 관련 함수
    const dragStartHandler = (e) => {
      const offsetLeft = e.clientX - e.target.offsetLeft;
      const offsetTop = e.clientY - e.target.offsetTop;

      // 드래그 끝나면 0,0으로 돌아가는 Bug로 인해 임시방편
      const dragHandler = (event) => {
        if (event.clientX == 0 && event.clientY == 0) {
          return; //리팩토링 필요-----------------
        }
        const left = event.clientX - offsetLeft;
        const top = event.clientY - offsetTop;

        setLocation(blockElement, left, top);
        console.log("[dragHandler] - blockElement",blockElement);
        console.log("insertBlock.id",insertBlock.testId);
        myMap.set(insertBlock.testId, 
          {
              left: left,
              top: top,
              testId: insertBlock.testId,
              blockName: insertBlock.blockName, 
              projectId:projectId
          });
        console.log(myMap.get(insertBlock.id));
        console.log(left, top);
        console.log(myMap);

      };
      blockElement.ondrag = dragHandler; // 해당
    };
    blockElement.ondragstart = dragStartHandler;
  };










  //---------------------------------------------
  //                  관련함수
  //---------------------------------------------
  // -----------------------------------New Block Insert-----------------------------------------------
  // 드롭 이벤트 발생 시 실행할 함수
  const handleDrop = (e) => {
    e.preventDefault(); //기본동작 막기. 파일을 브라우저의 기본 동작대로 열지 않도록 설정

    const file = e.dataTransfer.files[0]; // 드롭된 파일 중 첫번째 파일 가져오기

    //이미 있는 block이면 종료
    if (!file) {
      console.log("[handleDrop]-file (if)",file);
    } else {
      console.log("[handleDrop]-new file (else)",file);
      // setAudioBlock(file);
      addBlock(file); //파일 업로드
      // createAudioFile(file);
    }
  };


  //block 위치 조정-----------------------------------------------------------------------------
  const setLocation = (element, left, top) => {//div이동
    element.style.left = `${left}px`;
    element.style.top = `${top}px`;
  };

  //Audio파일 셋팅=====================================
  const createAudioFile = (file) => {
      console.log(file)
        const blockId = blockCounter;
        const audioElement = new Audio(file.file_location);
        audioElement.id = "audio" + file.testId;
      
        audioElement.addEventListener("loadedmetadata", () => {
          const duration = audioElement.duration;
          document.body.appendChild(audioElement);
          const color = "#" + Math.floor(Math.random() * 16777215).toString(16);
          const newBlock = document.createElement("div");
          newBlock.id = file.testId;
          newBlock.className = "block";
          newBlock.style.backgroundColor = color;
          newBlock.style.width = duration + "px";
          newBlock.style.height = "50px";
          newBlock.style.display = "inline-block";
          newBlock.style.position = "absolute";
          newBlock.style.textAlign = "center";
          newBlock.style.lineHeight = "50px";
          newBlock.style.fontSize = "10px";
          newBlock.style.left =
            ((blockCounter * 100) % (container.clientWidth - 100)) + "px";
          newBlock.style.top =
            Math.floor(blockCounter / (container.clientWidth / 100)) * 100 + "px";
          newBlock.innerHTML = file.blockName;
          container.appendChild(newBlock);
      
          newBlock.addEventListener("click", () => {
            setSelectedBlock(newBlock);
          });
          console.log(newBlock);
          setblock(file);
        });
  };
  //==================================================

  //플레이 헤드와 블록에 위치에 따라 플레이 설정
  const blockPlay = (blocks) => {
    blocks.forEach((block) => {
    //   console.log("block", block.id);
      const audio = document.getElementById("audio" + block.id); //블럭과 id같은 audio불러오기
    //   console.log("audio", audio);
      if (
        //플레이 헤드가 현재 블록 내 위치 시
        block.offsetLeft <= playhead.offsetLeft &&
        playhead.offsetLeft <= block.offsetLeft + block.offsetWidth
      ) {
        //플레이 헤드가 현재 블록 내 어느정도 위치인지 계산
        const progress =
          (playhead.offsetLeft - block.offsetLeft) / block.offsetWidth;
        audio.currentTime = audio.duration * progress;
        if (audio.paused) {
          //음악이 일시정지인 경우 해당 음악을 재생
          audio.play();
          console.log("play");
        }
      } else {
        audio.pause(); //일시정지
      }
    });
  };

  //음악 재생
  const handlePlay = () => {
    const blocks = document.querySelectorAll(".block"); //block이라는 클래스를 가진 모든 HTML요소 저장
    handleStop();
    blockPlay(blocks);
    console.log(blocks);

    playhead.style.display = "block"; //플레이 헤드를 보이게 설정
    setPlayInterval(
      //아래의 setInterval()함수를 사용하여
      setInterval(() => {
        //주기적으로 코드 블록을 실행(플레이 헤드 움직이는 역할)
        setPlayhead((prevPlayhead) => {
          //플레이 헤드를 한 픽셀씩 오른쪽으로 이동시키는 역할
          prevPlayhead.style.left =
            parseInt(prevPlayhead.style.left, 10) + 1 + "px";
          return prevPlayhead;
        });
        blockPlay(blocks);

        // 플레이 헤드가 편집위치를 벗어난 경우
        if (playhead.offsetLeft > container.clientWidth) {
          handleStop();
        }
        console.log("interval");
      }, 1000)
    );
  };

  //음악 정지
  const handleStop = () => {
    const blocks = document.querySelectorAll(".block"); //block이라는 클래스를 가진 모든 HTML요소 저장
    // console.log(blocks);
    clearInterval(playInterval);
    playhead.style.display = "none";
    // playhead.style.left = `${container.offsetLeft}px`;
    playhead.style.left = `0px`;
    blocks.forEach((block) => {
      //해당 block과 아이디가 같으면 정지
      const audio = document.getElementById("audio" + block.id);
      console.log(audio)
      audio.pause();
      console.log(block.id);
    });
  };

  //Audio파일 서버에 추가----->new Audio Insert에서 실행---------------------------------------------
  const addBlock = async (file) => {
    const accessToken = sessionStorage.getItem("accessToken");
    const formData = new FormData();
    formData.append("file", file); //오디오 파일 넣기
    const block = {
      testId: blockCounter,
      left: 100,
      top: 100,
      blockName: file.name,
      projectId: projectId,
    };
    setBlockCounter(blockCounter+1);

    formData.append(
      "AudioBlockRequestDTO",
      new Blob([JSON.stringify(block)], {
        type: "application/json",
      })
    );

    const res = await apiInstance.post("/block/add", formData, {
      headers: {
        Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
        "Content-Type": "multipart/form-data",
        Accept: "application/json", // 추가
      },
    });
    setBlockList(res.data.content);
    setBlockId(res.data);
  };

  //저장된 음원전체 가져오기-> 
  const getBlockList = async () => {
    console.log(projectId)
    const res = await apiInstance.get(`/block/${projectId}`);
    console.log(res.data);
    setBlockList(res.data);
  };

  //저장된 음원 가져오기
  const getBlock = async (blockId) => {
    const res = await apiInstance.get(`/block/read/${blockId}`);
    console.log("res ",res)
    return res;
  }

  //새로 저장된 음원들 가져오기
  const getNewBlockList = async () => {};

  const update = async(block) =>{

  }


  return (
    <div className="studioContainer">
      <div
        className="studioArea"
        onDrop={handleDrop}
        onDragOver={(e) => e.preventDefault()}>
        <div className="inner">
          <div className="editWrap">
            {/* 블록올리는 곳 */}
            <div id="editArea" className="editArea">
              <div id="playhead"></div>
              {/* 버튼들 */}
              <div className="buttonArea">
                <button id="play" onClick={handlePlay} className="w-btn-neon2">
                  <ion-icon name="play-outline"></ion-icon>
                </button>
                <button id="stop" onClick={handleStop} className="w-btn-neon2">
                  stop!
                </button>
                <button className="w-btn-neon2">음향효과2</button>
                <button onClick={update} className="w-btn-neon2">SAVE</button>
              </div>
            </div>
            <div id="blockListArea" className="blockListArea"></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Studio;

//화면 공유 안됨
