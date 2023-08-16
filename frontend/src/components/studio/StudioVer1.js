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

  useEffect(()=>{
    const init = async ()=> {
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
      

      const blockElement = document.getElementById(id);

      // const setLocation = (element,left, top) =>{
      //   element.style.left = `${left}px`;
      //   element.style.top = `${top}px`;
      // }

      setLocation(blockElement, block.left, block.top);

      // blockElement.style.left = `${block.x}px`;
      // blockElement.style.top = `${block.y}px`;

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
      };

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
  },[]);

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
        
      <div id="item1" className="draggable"></div>
      <div id="item2" className="draggable"></div>
    </div>
  );
};

export default Studio;