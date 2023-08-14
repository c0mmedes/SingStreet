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
	// 엔터 멤버 리스트
	const [entMemberList, setEntMemberList] = useState([]);
	// 선택된 멤버
	const [selectedMembers, setSelectedMembers] = useState([]);

	useEffect(() => {
		// 원하는 조건을 확인하고 이전 화면으로 돌아가기
		if (parseInt(userInfo.userId) !== parseInt(entMasterId)) {
			// 이전 화면으로 이동
			navigate(-1);
		}
		getEntMemberList();
	}, []);

	// 엔터 멤버 목록을 불러오는 함수
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
	//
	const onClickMemberInvite = async (userId) => {
		const accessToken = sessionStorage.getItem("accessToken");
		const res = await apiInstance.post(
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
	};

	return (
		<div>
			<h2>엔터 멤버 목록</h2>
			<ul>
				{entMemberList.map((member) => (
					<li key={member.userId}>
						닉네임 : {member.nickname}
						이메일 : {member.email}
						성별 : {member.gender}
						<button onClick={() => onClickMemberInvite(member.userId)}>초대</button>
					</li>
				))}
			</ul>
		</div>
	);
};

export default EntProjectMemberInvite;
