import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../../css/user/Login.css";
import Background from "../layout/Background.js";
import { api } from "../../services/httpService";

const apiInstance = api();

function Login({setUser}) {
  const [inputEmail, setInputEmail] = useState("");
  const [inputPw, setInputPw] = useState("");

  const handleInputEmail = (e) => {
    setInputEmail(e.target.value);
  };

  const handleInputPw = (e) => {
    setInputPw(e.target.value);
  };

  const onClickLogin = async function login () {
    console.log("click login");
    console.log("Email : ", inputEmail);
    console.log("PW : ", inputPw);
    
    await apiInstance.post("/auth/login", JSON.stringify({
      email: inputEmail,
      password: inputPw,
    }))
    .then((res) => {
        console.log(res);
        console.log("res.data.userId :: ", res.data.userId);
        console.log("res.data.msg :: ", res.data.msg);
        alert("로그인 성공");
      })
      .catch(() => {
        alert("로그인 실패");
      });
  };

  return (
    <div className="LoginWrap">
      <h1>LOGIN</h1>
      <div className="inputbox">
        <label htmlFor="input_email">Email</label>
        <input
          type="text"
          name="input_email"
          value={inputEmail}
          onChange={handleInputEmail}
          className="IdInput"
        />
      </div>
      <div className="inputbox b2">
        <label htmlFor="input_pw">PWD</label>
        <input
          type="password"
          name="input_pw"
          value={inputPw}
          onChange={handleInputPw}
          className="PwdInput"
        />
      </div>
      <div>
        <button type="button" onClick={onClickLogin} className="loginBtn">
          Log in
        </button>
        <div className="register">
          <span>회원이 아니십니까?</span>
          <Link to="/register">
            <span>회원가입</span>
          </Link>
        </div>
      </div>
      <div className="background-wrapper">
        <Background />
      </div>
    </div>
  );
}

export default Login;
