//src/components/ent/EntMain.js
import React, { useEffect, useState } from "react";
import "../../css/ent/EntMain.css";
import { Outlet, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import EntNavContainer from "../../containers/layout/EntNavContainer";
import ChatContainer from "../../containers/chat/ChatContainer";

const EntMain = () => {
	// entMain 라우터 경로에 있는 param인 entId, entMasterId, entName를 저장하는 변수
	const { entId, entMasterId, entName } = useParams();
	// 엔터 정보를 담고 있는 객체
	const [ent, setEnt] = useState({});
	// axios 객체
	const apiInstance = api();

	useEffect(() => {
		getEnt();
	}, []);

	//[API] 엔터 정보를 가져오는 함수
	const getEnt = async () => {
		const res = await apiInstance.get(`/ent/${entId}`);
		const newEnt = { ...res.data }; // 새로운 객체를 생성하고 res.data의 내용을 복사
		setEnt(newEnt);
		// "entId": 1,
		// "userId": "엔터장 아이디",
		// "entName": "qwe",
		// "entImg": "qwe",
		// "entInfo": "qwe",
		// "tagNameList": [],
		// "autoAccepted": true
	};

	return (
		<div>
			<div className="video-app">
				<div className="wrapper">
					<EntNavContainer entId={entId} entMasterId={entMasterId} entName={entName} />
					<div className="main-container">
						<Outlet />
					</div>
					<ChatContainer entId={entId} />
				</div>
			</div>
		</div>
	);
};

export default EntMain;
