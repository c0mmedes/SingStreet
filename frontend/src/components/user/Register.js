import React, { useState } from "react";
import axios from "axios";
import "../../css/user/Register.css";

function Register() {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");

  const handleNickname = (e) => {
    setNickname(e.target.value);
  };

  const handleEmail = (e) => {
    setEmail(e.target.value);
  };

  const handlePassword = (e) => {
    setPassword(e.target.value);
  };

  const handlePasswordConfirm = (e) => {
    setPasswordConfirm(e.target.value);
  };

  const checkDuplicateEmail = () => {
    axios
      .post("/user_inform/checkEmail", null, {
        params: {
          email: email,
        },
      })
      .then((res) => {
        if (res.data.duplicate) {
          alert("이미 사용중인 이메일입니다.");
        } else {
          alert("사용 가능한 이메일입니다.");
        }
      })
      .catch();
  };

  const checkDuplicateNickname = () => {
    axios
      .post("/user_inform/checkNickname", null, {
        params: {
          nickname: nickname,
        },
      })
      .then((res) => {
        if (res.data.duplicate) {
          alert("이미 사용중인 닉네임입니다.");
        } else {
          alert("사용 가능한 닉네임입니다.");
        }
      })
      .catch();
  };

  const onClickRegister = () => {
    // if (password !== passwordConfirm) {
    //   alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    //   return;
    // }
    // axios
    //   .post("/user_inform/onRegister", null, {
    //     params: {
    //       nickname: nickname,
    //       email: email,
    //       password: password,
    //     },
    //   })
    //   .then((res) => {
    //     if (res.data.success) {
    //       console.log("회원가입 성공");
    //       document.location.href = "/";
    //     } else {
    //       alert(res.data.msg);
    //     }
    //   })
    //   .catch();
  };

  return (
    <div className="RegisterWrap">
      <h1>REGISTER</h1>
      <div className="inputbox">
        <label htmlFor="nickname">Nickname</label>
        <input
          type="text"
          name="nickname"
          value={nickname}
          onChange={handleNickname}
        />
        <button onClick={checkDuplicateNickname}>중복확인</button>
      </div>
      <div className="inputbox">
        <label htmlFor="email">Email</label>
        <input type="email" name="email" value={email} onChange={handleEmail} />
        <button onClick={checkDuplicateEmail}>중복확인</button>
      </div>
      <div className="inputbox">
        <label htmlFor="password">Password</label>
        <input
          type="password"
          name="password"
          value={password}
          onChange={handlePassword}
        />
      </div>
      <div className="inputbox">
        <label htmlFor="passwordConfirm">Password Confirm</label>
        <input
          type="password"
          name="passwordConfirm"
          value={passwordConfirm}
          onChange={handlePasswordConfirm}
        />
      </div>
      <button type="button" onClick={onClickRegister}>
        Sign up
      </button>
    </div>
  );
}

export default Register;
