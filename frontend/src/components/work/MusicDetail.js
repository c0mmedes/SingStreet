import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/work/MusicDetail.css";

/* //라우터 방식으로 구현했을때 쓰려했던거
const MusicDetail = () => {
	// axios 인스턴스
	const apiInstance = api();
	// 라우터 파라미터에서 가져올 entId 변수
	const { projectId } = useParams();

	return <div></div>;

    export default MusicDetail;
}; */

const MusicDetail = ({ isOpen, onClose }) => {
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
				<h2>모달 창 내용</h2>
			</div>
		</div>
	);
};

export default MusicDetail;
