import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "../style/Login.css";
import Background from "../components/Background";
import Register from "./Register";
function Login() {
  const [inputId, setInputId] = useState("");
  const [inputPw, setInputPw] = useState("");

  const handleInputId = (e) => {
    setInputId(e.target.value);
  };

  const handleInputPw = (e) => {
    setInputPw(e.target.value);
  };

  const onClickLogin = () => {
    console.log("click login");
    console.log("ID : ", inputId);
    console.log("PW : ", inputPw);
    axios
      .post("/user_inform/onLogin", null, {
        params: {
          user_id: inputId,
          user_pw: inputPw,
        },
      })
      .then((res) => {
        console.log(res);
        console.log("res.data.userId :: ", res.data.userId);
        console.log("res.data.msg :: ", res.data.msg);
        if (res.data.userId === undefined) {
          // id 일치하지 않는 경우 userId = undefined, msg = '입력하신 id 가 일치하지 않습니다.'
          console.log("======================", res.data.msg);
          alert("입력하신 id 가 일치하지 않습니다.");
        } else if (res.data.userId === null) {
          // id는 있지만, pw 는 다른 경우 userId = null , msg = undefined
          console.log(
            "======================",
            "입력하신 비밀번호 가 일치하지 않습니다."
          );
          alert("입력하신 비밀번호 가 일치하지 않습니다.");
        } else if (res.data.userId === inputId) {
          // id, pw 모두 일치 userId = userId1, msg = undefined
          console.log("======================", "로그인 성공");
          sessionStorage.setItem("user_id", inputId);
        }
        // 작업 완료 되면 페이지 이동(새로고침)
        document.location.href = "/";
      })
      .catch();
  };

  return (
    <div className="LoginWrap">
      <h1>LOGIN</h1>
      <div className="inputbox">
        <label htmlFor="input_id">Email</label>
        <input
          type="text"
          name="input_id"
          value={inputId}
          onChange={handleInputId}
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
        <button type="button" onClick={onClickLogin}>
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
