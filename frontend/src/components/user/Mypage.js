import React from "react";
import "../../css/user/Mypage.css";
import { api } from "../../services/httpService";
import Footer from "../layout/Footer";

const Mypage = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
  const apiInstance = api();

  console.log(userInfo);
  return (
    <div className="user-my-info">
      <div className="mypage-title">내 정보</div>

      <div className="user-my-info-wrap">
        <div className="myinfoContainer">
          <div className="myinfoLeft">
            <div className="info-item">
              <label className="info-label">프로필</label>
              <img
                id="user-profile"
                src={userInfo.userImg}
                alt="프로필 이미지"
              />
            </div>
          </div>
          <div className="myinfoRight">
            <div className="info-item">
              <label className="info-label">이메일</label>
              <div className="info-value">{userInfo.email}</div>
            </div>
            <div className="info-item">
              <label className="info-label">성별</label>
              <input
                className="info-input"
                type="text"
                value={userInfo.gender}
              />
            </div>
            <div className="info-item">
              <label className="info-label">닉네임</label>
              <input
                id="user-nickname"
                className="info-input"
                type="text"
                value={userInfo.nickname}
              />
            </div>
          </div>
        </div>
        <div className="edit-button-wrap">
          <button className="edit-button">적용</button>
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default Mypage;
