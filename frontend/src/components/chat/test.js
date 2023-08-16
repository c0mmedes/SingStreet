import React, { useEffect, useState, useRef } from "react";
import { api } from "../../services/httpService"
import "../../css/chat/Chat.css"
import OtherChat from "./OtherChat"
import MyChat from './MyChat';
import { Client } from "@stomp/stompjs";

const Chat = () => {
    const [chatList, setChatList] = useState([]);
    const [page, setPage] = useState(0);
    const [isLastPage, setIsLastPage] = useState(false);
    const [ws, setWs] = useState(null);
    const [nickname, setNickname] = useState('혁준장');
    const [talk, setTalk] = useState([]);
    const [content, setContent] = useState('');
    const [entId , setEntId] = useState(1);
    const chatEndRef = useRef(null); // 마지막 메시지 요소에 대한 ref 생성
    const apiInstance = api();

    useEffect(() => {
      const init = async () => {
        getChatList();
        // 웹소켓 연결
        // const socket = new WebSocket(`ws://i9b110.p.ssafy.io/backend/chatt/${entId}`);
        // WebSocket 연결
        const socket = new Client({
          brokerURL: "ws://localhost:8080/ws",
          reconnectDelay: 5000,
          heartbeatIncoming: 4000,
          heartbeatOutgoing: 4000,
          onConnect: () => {
            console.log('socket connected!!!');
            getChatList();
            subscribe();
          },
          onStompError: (frame) => {
            console.error(frame);
            },
          });
          // 웹소켓 연결 활성화
          socket.activate();
          setWs(socket);

        const subscribe = () =>{
          socket.subscribe(`/sub/channel/${entId}`, (message) =>{
            //메시지 도착 시 처리 로직
            const data = JSON.parse(message.body);
            console.log(data);
            let messageItem = null;
              
            if (data.nickname === nickname) {
              messageItem = <MyChat content={data}/>;
            } else {
              messageItem = <OtherChat content={data}/>;
            }
      
            setTalk((prevTalk) => [...prevTalk, messageItem]);
          })
        };
      }
      init();
      }, []);

        // 스크롤 내리기------------------------------------------
      useEffect(() => {
        // 새로운 메시지가 추가될 때마다 스크롤을 아래로 내림
        if (chatEndRef.current) {
          chatEndRef.current.scrollIntoView({ behavior: "smooth", block: "end" });
        }
      }, [talk,chatList]); // talk 상태가 업데이트될 때마다 실행

        

    const getChatList = async()=>{
        const res = await apiInstance.get(`/chatting/${entId}?page=${page}&size=100`);
        setChatList(res.data.content);
        setIsLastPage(res.data.last);
        console.log(res.data);
    }

    const handleSend = () => {
        if (content.trim() !== '') {
          const data = {
            nickname: nickname,
            content: content,
            entId:1,
          };
    
          ws.send(`/pub/channel/${entId}`,{}, JSON.stringify(data));
          setContent('');
        }
    }

    const handleKeyUp = (ev) => {
        if (ev.keyCode === 13) {
          handleSend();
          console.log("<enter--->")
        }
    }

    return (
        <div className="area">
          <div className="chatArea">
            {chatList.map((content) => {
              if(content.nickname === nickname){
                return <MyChat content={content}/>
              } else{
                return <OtherChat content={content}/>
              }
              })}
            
              {talk}
              <div ref={chatEndRef} />
          </div>
          
            <div className="inputArea">
            <textarea
                type="text"
                className="chatInput"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                onKeyUp={handleKeyUp}>
            </textarea>
            </div>
        </div>
    );
};

export default Chat;