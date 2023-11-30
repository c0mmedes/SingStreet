import React, { useEffect, useState } from "react";
import * as Y from "yjs";
import { WebsocketProvider } from "y-websocket";
import "./Studio.css";
import { api } from "../../services/httpService";

const Studio = () => {
  const [blockCounter, setBlockCounter] = useState(0);
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
  const [blockList, setBlockList] = useState(null);
  const [provider, setProvider] = useState(null);
  // const container = document.getElementById("editArea");
  // const blockListArea = document.getElementById("blockListArea");
  const ydoc = new Y.Doc();
  // const provider = new WebsocketProvider('ws://localhost:8080/chatt/', 1 , ydoc);

  const apiInstance = api();
  // const [audioBlock, setAudioBlock] = useState(null); //업로드 오디오 파일
  const [userId, setUserId] = useState("1");
  const [projectId, setProjectId] = useState("1");

  //초기설정 -
  useEffect(() => {
    const init = async () => {
      const provider = new WebsocketProvider("ws://localhost:8080/chatt",1,ydoc);
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

      // getBlockList(); -> 블럭 가져오기(서버에서도 음원 가져와)
    };
    init();
    return () => {
      if (playInterval) {
        clearInterval(playInterval);
      }
    };
  }, []);


  
  // 2.================================================Yjs관련 초기 설정=================================================================
  useEffect(() => {
    const myMap = ydoc.getMap("myMap");

    //2-1. -----------------------------block초기설정-----------------------------
    const setblock = (insertBlock) => {
      console.log("------setBlock------");
      // console.log("1:",insertBlock);
      let block = myMap.get(insertBlock.id);
      // console.log("2. insertBlock",block);

      //블록이 없다면 yMap에 (ID, 해당Block)업데이트
      if (!block) {
        myMap.set(insertBlock.id, insertBlock); // -> yMap.oberve()실행됨
      }
      // console.log("3. block:",insertBlock);

      //2-1-2. ------------해당 블럭을 찾아서 위치 조정 및 이벤트 추가-------------------
      const blockElement = document.getElementById(insertBlock.id);
      blockElement.setAttribute("draggable", "true");
      setLocation(blockElement, insertBlock.left, insertBlock.top);
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
          myMap.set(insertBlock.id, { left: left, top: top });
        };
        blockElement.ondrag = dragHandler; // 해당
      };
      blockElement.ondragstart = dragStartHandler;
    }; //2-1. ------------------------블럭 초기화 end----------------------------------

    // 2-2. -------------------------<blockList업데이트>--------------------
    console.log("container", container);
    // const block = {//----------------------------------------------------------------------------------------
    //     id : blockCounter,
    //     projectId : 1,//------------------->{projectId}이런식으로 수정필요
    //     left : ((blockCounter * 100) % (container.clientWidth - 100)),
    //     top : Math.floor(blockCounter / (container.clientWidth / 100)) * 100
    // }
    // if(blockList){
    //     const prevBlockList = [...blockList, block];
    //     console.log("if",prevBlockList);
    //     setBlockList(prevBlockList); // 기존 배열에 새로운 블록 추가
    // }else{
    //     setBlockList([block]);
    //     console.log("else",blockList);
    // }
    // setBlockCounter(blockCounter + 1);//블록 카운터 수 증가----------------------------------------------------------

    console.log("blockList:", blockList);
    if (!blockList) {
      //처음에 null이면 실행 -> api통해 전체 블럭리스트 가져오기
    } else {
      console.log("null아니면", blockList[blockList.length - 1]);
      setblock(blockList[blockList.length - 1]);
    } // 2-2.end -------------------------------------------------------------

    // 2-3. --------------------------<YMap업데이트 화면공유>--------------------
    myMap.observe(() => {
      console.log("myMapObserve");
      myMap.forEach((block, id) => {
        // console.log(block, id);
        const blockElement = document.getElementById(id);
        // console.log(blockElement);
        if (blockElement) {
          setLocation(blockElement, block.left, block.top);
        } else {
          createAudioFile();
        }
      });
    }); // 2-3. end--------------------------------------------------------------
  }, [blockList]);
  //2.end=============================================Yjs초기설정end=========================================================================

  //---------------------------------------------
  //                  관련함수
  //---------------------------------------------
  // -----------------------------------New Block Insert-----------------------------------------------
  // 드롭 이벤트 발생 시 실행할 함수
  const handleDrop = (e) => {
    e.preventDefault(); //기본동작 막기. 파일을 브라우저의 기본 동작대로 열지 않도록 설정

    const file = e.dataTransfer.files[0]; // 드롭된 파일 중 첫번째 파일 가져오기

    console.log(file);
    //이미 있는 block이면 종료
    if (!file) {
      console.log("이미 있는거임");
    } else {
      console.log("New!");
      // setAudioBlock(file);
      addBlock(file); //파일 업로드
      createAudioFile(file);
    }
  };

  //Audio파일 셋팅=====================================
  const createAudioFile = (file) => {
    const reader = new FileReader(); // 파일을 읽기 위한 FileReader객체 생성

    //파일 성공적으로 읽으면 실행할 콜백 함수 정의
    reader.onload = (e) => {
      // const blockId = "block" + blockCounter;
      const blockId = blockCounter;
      const audioElement = new Audio();
      audioElement.src = e.target.result; // FileReader로 읽은
      // audioElement.id = "audio" + blockId;  // 블럭의 고유ID값 할당
      audioElement.id = "audio" + blockId; // 블럭의 고유ID값 할당

      //오디오의 메타데이터 로드 완료시 실행되는 이벤트 리스너 추가
      audioElement.addEventListener("loadedmetadata", () => {
        const duration = audioElement.duration; //오디오의 총 재생 시간
        const width = duration;
        document.body.appendChild(audioElement);
        const color = "#" + Math.floor(Math.random() * 16777215).toString(16); //랜덤한 색상 코드를 생성
        // const container = document.getElementById("blockListArea"); //블록 추가할 컨테이너 가져와서
        // setContainer(container);
        const newBlock = document.createElement("div"); //새로운 div생성
        //새로운 블럭 관련 설정--------
        newBlock.id = blockId;
        newBlock.className = "block";
        newBlock.style.backgroundColor = color;
        newBlock.style.width = width + "px";
        //ondrag속성 지정해줘야함<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        console.log("file>>>>>", file.name);
        newBlock.style.height = "50px";
        newBlock.style.display = "inline-block";
        newBlock.style.position = "absolute";
        newBlock.style.textAlign = "center";
        newBlock.style.lineHeight = "50px";
        newBlock.style.left =
          ((blockCounter * 100) % (container.clientWidth - 100)) + "px";
        newBlock.style.top =
          Math.floor(blockCounter / (container.clientWidth / 100)) * 100 + "px";
        //newBlock.contentEditable = "true"; //블록의 내용을 편집 가능하도록 설정
        newBlock.innerHTML = file.name; //블록의 내용을 설정
        container.appendChild(newBlock); //새로운 블록을 컨테이너에 추가

        newBlock.addEventListener("click", () => {
          setSelectedBlock(newBlock);
        });
        //blockList갱신
        const block = {
          id: blockCounter,
          projectId: 1, //------------------->{projectId}이런식으로 수정필요
          left: (blockCounter * 100) % (container.clientWidth - 100),
          top: Math.floor(blockCounter / (container.clientWidth / 100)) * 100,
        };
        if (blockList) {
          const prevBlockList = [...blockList, block];
          console.log("if", prevBlockList);
          setBlockList(prevBlockList); // 기존 배열에 새로운 블록 추가
        } else {
          setBlockList([block]);
          console.log("else", blockList);
        }
        setBlockCounter(blockCounter + 1); //블록 카운터 수 증가
      });
    };
    reader.readAsDataURL(file); // FileReader 객체를 사용하여 주어진 파일을 데이터 URL로 읽어들이는 역할을 합니다.
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
      audio.pause();
      console.log(block.id);
    });
  };

  //Audio파일 서버에 추가----->new Audio Insert에서 실행---------------------------------------------
  const addBlock = async (file) => {
    console.log("addBlock임다", file);
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
    console.log(res.data);
  };

  //저장된 음원전체 가져오기-> back에서 구현-------------------------------------------------------
  const getBlockList = async () => {
    const res = await apiInstance.get(`/block/get/${projectId}`);
    console.log(res.data);
    setBlockList(res.data.content);
    console.log(res.data);
  };

  //새로 저장된 음원들 가져오기
  const getNewBlockList = async () => {};

  //block 위치 조정-----------------------------------------------------------------------------
  const setLocation = (element, left, top) => {
    // console.log(element,left,top);
    element.style.left = `${left}px`;
    element.style.top = `${top}px`;
  };

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

//드래그 수정
