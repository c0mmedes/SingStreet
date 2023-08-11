//src/components/ent/EntMain.js
import React, { useEffect, useState } from "react";
import "../../css/ent/EntMain.css";
import { Link, Outlet, useParams } from "react-router-dom";
import { api } from "../../services/httpService";

const EntFeed = () => {
  // 피드 쓰기
  const [content, setContent] = useState("");
  const [type, setType] = useState("");

  const handleSubmit = () => {
    // 여기서 API 호출을 통해 데이터베이스에 게시물을 추가합니다.
    api.post("/board", { content });
    setContent("");
    setType("");
  };
  // useEffect(() => {
  //   // API 호출을 통해 게시물 데이터를 가져옵니다.
  //   api.get("/board/{type}").then((response) => setContent(response.data));
  // },
  //  []);

  return (
    <>
      <div className="profile">
        <h1>헤더(엔터명)</h1>
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
            <option value="">타입 선택</option>
            <option value="type1">공지사항</option>
            <option value="type2">자유</option>
          </select>
          <button onClick={handleSubmit}>게시하기</button>
        </div>
      </div>

      {/* <div className="feed-posts">
          {posts.map((post) => (
            <div key={post.id} className="post">
              <div className="post-content">{post.content}</div>
              <div className="post-date">{post.date}</div>
            </div>
          ))}
        </div> */}
    </>
  );
};

export default EntFeed;
