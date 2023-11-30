// Header.js
import React from "react";
import { Link } from "react-router-dom";
import "../../css/layout/Header.css";
import logo from "../../assets/logo.png";
import { api } from "../../services/httpService";

const Header = ({ isLogin, setIsLogin, user, addUserInfo, logout }) => {
  const apiInstance = api();
  const accessToken = sessionStorage.getItem("accessToken");
  console.log(accessToken);

  async function onClickLogout() {
    try {
      await apiInstance.post(
        "/auth/logout",
        {
          // 요청 바디 데이터 (필요한 경우 추가)
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
          },
        }
      );
      setIsLogin();
      logout();
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("refreshToken");
      alert("로그아웃 성공");
    } catch (error) {
      alert("로그아웃 실패"); // 사용중인건지 사용이 불가능한건지 구분해줘야함
    }
  }

  return (
    <header>
      <Link to="/">
        <img src={logo} alt="SING STREET 로고" />
      </Link>
      <div className="header_right">
        <Link className="headerA" to="/ent">
          <span>Ent</span>
        </Link>
        <Link className="headerA" to="/chart">
          <span>Chart</span>
        </Link>
        <Link className="headerA" to="/music">
          <span>Music</span>
        </Link>
        {/* 로그인 상태에 따라 Login 또는 Mypage로 링크 변경 */}
        {isLogin === true ? (
          <div>
            <Link className="headerA" to="/mypage">
              <span>Mypage</span>
            </Link>
            <a className="headerA" href="#" onClick={onClickLogout}>
              Logout
            </a>
          </div>
        ) : (
          <Link className="headerA" to="/login">
            <span>Login</span>
          </Link>
        )}
      </div>
    </header>
  );
};

export default Header;
