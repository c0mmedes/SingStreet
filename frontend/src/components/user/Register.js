import React, { useState } from "react";
import axios from "axios";
import "../../css/user/Register.css";
import Background from "../layout/Background.js";

function Register() {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [gender, setGender] = useState("");

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

  const handleGender = (e) => {
    setGender(e.target.value);
  };

  const checkDuplicateEmail = () => {
    axios
      .get(`/auth/email/${email}`)
      .then((res) => {
        if (res.status === 200) {
          alert("사용 가능한 이메일입니다.");
        }
      })
      .catch((error) => {
        if (error.response && error.response.status === 409) {
          alert("이미 사용중인 이메일입니다.");
        } else {
          console.error("중복 이메일 확인 중 오류 발생:", error);
        }
      });
  };

  const checkDuplicateNickname = () => {
    axios
      .get(`http://i9b110.p.ssafy.io:4050/auth/nickname/${nickname}`)
      .then((res) => {
        if (res.data.status === 200) {
          alert("사용 가능한 닉네임입니다.");
        }
      })
      .catch((error) => {
        if (error.response && error.response.status === 409) {
          alert("이미 사용중인 닉네임입니다.");
        } else {
          console.error("중복 닉네임 확인 중 오류 발생:", error);
        }
      });
  };

  const onClickRegister = () => {
    if (password !== passwordConfirm) {
      alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return;
    }

    const userData = {
      email: email,
      gender: gender === "남" ? "M" : "F",
      nickname: nickname,
      password: password,
      user_img: "사용자 프로필 이미지 파일 경로", // 사용자의 프로필 이미지 경로로 변경핧겅미
    };

    axios
      .post("/auth/password/sendTemporaryPassword", userData)
      .then((res) => {
        if (res.status === 200) {
          console.log("회원가입 성공");
          document.location.href = "/";
        }
      })
      .catch((error) => {
        console.error("회원가입 중 오류 발생:", error);
        alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
      });
  };

  return (
    <div className="RegisterWrap">
      <div>
        <h1>REGISTER</h1>
        <div className="inputbox">
          <label htmlFor="nickname">Nickname</label>
          <input
            type="text"
            name="nickname"
            value={nickname}
            onChange={handleNickname}
          />
          <button onClick={checkDuplicateNickname} className="CheckBtn">
            중복확인
          </button>
        </div>
        <div className="inputbox">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            name="email"
            value={email}
            onChange={handleEmail}
          />
          <button onClick={checkDuplicateEmail} className="CheckBtn">
            중복확인
          </button>
        </div>
        <div className="inputbox">
          <label htmlFor="gender">Gender</label>
          <select name="gender" value={gender} onChange={handleGender}>
            <option value="">Select Gender</option>
            <option value="남">남</option>
            <option value="녀">녀</option>
          </select>
        </div>
      </div>
      <div className="rightWrap">
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
        <button type="button" onClick={onClickRegister} className="signBtn">
          Sign up
        </button>
      </div>

      <div className="background-wrapper">
        <Background />
      </div>
    </div>
  );
}

export default Register;
