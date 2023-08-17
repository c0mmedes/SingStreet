import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/work/MusicDetail.css";

const MusicDetail = ({ isOpen, onClose, projectId }) => {
	const [project, setProject] = useState([]);

	// axios 인스턴스
	const apiInstance = api();

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
			<button className="close-button" onClick={onClose}>
				닫기
			</button>
			<div className="modal-content">
				{/* 모달 내용 */}
				<h2>모달 창 내용 - Project ID: {projectId}</h2>
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
