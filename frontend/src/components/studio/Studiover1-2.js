import React, { useEffect, useState } from 'react';
import * as Y from 'yjs';
import { WebsocketProvider } from 'y-websocket';
import "./Studio.css"
const Studio = () => {
    const [blockCounter, setBlockCounter] = useState(0);
    const [playInterval, setPlayInterval] = useState(null);
    const [selectedBlock, setSelectedBlock] = useState(null);
    const [audioCtx, setAudioCtx] = useState(new (window.AudioContext || window.webkitAudioContext)());
    const [enhancerNode, setEnhancerNode] = useState(null);
    const [playhead, setPlayhead] = useState(null);

    const ydoc = new Y.Doc();
    const provider = new WebsocketProvider('ws://localhost:8080/chatt/', 1 , ydoc);
    const myMap = ydoc.getMap('myMap');

  useEffect(()=>{
    const init = async ()=> {
      
      const list = [{
        blockId : 1,
        left : 4,
        top : 5
      },{
        blockId : 2,
        left : 9,
        top : 10
      }]

      list.forEach((block) =>{
        setblock(block);
      })
    }
    init();
  },[]);

  myMap.observe(()=>{
    console.log(myMap);
    myMap.forEach((block, id) =>{
      const blockElement = document.getElementById(id);
      if(blockElement){
        setLocation(blockElement, block.left, block.top);
      }
    })
  })

  const setblock = (l) => {
    let block = myMap.get(l.blockId);
    if(!block){
      block = {
        blockId:l.blockId,
        left : l.left,
        top: l.top,
      }
      myMap.set(block.blockId, block)
      console.log(block);
      console.log("hi");
    }

  const blockElement = document.getElementById(block.blockId);

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
      console.log(block.blockId)
      myMap.set(2, {
        left:left,
         top:top
        });
        console.log("2",myMap.get(2));
      console.log("bye");
    }
    blockElement.ondrag = dragHandler;
  }
  blockElement.ondragstart = dragStartHandler;
  };

  //block 위치 조정---------------------------------------
  const setLocation = (element,left, top) =>{
    element.style.left = `${left}px`;
    element.style.top = `${top}px`;
  }

  return (
    <div className='studioArea'>
        <div className='inner'>
            <div className='buttonArea'>
                <button className='w-btn-neon2'>gd</button>
                <button className='w-btn-neon2'>gd</button>
                <button className='w-btn-neon2'>gd</button>
            </div>
            <div className='editArea'>

            </div>
            <div className='blockListArea'>

            </div>
        </div>
        
      <div id="1" className="draggable"></div>
      <div id="2" className="draggable"></div>
    </div>
  );
};

export default Studio;


//yMap안되는 예시
