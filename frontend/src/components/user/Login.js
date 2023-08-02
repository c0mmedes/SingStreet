import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../../css/user/Login.css";
import Background from "../layout/Background.js";
import { api } from "../../services/httpService";

const apiInstance = api();

function Login({ setUser }) {
	const [inputEmail, setInputEmail] = useState("");
	const [inputPw, setInputPw] = useState("");

	const handleInputEmail = (e) => {
		setInputEmail(e.target.value);
	};

	const handleInputPw = (e) => {
		setInputPw(e.target.value);
	};

	const onClickLogin = async function login() {
		try {
			const res = await apiInstance.post("/auth/login", {
				email: inputEmail,
				password: inputPw,
			});

			// 서버 응답으로 받은 accessToken과 refreshToken을 sessionStorage에 저장
			sessionStorage.setItem("accessToken", res.data.accessToken);
			sessionStorage.setItem("refreshToken", res.data.refreshToken);

			console.log(res);
			console.log("res.data.accessToken :: ", res.data.accessToken);
			console.log("res.data.grantType :: ", res.data.grantType);
			console.log("res.data.refreshToken :: ", res.data.refreshToken);
			alert("로그인 성공");
		} catch (error) {
			console.error("로그인 실패:", error);
			alert("로그인 실패");
		}
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
