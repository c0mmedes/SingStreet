//src/components/ent/EntMain.js
import React, { useEffect, useState } from "react";
import "../../css/ent/EntMain.css";
import { Link, useParams } from "react-router-dom";
import { api } from "../../services/httpService";

const EntFeed = ({userInfo}) => {
  // entMain 라우터 경로에 있는 param인 entId, entMasterId, entName를 저장하는 변수
  const { entId, entMasterId, entName } = useParams();
  // 엔터 정보를 담고 있는 객체
  const [ent, setEnt] = useState({});
  // axios 객체
  const apiInstance = api();
  // 피드 쓰기
  const [content, setContent] = useState("");
  const [type, setType] = useState();

  useEffect(() => {
    // API 호출을 통해 게시물 데이터를 가져옵니다.
    // API 호출로 엔터 정보 가져옴
    getEnt();
  },[]);

  // [API]  데이터베이스에 게시물을 추가하는 함수
  const onClickSubmit = async () => {
    const accessToken = sessionStorage.getItem("accessToken");
    const feedData = {
      ent: entId,
      title: "dummy",
      content: content,
      isNotice: type,
    };
    const res = await apiInstance.post(`/ent/feed`, feedData, {
      headers: {
        Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
      },
    });
    console.log(`피드 추가 : ${res}`);
    setContent("");
    setType("");
  };

  //[API] 엔터 정보를 가져오는 함수
  const getEnt = async () => {
    const res = await apiInstance.get(`/ent/${entId}`);
    const newEnt = { ...res.data }; // 새로운 객체를 생성하고 res.data의 내용을 복사
    setEnt(newEnt);
    // "entId": 1,
    // "userId": "엔터장 아이디",
    // "entName": "qwe",
    // "entImg": "qwe",
    // "entInfo": "qwe",
    // "tagNameList": [],
    // "autoAccepted": true
  };

  return (
    <>
      <div className="profile">
        <h1>{ent.entName}</h1>
        <span>
          {ent.entInfo}
        </span>
        <div className="videos">
          <div className="video">
            <div className="video-time">15.13</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/368244254.sd.mp4?s=2dc98d46cc5c55913b309928d1d14769f76bc6f9&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">Planning Helps Make</div>
            <div className="view">15.4k views</div>
          </div>
          <div className="video">
            <div className="video-time">13.10</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/356200184.sd.mp4?s=f528556cafba1d369984dc341104e7eef8bb71bb&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">This Is Cloaud Break</div>
            <div className="view">13.2k views</div>
          </div>
          <div className="video">
            <div className="video-time">15.30</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/364324653.sd.mp4?s=7ded2b451ac7f5dfaf1375277aa0308cdf5d011c&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">Lost Your Mind</div>
            <div className="view">11.7k views</div>
          </div>
          <div className="video">
            <div className="video-time">11.30</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/399004885.sd.mp4?s=1d39443bef9856dacc4d3ba2c6be0881e38b7e66&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">Planning Helps Make</div>
            <div className="view">9.2k views</div>
          </div>
        </div>
      </div>

      <div className="post-form">
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="당신의 생각을 공유하세요."
        />
        <div className="post-form-bottom">
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="" disabled>말머리 선택</option>
            <option value="true">공지사항</option>
            <option value="false">자유</option>
          </select>
          <button onClick={onClickSubmit}>등록</button>
        </div>
      </div>

      <div className="feed-posts">
        {/* {posts.map((post) => (
          <div key={post.id} className="post">
            <div className="post-content">{post.content}</div>
            <div className="post-date">{post.date}</div>
          </div>
        ))} */}
        <div className="feed-post-user">
          <div>프로필사진</div>
          <div>유저닉네임</div>
          <div>글작성 시간</div>
        </div>
        <div className="feed-post-content">
          글 내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글
          내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글
          내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글 내용글
          내용
        </div>
        <div className="feed-post-comment-menu">
          <div>좋아요n개</div>
          <div>댓글n개</div>
        </div>
        <div className="feed-post-comments">코멘트들 보이는 영역</div>
      </div>
    </>
  );
};

export default EntFeed;
