import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from "react-router-dom";
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
        </div>
    );
};

export default EntProjectMain;