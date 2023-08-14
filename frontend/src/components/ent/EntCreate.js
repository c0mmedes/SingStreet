import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntCreate.css";
const EntCreate = ({ userInfo, isLogin }) => {
	const [entName, setEntName] = useState("");
	const [entInfo, setEntInfo] = useState("");
	const [entTagList, setEntTagList] = useState("");
	const [isAutoAceepted, setIsAutoAceepted] = useState(true);
	const [entImg, setEntImg] = useState("");
	const handleEntName = (e) => {
		setEntName(e.target.value);
	};
	const handleEntInfo = (e) => {
		setEntInfo(e.target.value);
	};
	const handleEntTagList = (e) => {
		setEntTagList(e.target.value);
	};
	const handleIsAutoAceepted = (e) => {
		const value = e.target.value === "true"; // 문자열 "true"를 불리언 true로 변환
		setIsAutoAceepted(value);
	};
	const handleEntImg = (e) => {
		setEntImg(e.target.files[0]);
	};
	// axios 인스턴스 생성
	const apiInstance = api();
	// 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
	const navigate = useNavigate();

	// 생성하기 클릭
	const onClickEntCreate = async function (e) {
		e.preventDefault(); // 기본 제출 동작 막기

		const accessToken = sessionStorage.getItem("accessToken");
		// 엔터 프로필 이미지와, entData를 함께 보내기 위한 작업
		const formData = new FormData();
		formData.append("file", entImg);
		const entData = {
			entInfo: entInfo,
			entName: entName,
			entTagList: entTagList,
			isAutoAccepted: isAutoAceepted,
		};
		formData.append(
			"requestDto",
			new Blob([JSON.stringify(entData)], {
				type: "application/json",
			})
		);

		try {
			const res = await apiInstance.post("/ent", formData, {
				headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
					"Content-Type": "multipart/form-data",
					Accept: "application/json", // 추가
				},
			});
			alert("엔터 생성 완료");
			navigate("/ent");
		} catch (error) {
			alert("엔터 생성 오류");
		}
	};
	return (
		<div>
			<div className="form_wrapper entCreateFormWrapper">
				<div className="form_container">
					<div className="title_container">
						<h2>엔터 신규 등록</h2>
					</div>

					<div className="row clearfix">
						<div className="">
							<form className="entCreateForm" onSubmit={onClickEntCreate}>
								<label>엔터명</label>
								<div className="input_field">
									<input
										type="text"
										maxlength="20"
										name="entId"
										value={entName}
										onChange={handleEntName}
										required
									/>
								</div>
								<div className="input_field">
									{/* <input type="radio" id="ex_rd" name="ex_rds"> 
                  <label for="ex_rd">라디오버튼</label> */}
								</div>
								<label>엔터 소개</label>
								<div className="input_field">
									<textarea
										id="entInfo"
										type="text"
										name="entInfo"
										value={entInfo}
										onChange={handleEntInfo}
										required
									/>
								</div>
								<label>해시태그</label>
								<div className="input_field">
									<input
										type="text"
										name="tagList"
										placeholder="#뉴진스 #하입보이"
										value={entTagList}
										onChange={handleEntTagList}
										required
									/>
								</div>
								<label>공개여부</label>
								<div className="input_field select_option">
									<select onChange={handleIsAutoAceepted}>
										<option value="true">공개엔터 (바로 가입)</option>
										<option value="false">공개엔터 (승인 대기)</option>
									</select>
									<div className="select_arrow"></div>
								</div>
								<label>엔터 프로필</label>
								<div className="input_field">
									<input type="file" name="file" onChange={handleEntImg} />
								</div>
								<input className="button" type="submit" value="생성하기" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
//  엔터명 (중복확인) 엔터 공개 설정 (자동가입) / 엔터 소개 / 해시태그 / 엔터로고
export default EntCreate;
