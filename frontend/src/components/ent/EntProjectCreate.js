import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectCreate.css";
const EntProjectCreate = ({ userInfo, isLogin, myEntList, addToMyEntList }) => {
	// 라우터 파라미터에서 가져올 entId 변수
	const { entId } = useParams();
	//  useState로 관리할 상태
	const [projectName, setProjectName] = useState("");
	const [projectInfo, setProjectInfo] = useState("");
	const [projectTagList, setProjectTagList] = useState("");
	const [projectImg, setProjectImg] = useState("");
	const [isRecruited, setIsRecruited] = useState(true);
	const [isVisible, setIsVisible] = useState(true);
	const [partList, setPartList] = useState([""]);
	const [singName, setSingName] = useState("");
	const [singerName, setSingerName] = useState("");
	const [userList, setUserList] = useState([0]);

	const handleProjectName = (e) => {
		setProjectName(e.target.value);
	};
	const handleProjectInfo = (e) => {
		setProjectInfo(e.target.value);
	};
	const handleProjectTagList = (e) => {
		setProjectTagList(e.target.value);
	};
	const handleSingName = (e) => {
		setSingName(e.target.value);
	};
	const handleSingerName = (e) => {
		setSingerName(e.target.value);
	};
	const handleProjectImg = (e) => {
		setProjectImg(e.target.files[0]);
	};
	const handleIsRecruited = (e) => {
		setIsRecruited(e.target.value);
	};
	const handleIsVisible = (e) => {
		setIsVisible(e.target.value);
	};

	// axios 인스턴스 생성
	const apiInstance = api();
	// 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
	const navigate = useNavigate();

	useEffect(() => {
		// 내 엔터리스트를 불러오고, 엔터소속이 아니면 튕겨내기
		async function getMyEntListAndCheck() {
			await getMyEntList();
			if (myEntList && myEntList.length > 0) {
				console.log("1차검증 통과");
				if (!myEntList.some((ent) => parseInt(ent.entId) === parseInt(entId))) {
					// 이전 화면으로 이동
					alert("엔터 회원이 아닙니다. 먼저 엔터에 가입하세요!");
					navigate(-1);
				}
			} else {
				alert("엔터 회원이 아닙니다. 먼저 엔터에 가입하세요!");
				navigate(-1);
			}
		}
		getMyEntListAndCheck();
	}, []);

	// 내 엔터 리스트 불러오기
	const getMyEntList = async () => {
		const accessToken = sessionStorage.getItem("accessToken");
		try {
			const res = await apiInstance.get("/ent/myEnt", {
				headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
				},
			});
			await addToMyEntList(res.data);
		} catch (error) {}
	};

	// 파트추가
	const handleAddPart = () => {
		if (partList.length < 10) {
			// 최대 10개의 파트까지 추가 가능
			setPartList([...partList, ""]); // 새로운 파트 추가
		}
	};
	const handlePartChange = (index, value) => {
		const updatedPartList = [...partList];
		updatedPartList[index] = value;
		setPartList(updatedPartList);
	};
	const renderPartInputs = () => {
		return partList.map((part, index) => (
			<div key={index} className="input_field">
				<input
					type="text"
					name={`part-${index}`}
					value={part}
					onChange={(e) => handlePartChange(index, e.target.value)}
					required
				/>
			</div>
		));
	};

	// 생성하기 버튼 클릭
	const onClickProjectCreate = async function () {
		const accessToken = sessionStorage.getItem("accessToken");
		// 프로젝트 프로필 이미지와, projectData를 함께 보내기 위한 작업
		const formData = new FormData();
		formData.append("file", projectImg);
		const projectData = {
			entId: entId,
			isRecruited: isRecruited,
			isVisible: isVisible,
			partList: partList,
			projectInfo: projectInfo,
			projectName: projectName,
			projectTagList: projectTagList,
			singName: singName,
			singerName: singerName,
			userId: userInfo.userId,
			userList: userList,
		};
		formData.append(
			"dto",
			new Blob([JSON.stringify(projectData)], {
				type: "application/json",
			})
		);
		// 비동기 통신
		try {
			const res = await apiInstance.post("/project", formData, {
				headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
					"Content-Type": "multipart/form-data",
					Accept: "application/json", // 추가
				},
			});
			if (res.data) {
				//정상적으로 만들어지면 true
				navigate("/projectlist");
				alert(`${projectName}가 생성되었습니다!`);
			} else {
				alert("프로젝트명이 중복되었습니다!");
			}
		} catch (error) {
			alert("프로젝트 생성 오류");
		}
	};
	return (
		<div>
			<div className="form_wrapper">
				<div className="form_container">
					<div className="title_container">
						<h2>프로젝트 신규 등록</h2>
					</div>

					<div className="row clearfix">
						<div className="">
							<form className="entCreateForm">
								<label>프로젝트명</label>
								<div className="input_field">
									<input
										type="text"
										maxlength="20"
										name="projectName"
										value={projectName}
										onChange={handleProjectName}
										required
									/>
								</div>

								<label>프로젝트 소개</label>
								<div className="input_field">
									<textarea
										id="projectInfo"
										type="text"
										name="projectInfo"
										value={projectInfo}
										onChange={handleProjectInfo}
										required
									/>
								</div>

								<label>곡</label>
								<div className="input_field">
									<input
										type="text"
										name="singName"
										value={singName}
										onChange={handleSingName}
										required
									/>
								</div>

								<label>가수</label>
								<div className="input_field">
									<input
										type="text"
										name="singerName"
										value={singerName}
										onChange={handleSingerName}
										required
									/>
								</div>

								<label>해시태그</label>
								<div className="input_field">
									<input
										type="text"
										name="projectTagList"
										placeholder="#뉴진스 #하입보이"
										value={projectTagList}
										onChange={handleProjectTagList}
										required
									/>
								</div>

								<label>프로젝트 프로필</label>
								<div className="input_field">
									<input type="file" name="file" onChange={handleProjectImg} required />
								</div>
								<div className="input_field">
									<label htmlFor="isRecruited">프로젝트 멤버 모집 여부</label>
									<select name="isRecruited" value={isRecruited} onChange={handleIsRecruited}>
										<option value="">프로젝트 멤버 모집 여부</option>
										<option value="true">모집 중</option>
										<option value="false">모집 마감</option>
									</select>
								</div>
								<div className="input_field">
									<label htmlFor="isVisible">엔터페이지에 프로젝트 공개 여부</label>
									<select name="isVisible" value={isVisible} onChange={handleIsVisible}>
										<option value="">엔터페이지에 프로젝트 공개 여부</option>
										<option value="true">공개</option>
										<option value="false">공개하지 않음</option>
									</select>
								</div>

								<label>파트</label>
								{renderPartInputs()}
								{partList.length < 10 && (
									<button type="button" onClick={handleAddPart}>
										파트 추가
									</button>
								)}

								<input
									className="button"
									type="submit"
									value="생성하기"
									onClick={onClickProjectCreate}
								/>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
export default EntProjectCreate;
