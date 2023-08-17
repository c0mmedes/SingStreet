import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";

const MusicDetail = () => {
	// axios 인스턴스
	const apiInstance = api();
	// 라우터 파라미터에서 가져올 entId 변수
	const { projectId } = useParams();

	return <div></div>;
};

export default MusicDetail;
