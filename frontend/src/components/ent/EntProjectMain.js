import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectMain.css";

const EntProjectMain = () => {
  // useState
  const [project, setProject] = useState({});
  // 라우터 경로로 받아오는 정보들
  const { entId, entMasterId, entName, projectId } = useParams();
  // 비동기 통신을 위한 apiInstance 생성
  const apiInstance = api();
  // useEffect
  useEffect(() => {
    getProject();
  }, []);

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

  return (
    <div>
      <div className="pjtMainHeader">
        <img src={project.projectImg} class="card__image" alt="" />
        <div className="pjtMainHeaderRight">
          {project.isRecruited ? <h3>[모집 중]</h3> : <h3>[모집 마감]</h3>}
          <h3 class="card__title">프로젝트 명: {project.projectName}</h3>
          <div className="pjtMainHeaderRight_btm">
            <h3>singerName: {project.singerName}</h3>
            <h3>singName: {project.singName}</h3>
          </div>
        </div>
      </div>
      <Link
        to={`/entproject/studio/${entId}/${entMasterId}/${entName}/${projectId}`}>
        <div>스튜디오 입장</div>
      </Link>

      <h3>projectInfo: {project.projectInfo}</h3>
      <span class="card__status">
        {project.partList ? (
          project.partList.map((part) => (
            <b>
              파트 : {part.partName} - {part.nickname}
            </b>
          ))
        ) : (
          <div>파트가 없습니다.</div>
        )}
      </span>
      <div onClick={onClickDeleteProject}>프로젝트 삭제</div>
      <Link
        to={`/entmain/${entId}/${entMasterId}/${entName}/entprojectmodify/${projectId}`}>
        <div>프로젝트 수정</div>
      </Link>
    </div>
  );
};

export default EntProjectMain;
