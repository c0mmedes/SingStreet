import { Link, Route, Routes, Navigate } from "react-router-dom";
import Ent from "./components/ent/Ent";
import Home from "./components/Home";
import Chart from "./components/chart/Chart";
import Music from "./components/work/Music";
import LoginContainer from "./containers/user/LoginContainer";
import Register from "./components/user/Register";
import MypageContainer from "./containers/user/MypageContainer";
import React from "react";
import EntCreateContainer from "./containers/ent/EntCreateContainer";
import EntMain from "./components/ent/EntMain";
import EntApplyContainer from "./containers/ent/EntApplyContainer";
import EntApplicantsContainer from "./containers/ent/EntApplicantsContainer"
import "./css/App.css";
import { connect } from "react-redux";
import MyEntListContainer from "./containers/user/MyEntListContainer";
import EntNavContainer from "./containers/layout/EntNavContainer";

const mapStateToProps = (state) => ({
	userInfo: state.user.userInfo,
	isLogin: state.user.isLogin,
});

function App({ isLogin }) {
	return (
		<Routes>
			<Route path="/" element={<Home />} />
			<Route path="/ent" element={<Ent />} />
			<Route path="/chart" element={<Chart />} />
			<Route path="/music" element={<Music />} />
			<Route path="/register" element={<Register />} />
			<Route path="/login" element={<LoginContainer />} />
			{/* 동적으로 컴포넌트 선택 */}
			<Route
				path="/mypage"
				element={
					// isLogin 상태에 따라 컴포넌트를 선택
					isLogin ? <MypageContainer /> : <Navigate to="/login" />
				}
			/>
				<Route
				path="/myentlist"
				element={
					// isLogin 상태에 따라 컴포넌트를 선택
					isLogin ? <MyEntListContainer /> : <Navigate to="/login" />
				}
			/>
			<Route
				path="/entcreate"
				element={
					// isLogin 상태에 따라 컴포넌트를 선택
					isLogin ? <EntCreateContainer /> : <Navigate to="/login" />
				}
			/>
		
		
		</Routes>
	);
}

export default connect(mapStateToProps, null)(App);
