import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntCreate.css";

const EntApply = ({ userInfo, isLogin }) => {
	const [age, setAge] = useState("");
	const [artist, setArtist] = useState("");
	const [audioName, setAudioName] = useState("");
	const [content, setContent] = useState("");
	const [entId, setEntId] = useState("");
	const [hope, setHope] = useState("");
	const [userId, setUserId] = useState("");
	const handleAge = (e) => {
		setAge(e.target.value);
	};
	const handleArtist = (e) => {
		setArtist(e.target.value);
	};
	const handleAudioName = (e) => {
		setAudioName(e.target.value);
	};
	const handleContent = (e) => {
		const value = e.target.value === "true"; // 문자열 "true"를 불리언 true로 변환
		setContent(value);
	};
	const handleEntId = (e) => {
		setEntId(e.target.value);
	};
	const handleHope = (e) => {
		setHope(e.target.value);
	};
	const handleUserId = (e) => {
		setUserId(e.target.value);
	};
	// axios 인스턴스 생성
	const apiInstance = api();
	// 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
	const navigate = useNavigate();

	const onClickEntApply = async function () {
		const accessToken = sessionStorage.getItem("accessToken");
		try {
			const res = await apiInstance.post(
				"/ent/apply",
				{
					age: age,
					artist: artist,
					audioName: audioName,
					content: content,
					entId: entId,
					hope: hope,
					userId: userId,
				},
				{
					headers: {
						Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
					},
				}
			);
			if (res.data) {
				//정상적으로 만들어지면 true
				navigate("/ent");
				alert("엔터 지원 성공!");
			} else {
				alert("엔터 지원 실패!");
			}
		} catch (error) {
			alert("엔터 지원 오류");
		}
	};
	return (
		<div>
			<div className="form_wrapper">
				<div className="form_container">
					<div className="title_container">
						<h2>엔터 지원 하기</h2>
					</div>

					<div className="row clearfix">
						<div className="">
							<form className="entCreateForm">
								<label>나이</label>
								<div className="input_field">
									<input
										type="text"
										name="age"
										value={age}
										onChange={handleAge}
										placeholder="만 나이 숫자만 입력. 예) 20"
										required
									/>
								</div>
								<div className="input_field">
									{/* <input type="radio" id="ex_rd" name="ex_rds"> 
                  <label for="ex_rd">라디오버튼</label> */}
								</div>
								<label>선호 아티스트</label>
								<div className="input_field">
									<textarea
										id="artist"
										type="text"
										name="artist"
										value={artist}
										onChange={handleArtist}
										required
									/>
								</div>
								<label>하고싶은 노래</label>
								<div className="input_field">
									<input type="hope" name="tagList" value={hope} onChange={handleHope} required />
								</div>
								<label>포부</label>
								<div className="input_field">
									<input
										type="text"
										name="content"
										value={content}
										onChange={handleContent}
										required
									/>
								</div>
								<label>본인이 부른 노래 파일</label>
								<div className="input_field">
									<input
										type="file"
										name="file"
										value={audioName}
										onChange={handleAudioName}
										required
									/>
								</div>
								<input
									className="button"
									type="submit"
									value="지원하기"
									onClick={onClickEntApply}
								/>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
export default EntApply;
