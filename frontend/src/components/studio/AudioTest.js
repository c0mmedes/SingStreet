import React, { useEffect, useState, useRef } from "react";
import "./style.css"; // 스타일 파일 경로를 맞게 수정해주세요

const AudioTest = () => {
  const [blockCounter, setBlockCounter] = useState(0);
  const [playInterval, setPlayInterval] = useState(null);
  const [selectedBlock, setSelectedBlock] = useState(null);
  const [audioCtx, setAudioCtx] = useState(new (window.AudioContext || window.webkitAudioContext)());
  const [enhancerNode, setEnhancerNode] = useState(null);
  const [playhead, setPlayhead] = useState(null);

  useEffect(() => {
    const newEnhancerNode = audioCtx.createBiquadFilter();
    newEnhancerNode.type = "highshelf";
    newEnhancerNode.frequency.value = 1000;
    newEnhancerNode.gain.value = 25;
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

  const handleDrop = (e) => {
    e.preventDefault();
    const file = e.dataTransfer.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      const blockId = "block" + blockCounter;
      const audioElement = new Audio();
      audioElement.src = e.target.result;
      audioElement.id = "audio" + blockId;
      audioElement.addEventListener("loadedmetadata", () => {
        const duration = audioElement.duration;
        const width = duration; // Modify this as needed
        document.body.appendChild(audioElement);
        const color = "#" + Math.floor(Math.random() * 16777215).toString(16);
        const container = document.getElementById("container");
        const newBlock = document.createElement("div");
        newBlock.id = blockId;
        newBlock.className = "block";
        newBlock.style.backgroundColor = color;
        newBlock.style.width = width + "px";
        newBlock.style.left = ((blockCounter * 100) % (container.clientWidth - 100)) + "px";
        newBlock.style.top = Math.floor(blockCounter / (container.clientWidth / 100)) * 100 + "px";
        newBlock.contentEditable = "true";
        newBlock.innerHTML = "Track " + blockCounter;
        container.appendChild(newBlock);
        newBlock.addEventListener("click", () => {
          setSelectedBlock(newBlock);
        });
        setBlockCounter(blockCounter + 1);
      });
    };
    reader.readAsDataURL(file);
  };

  const handlePlay = () => {
    const blocks = document.querySelectorAll(".block");
    blocks.forEach((block) => {
      const audio = document.getElementById("audio" + block.id);
      if (
        block.offsetLeft <= playhead.offsetLeft &&
        playhead.offsetLeft <= block.offsetLeft + block.offsetWidth
      ) {
        const progress = (playhead.offsetLeft - block.offsetLeft) / block.offsetWidth;
        audio.currentTime = audio.duration * progress;
        if (audio.paused) {
          audio.play();
        }
      } else {
        audio.pause();
      }
    });
    playhead.style.display = "block";
    setPlayInterval(
      setInterval(() => {
        setPlayhead((prevPlayhead) => {
          prevPlayhead.style.left = parseInt(prevPlayhead.style.left, 10) + 1 + "px";
          return prevPlayhead;
        });
        blocks.forEach((block) => {
          const audio = document.getElementById("audio" + block.id);
          if (
            block.offsetLeft <= playhead.offsetLeft &&
            playhead.offsetLeft <= block.offsetLeft + block.offsetWidth
          ) {
            if (audio.paused) {
              const progress = (playhead.offsetLeft - block.offsetLeft) / block.offsetWidth;
              audio.currentTime = audio.duration * progress;
              audio.play();
            }
          } else {
            audio.pause();
          }
        });
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

export default AudioTest;
