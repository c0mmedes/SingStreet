// Header.js
import React from "react";
import { Link } from "react-router-dom";
import "../../css/layout/Header.css";
import logo from "../../assets/logo.png";

const Header = ({isLogin, setIsLogin, user, setUser}) => {
  return (
    <header>
      <Link to="/">
        <img src={logo} alt="SING STREET 로고" />
      </Link>
      <div className="header_right">
        <Link to="/ent">
          <span>Ent</span>
        </Link>
        <Link to="/chart">
          <span>Chart</span>
        </Link>
        <Link to="/music">
          <span>Music</span>
        </Link>
        {/* 로그인 상태에 따라 Login 또는 Mypage로 링크 변경 */}
        {isLogin ? (
          <Link to="/mypage">
            <span>Mypage</span>
          </Link>
        ) : (
          <Link to="/login">
            <span>Login</span>
          </Link>
        )}
      </div>
    </header>
  );
};

export default Header;
