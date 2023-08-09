import React from "react";
import "../../css/layout/MypageNav.css";
import { api } from "../../services/httpService";
import { useNavigate } from "react-router-dom";
const MypageNav = ({}) => {
  return (
    <div>
      <div>
        <div class="left-side">
          <div class="side-wrapper">
            <div class="side-menu">
              <div className="side-title side-title1 ">
                <Link to={`/entapplicants/${entId}/${entName}`}>
                  <a className="menuLink" href="#">
                    <span>지원자 목록</span>
                  </a>
                </Link>
                ) : (
                <Link to={`/entapply/${entId}/${entName}`}>
                  <a className="menuLink" href="#">
                    <span>지원 하기</span>
                  </a>
                </Link>
                )}
              </div>

              <div className="side-title side-title2 ">
                <h1>프로젝트</h1>
                <a href="#">프로젝트 생성</a>
                <a href="#">프로젝트 목록</a>
              </div>
              <div className="side-title side-title3 ">
                <a href="#">
                  <span>작품 목록</span>
                </a>
              </div>
              <button class="entNavBtn show-more">탈퇴하기</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MypageNav;
