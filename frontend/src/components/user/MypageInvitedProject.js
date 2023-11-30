import React, { useEffect, useState } from "react";
import "../../css/user/MyEntList.css";
import { api } from "../../services/httpService";
import { useNavigate } from "react-router-dom";
import "../../css/user/MypageInvitedProject.css";
const MypageInvitedProject = ({ userInfo, myEntList }) => {
  // useState
  const [invitedProjectList, setInvitedProjectList] = useState([{}]);
  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 navigate 생성
  const navigate = useNavigate();
  // useEffect
  useEffect(() => {
    getInvitedProject();
  }, []);
  // 내가 받은 프로젝트 초대 목록 가져오기
  const getInvitedProject = async () => {
    const accessToken = sessionStorage.getItem("accessToken");
    try {
      const res = await apiInstance.get(
        `/project/invitedList/${userInfo.userId}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
          },
        }
      );
      console.log(res.data);
      setInvitedProjectList(res.data);
    } catch {
      console.log("초대장 가져오기 오류(초대받은 프로젝트가 없음)");
    }
  };
  // 수락 버튼 누르기
  const onClickAccept = async (isAccept, projectId, createdAt) => {
    const accessToken = sessionStorage.getItem("accessToken");
    console.log(
      `isAccept: ${isAccept}, projectId: ${projectId}, createdAt:${createdAt}`
    );
    const res = await apiInstance.put(
      "project/member/joinProject",
      {
        userId: userInfo.userId,
        projectId: projectId,
        isAccept: isAccept,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
        },
      }
    );
  };

  console.log(userInfo);
  return (
    <div className="myEntListContainer">
      <div className="myEntListWrap">
        <h1>초대 받은 프로젝트</h1>
        {/* 초대장 불러오기 */}
        {invitedProjectList
          .filter((project) => project.isAccept === null)
          .map((project) => (
            <div className="myEntListItemContainer">
              <li className="myEntListItem" key={project.entId}>
                <div className="myEntListItemTitleWrap">
                  <h3 className="myEntListItemTitle">
                    엔터 이름 : {project.entName}
                  </h3>
                  <h3 className="myEntListItemTitle invitePjtWrap">
                    프로젝트 이름 : {project.projectName}
                  </h3>
                  <button
                    className="invitePjtBtn invitePjtBtnOk"
                    onClick={() => {
                      onClickAccept(true, project.projectId, project.createdAt);
                    }}>
                    수락
                  </button>
                  <button
                    className="invitePjtBtn invitePjtBtnOkNo"
                    onClick={() => {
                      onClickAccept(
                        false,
                        project.projectId,
                        project.createdAt
                      );
                    }}>
                    거절
                  </button>
                </div>
              </li>
            </div>
          ))}
      </div>
    </div>
  );
};

export default MypageInvitedProject;
