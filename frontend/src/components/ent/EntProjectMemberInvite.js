import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";

const EntProjectMemberInvite = ({ myEntList, userInfo }) => {
	// axios 인스턴스
	const apiInstance = api();
	// 라우터 파라미터에서 가져올 entId 변수
	const { entId, entName, entMasterId, projectId } = useParams();
	// 화면을 다른 화면으로 넘겨줄때 필요한
	const navigate = useNavigate();
	// 상태관리
	const [entMemberList, setEntMemberList] = useState([]); // 엔터 멤버 리스트
	const [projectMemberList, setProjectMemberList] = useState([]); // 프로젝트 멤버 리스트
	// 선택된 멤버
	const [selectedMembers, setSelectedMembers] = useState([]);

	useEffect(() => {
		// 원하는 조건을 확인하고 이전 화면으로 돌아가기
		if (parseInt(userInfo.userId) !== parseInt(entMasterId)) {
			// 이전 화면으로 이동
			navigate(-1);
		}
		getEntMemberList();
		getProjectMemberList();
	}, []);

	//[비동기] 엔터 멤버 목록을 불러오는 함수
	const getEntMemberList = async () => {
		try {
			const accessToken = sessionStorage.getItem("accessToken");
			const res = await apiInstance.get(`ent/member/${entId}`, {
				headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
				},
			});
			/*
            res.data
            [
                {
                    "userId": 0
                    "nickname": "string",
                    "email": 0,
                    "gender":남
                    "createAt": "2023-08-07T07:34:01.357Z",   
                }
            ] 
      */
			console.log(res.data);
			setEntMemberList(res.data);
		} catch {
			alert("엔터 멤버 목록 불러오기 실패!");
		}
	};
	//[비동기] 초대 버튼 누르기 함수
	const onClickMemberInvite = async (userId) => {
		try {
			const accessToken = sessionStorage.getItem("accessToken");
			const res = await apiInstance.put(
				"project/member",
				{
					userId: userId,
					entId: entId,
					projectId: projectId,
				},
				{
					headers: {
						Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
					},
				}
			);
			alert("초대 성공");
		} catch {
			alert("초대 에러 발생");
		}
	};
	//[비동기] 프로젝트 멤버 목록 불러오는 함수
	const getProjectMemberList = async () => {
		try {
			const accessToken = sessionStorage.getItem("accessToken");
			const res = await apiInstance.get(`project/member/${projectId}`, {
				headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
				},
			});
			console.log(res.data);
			setProjectMemberList(res.data);
			alert("프로젝트 멤버 목록 불러오기 성공");
		} catch {
			alert("불러오기 발생");
		}
	};
	return (
		<div>
			<h2>엔터 멤버 목록</h2>
			<ul>
				{entMemberList.map((entMember) => (
					<li key={entMember.userId}>
						닉네임 : {entMember.nickname}
						이메일 : {entMember.email}
						성별 : {entMember.gender}
						<button onClick={() => onClickMemberInvite(entMember.userId)}>초대</button>
					</li>
				))}
			</ul>
			<h2>프로젝트 멤버 목록</h2>
			<ul>
				{projectMemberList.map((projectMember) => (
					<li key={projectMember.userId}>
						닉네임 : {projectMember.nickname}
						이메일 : {projectMember.email}
						성별 : {projectMember.gender}
						<button onClick={() => onClickMemberInvite(projectMember.userId)}>초대</button>
					</li>
				))}
			</ul>
		</div>
	);
};

export default EntProjectMemberInvite;
