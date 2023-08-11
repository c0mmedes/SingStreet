import React, { useEffect, useState } from 'react';
import {  useParams, Link } from "react-router-dom";
import {api} from "../../services/httpService";

const EntProjectMain = () => {
    // useState
    const [project, setProject] = useState({});
    // 라우터 경로로 받아오는 정보들
    const { entId, entMasterId, entName, projectId } = useParams();
    // 비동기 통신을 위한 apiInstance 생성 
    const apiInstance = api();
    // useEffect 
    useEffect(()=>{
        getProject();
    }, []);

    const getProject = async () => {
        const res = await apiInstance.get(`/project/info/${projectId}`);
        console.log(res.data);
        const newProject = {...res.data}; // 새로운 객체를 생성하고 res.data의 내용을 복사 
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
            recruited // 모집여부
        */
    };

    return (
        <div>
            <Link to={`/entproject/studio/${entId}/${entMasterId}/${entName}/${projectId}`}>
                <div>스튜디오 입장</div>
            </Link>
            <img src={project.projectImg} class="card__image" alt="" />
            <h3 class="card__title">프로젝트 명: {project.projectName}</h3>
            { project.recruited?
            <h3>모집 중</h3> : <h3>모집 마감</h3>
            }
            <h3>singName: {project.singName}</h3>
            <h3>singerName: {project.singerName}</h3>
            <h3>projectInfo: {project.projectInfo}</h3>
            <span class="card__status">
            {project.partList? project.partList.map((part) => (
                <b>파트 : {part.partName} - {part.nickname}</b>
            )) : <div>파트가 없습니다.</div>}
            </span>
        </div>      
    );
};

export default EntProjectMain;