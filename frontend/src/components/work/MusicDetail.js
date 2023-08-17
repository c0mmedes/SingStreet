// MusicDetail.js
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/work/MusicDetail.css";

const MusicDetail = ({ isOpen, onClose, projectId }) => {
	const [project, setProject] = useState([]);
	// axios 인스턴스
	const apiInstance = api();

	const onCloseModal = () => {
		setProject([]); // 모달을 닫을 때 project 상태 초기화
		onClose(); // 원래의 onClose 함수 호출
	};

	const getProject = async () => {
		try {
			const accessToken = sessionStorage.getItem("accessToken");
			const res = await apiInstance.get(`project/detail/${projectId}`, {
				headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
				},
			});
			/*
            res.data
            [
                {
                  "applId": 0,
                  "createAt": "2023-08-07T07:34:01.357Z",
                  "nickname": "string",
                  "userId": 0
                }
            ] 
      */
			console.log(res.data);
			setProject(res.data);
		} catch {
			alert("작품 정보 불러오기 실패!");
		}
	};

	useEffect(() => {
		if (isOpen) {
			getProject();
		}
	}, [isOpen]);

	if (!isOpen) {
		return null;
	}

	return (
		<div className="modal">
			<div className="modal-content">
				<button className="close-button" onClick={onCloseModal}>
					닫기
				</button>
				{/* 모달 내용 */}
				<h2>프로젝트 명 : {project.projectName}</h2>
				{/* <img src={project.projectImg} alt="프로젝트 이미지" /> */}
				<h2>원곡 가수: {project.singerName}</h2>
				<h2>원곡 노래 : {project.singName}</h2>
				<div className="video-container">
					{/* Conditional rendering을 사용하여 재생 가능한 미디어 플레이어를 나타냄 */}
					{project.originFilename ? (
						<video controls>
							<source src={project.originFilename} type="video/mp4" />
							<source src={project.originFilename} type="audio/mpeg" />
							브라우저가 미디어를 지원하지 않습니다.
						</video>
					) : (
						<p>재생 가능한 미디어 파일이 없습니다.</p>
					)}
				</div>
			</div>
		</div>
	);
};

export default MusicDetail;

/* //라우터 방식으로 구현했을때 쓰려했던거
const MusicDetail = () => {
	// axios 인스턴스
	const apiInstance = api();
	// 라우터 파라미터에서 가져올 entId 변수
	const { projectId } = useParams();

	return <div></div>;

    export default MusicDetail;
}; */
