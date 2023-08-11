import React from 'react';
import "../../css/chat/OtherChat.css"

const OtherChat = ({content}) => {
    const createdAtDate = new Date(content.createdAt);

    return (
        <div className="OtherChatWrap">
            <div className="namebox">{content.nickname}</div>
            <div className="chat ch1">
                <div className="textbox">{content.content}</div>
            </div>
            <div className='chattime'>{createdAtDate.toLocaleString()}</div>
        </div>
    );
};

export default OtherChat;