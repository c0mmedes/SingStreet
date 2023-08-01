import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../style/Header.css";
import logo from "../assets/logo.png";
// import styled from "styled-components";

// const [isLogin, setIsLogin] = useState(false); // 로그인 관리

// useEffect(() => {
//   if (sessionStorage.getItem("name") === null) {
//     // sessionStorage 에 name 라는 key 값으로 저장된 값이 없다면
//     console.log("isLogin ?? :: ", isLogin);
//   } else {
//     // sessionStorage 에 name 라는 key 값으로 저장된 값이 있다면
//     // 로그인 상태 변경
//     setIsLogin(true);
//     console.log("isLogin ?? :: ", isLogin);
//   }
// });

const Header = () => {
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
        <Link to="/login">
          <span>Login</span>
        </Link>
      </div>
    </header>
  );
};

export default Header;
