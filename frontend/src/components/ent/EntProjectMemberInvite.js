import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectMemberInvite.css";

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
      console.log("엔터 멤버 목록 불러오기 실패!");
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
      alert("에러: 이미 초대 받은 사람입니다!");
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
    } catch {
      console.log(
        "프로젝트 멤버 불러오기 에러 발생(프로젝트 멤버가 없습니다.)"
      );
    }
  };
  return (
    <div entPjtMemberInviteContainer>
      <div AlreadyMemberList>
        <h2>프로젝트 멤버 목록</h2>
        {projectMemberList.map((projectMember) => (
          <li key={projectMember.user.userId}>
            {projectMember.user.nickname}
            {projectMember.user.email}
            {projectMember.user.gender}
          </li>
        ))}
      </div>
      <div PossibleInviteMemberList>
        <h2>초대 가능한 엔터원 목록</h2>
        {entMemberList.map((entMember) => (
          <li key={entMember.userId}>
            닉네임 : {entMember.nickname}
            이메일 : {entMember.email}
            성별 : {entMember.gender}
            <button onClick={() => onClickMemberInvite(entMember.userId)}>
              초대
            </button>
          </li>
        ))}
      </div>
    </div>
  );
};

export default EntProjectMemberInvite;
