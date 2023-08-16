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
    <div className="entPjtMemberInviteContainer">
      <div className="AlreadyMemberList">
        <h2 className="inviteEntPersonTitle">프로젝트 멤버 목록</h2>
        <div className="AlreadyMemberListHeader">
          <span>닉네임</span>
          <span>이메일</span>
          <span>성별</span>
        </div>
        <ul className="AlreadyMemberListItems">
          {projectMemberList.map((projectMember) => (
            <li
              className="AlreadyMemberListItem"
              key={projectMember.user.userId}>
              <span> {projectMember.user.nickname}</span>
              <span> {projectMember.user.email}</span>
              <span> {projectMember.user.gender}</span>
            </li>
          ))}
        </ul>
      </div>
      <div className="PossibleInviteMemberList">
        <h2 className="inviteEntPersonTitle">초대 가능한 엔터원 목록</h2>
        <div className="PossibleInviteMemberListHeader">
          <span>닉네임</span>
          <span>이메일</span>
          <span>성별</span>
        </div>
        <ul className="PossibleInviteMemberListItems">
          {entMemberList.map((entMember) => (
            <li className="PossibleInviteMemberListItem" key={entMember.userId}>
              <span> {entMember.nickname}</span>
              <span> {entMember.email}</span>
              <span> {entMember.gender}</span>
              <button
                className="inviteMemberBtn"
                onClick={() => onClickMemberInvite(entMember.userId)}>
                초대
              </button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};
export default EntProjectMemberInvite;
