import React from 'react';
import "../../css/chat/MyChat.css"

const MyChat = ({content}) => {
    const createdAtDate = new Date(content.createdAt);
    
    return (
        <div className='MyChatWrap'>
            <div className="chat ch2">
                <div className="textbox">{content.content}</div>
            </div>
            <div className='chattime'>{createdAtDate.toLocaleString()}</div>
        </div>
    );
};

export default MyChat;