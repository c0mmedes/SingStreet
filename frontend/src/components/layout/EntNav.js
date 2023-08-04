import React from "react";
import "../../css/layout/EntNav.css";
const EntNav = () => {
  return (
    <div>
      <div class="left-side">
        <div class="side-wrapper"></div>
        <div class="side-wrapper"></div>
        <div class="side-wrapper">
          <div class="side-menu">
            <div className="side-title1">
              <a className="menuLink" href="#">
                지원하기
              </a>
            </div>

            <div className="side-title2">
              <h1>프로젝트</h1>
              <a href="#">프로젝트 생성</a>
              <a href="#">프로젝트 목록</a>
            </div>
            <div className="side-title3">
              <a href="#">작품 목록</a>
            </div>
            <button class="button show-more">Show More</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EntNav;
