//src/components/layout/EntNav.js

import React from "react";
import { Link } from "react-router-dom";
import "../../css/layout/MypageNav.css";
const EntNav = ({ userInfo, myEntList }) => {
  return (
    <div>
      <div class="left-side">
        <div class="side-wrapper">
          <div class="side-menu">
            <div className="side-title side-title1 ">
              <Link to={`/mypage`}>
                <a className="menuLink" href="#">
                  <span>내 정보</span>
                </a>
              </Link>
              <Link to={`/mypage/myentlist`}>
                <a className="menuLink" href="#">
                  <span>가입한 엔터</span>
                </a>
              </Link>
              <Link to={`/mypage/mymusic`}>
                <a className="menuLink" href="#">
                  <span>내 작품</span>
                </a>
              </Link>
              <Link to={`/mypage/mypageinvitedproject`}>
                <a className="menuLink" href="#">
                  <span>프로젝트 초대</span>
                </a>
              </Link>
            </div>
            <button class="entNavBtn show-more">탈퇴하기</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EntNav;
