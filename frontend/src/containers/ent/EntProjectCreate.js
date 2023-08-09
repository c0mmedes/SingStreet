import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";

const EntProjectCreate = () => {
  return (
    <div>
      <div className="RegisterWrap">
        <div className="RegisterForm">
          <h1 className="resisterH1">REGISTER</h1>
          <div className="inputbox">
            <label htmlFor="nickname">Nickname</label>
            <input
              type="text"
              name="nickname"
              value={nickname}
              onChange={handleNickname}
              className="RegisterInput"
            />
            <button onClick={checkDuplicateNickname} className="CheckBtn ">
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
              className="RegisterInput"
            />
            <button onClick={checkDuplicateEmail} className="CheckBtn">
              중복확인
            </button>
          </div>
          {/* 인증번호 입력란과 확인 버튼 */}
          {!isAuthorized && isEmailDuplicated && (
            <div className="inputbox">
              <label htmlFor="authCode">Verification Code</label>
              <input
                type="text"
                name="authCode"
                value={authCode}
                onChange={handleAuthCode}
                className="RegisterInput"
              />
              <button onClick={onClickAuthorize} className="CheckBtn">
                확인
              </button>
            </div>
          )}
          <div className="inputbox">
            <label htmlFor="gender">Gender</label>
            <select name="gender" value={gender} onChange={handleGender}>
              <option value="">Select Gender</option>
              <option value="남">남</option>
              <option value="녀">녀</option>
            </select>
          </div>{" "}
          <div className="inputbox">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              name="password"
              value={password}
              onChange={handlePassword}
              className="RegisterInput"
            />
          </div>
          <div className="inputbox">
            <label htmlFor="passwordConfirm">Password Confirm</label>
            <input
              type="password"
              name="passwordConfirm"
              value={passwordConfirm}
              onChange={handlePasswordConfirm}
              className="RegisterInput"
            />
          </div>
          <div className="inputbox">
            <label htmlFor="프로필 사진">ProfileImg</label>
            <input type="file" onChange={handleFileChange} />
          </div>
          <button type="button" onClick={onClickRegister} className="signBtn">
            Sign up
          </button>
        </div>
      </div>
    </div>
  );
};

export default EntProjectCreate;
