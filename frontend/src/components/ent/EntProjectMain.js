import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectMain.css";

const EntProjectMain = ({ userInfo }) => {
  // useState
  const [project, setProject] = useState({});
  // 라우터 경로로 받아오는 정보들
  const { entId, entMasterId, entName, projectId } = useParams();
  // 비동기 통신을 위한 apiInstance 생성
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();
  // useEffect
  useEffect(() => {
    getProject();
  }, []);

  // [비동기] 내 프로젝트 정보 가져오기
  const getProject = async () => {
    const res = await apiInstance.get(`/project/info/${projectId}`);
    console.log(res.data);
    const newProject = { ...res.data }; // 새로운 객체를 생성하고 res.data의 내용을 복사
    setProject(newProject);
    /*
            projectId
            userId // 프로젝트장
            projectName
            singerName
            singName
            projectInfo
            projectImg
            partList:
            {
            nickname // 등록 전에는 “”
            partName
            userId // 등록 전에는 -1임
            }
            isRecruited // 모집여부
        */
  };
  // 프로젝트 삭제
  const onClickDeleteProject = async () => {
    const accessToken = sessionStorage.getItem("accessToken");
    try {
      await apiInstance.put(`/project/delete/${projectId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
        },
      });
      alert("삭제 성공");
    } catch {
      alert("삭제 실패");
    }
  };
  // 프로젝트 수정으로 가기 함수
  const onClickGoToProjectModify = () => {
    // userInfo의 userId가 프로젝트 장의 userId와 일치하면
    if (parseInt(project.userId) === parseInt(userInfo.userId)) {
      // 프로젝트 수정 페이지로 이동
      navigate(
        `/entmain/${entId}/${entMasterId}/${entName}/entprojectmodify/${projectId}`
      );
    } else {
      alert("권한이 없습니다!");
    }
  };
  // 프로젝트 멤버 초대로 가기 함수
  const onClickGoToProjectMemberInvite = () => {
    // userInfo의 userId가 프로젝트 장의 userId와 일치하면
    if (parseInt(project.userId) === parseInt(userInfo.userId)) {
      // 프로젝트 멤버 초대로 이동
      navigate(
        `/entmain/${entId}/${entMasterId}/${entName}/entprojectmemberinvite/${projectId}`
      );
    } else {
      alert("권한이 없습니다!");
    }
  };

  return (
    <div className="pjtMainWrap">
      <div className="pjtMainHeader">
        <img src={project.projectImg} class="card__image" alt="" />
        <div className="pjtMainHeaderRight">
          <div>
            <span className="isRecruitedTitle">
              {project.isRecruited ? <h3>[모집 중]</h3> : <h3>[모집 마감]</h3>}
            </span>
            <h3 className="singName">{project.singName}</h3>
          </div>
          <div className="pjtMainHeaderRight_btm">
            <h3 className="singerName">{project.singerName}</h3>
            <h3>{project.projectName}</h3>
          </div>
        </div>
      </div>
      <div className="entStudioEnter">
        <i class="fi fi-br-play-alt"></i>
        <Link
          to={`/entproject/studio/${entId}/${entMasterId}/${entName}/${projectId}`}>
          <div className="studioBtn">스튜디오 입장</div>
        </Link>
      </div>
      <div className="pjtinfoWrap">
        <h3 className="pjtinfotext">{project.projectInfo}</h3>
        <div className="pjtPartMember">
          <span>파트</span>
          <span>멤버</span>
        </div>
        <span class="pjtPartNickWrap">
          {project.partList ? (
            project.partList.map((part) => (
              <div className="pjtPart_nickname">
                <div className="pjtPartTitle"> {part.partName}</div>
                <div className="pjtNicknameTitle"> {part.nickname}</div>
              </div>
            ))
          ) : (
            <div>파트가 없습니다.</div>
          )}
        </span>
      </div>
      <div className="pjtClick">
        <div
          className="pjtClickbtn pjtClickbtnDelete"
          onClick={onClickDeleteProject}>
          삭제
        </div>
        <div
          className="pjtClickbtn pjtClickbtnModify"
          onClick={onClickGoToProjectModify}>
          수정
        </div>
        <div
          className="pjtClickbtn pjtClickbtnModify"
          onClick={onClickGoToProjectMemberInvite}>
          멤버 초대
        </div>
      </div>
    </div>
  );
};

export default EntProjectMain;
