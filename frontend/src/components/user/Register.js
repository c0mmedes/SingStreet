import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../css/user/Register.css";
import Background from "../layout/Background.js";
import { api } from "../../services/httpService";

function Register() {
	const [nickname, setNickname] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [passwordConfirm, setPasswordConfirm] = useState("");
	const [gender, setGender] = useState("");
	const [isEmailDuplicated, setIsEmailDuplicated] = useState(null);
	const [isNicknameDuplicated, setIsNicknameDuplicated] = useState(null);
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
	const apiInstance = api();
	// 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
	const navigate = useNavigate();

	async function checkDuplicateEmail() {
		if(email){
			try {
				const res = await apiInstance.get(`/auth/email/${email}`);
				console.log(res);
				setIsEmailDuplicated(email);
				alert("사용 가능한 이메일입니다.");
			} catch (error) {
				if (error.response && error.response.status === 422) {
					console.log('422 에러:', error.response.data);
					// 422 에러가 발생했을 때 처리할 로직을 구현합니다.
					setIsEmailDuplicated(null);
					alert("올바른 이메일이 아닙니다.")
				  } else {
					console.error('요청 실패:', error);
					// 다른 에러가 발생했을 때 처리할 로직을 구현합니다.
					setIsEmailDuplicated(null);
					alert("이미 사용중인 이메일 입니다.");
				  }
			}
		} else {
			alert("이메일을 입력해주세요.");
		}
	}

	async function checkDuplicateNickname() {
		if(nickname){
			try {
				const res = await apiInstance.get(`/auth/nickname/${nickname}`);
				console.log(res);
				setIsNicknameDuplicated(nickname);
				alert("사용 가능한 닉네임입니다.");
			} catch (error) {
				setIsNicknameDuplicated(null);
				alert("이미 사용중인 닉네임 입니다."); // 사용중인건지 사용이 불가능한건지 구분해줘야함
			}
		} else {
			alert("닉네임을 입력해주세요.");
		}
	}

	async function onClickRegister() {
		if (!nickname) {
			alert("닉네임을 입력해주세요");
			return;
		}
		if (!email) {
			alert("이메일을 입력해주세요");
			return;
		}
		if (!password) {
			alert("비밀번호를 입력해주세요");
			return;
		}
		if (!gender) {
			alert("성별을 선택해주세요");
			return;
		}
		if (password !== passwordConfirm) {
			alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return;
		}
		if (nickname !== isNicknameDuplicated) {
			alert("닉네임 중복확인 필요");
			return;
		}
		if (email !== isEmailDuplicated) {
			alert("이메일 중복확인 필요");
			return;
		}
		const data = {
			email: email,
			gender: gender === "남" ? "M" : "F",
			nickname: nickname,
			password: password,
			userImg: "사용자 프로필 이미지 파일 경로", // 사용자의 프로필 이미지 경로로 변경핧겅미
		};
		try {
			const res = await apiInstance.post("/user", data);
			console.log(res);
			console.log("회원가입 성공");
			navigate("/");
			alert("회원가입 성공");
		} catch (error) {
			console.error("회원가입 실패:", error);
			alert("회원가입 실패");
		}
	}

	return (
		<div className="RegisterWrap">
			<div>
				<h1>REGISTER</h1>
				<div className="inputbox">
					<label htmlFor="nickname">Nickname</label>
					<input type="text" name="nickname" value={nickname} onChange={handleNickname} />
					<button onClick={checkDuplicateNickname} className="CheckBtn">
						중복확인
					</button>
				</div>
				<div className="inputbox">
					<label htmlFor="email">Email</label>
					<input type="email" name="email" value={email} onChange={handleEmail} />
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
					<input type="password" name="password" value={password} onChange={handlePassword} />
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
