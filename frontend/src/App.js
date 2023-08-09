import React from "react";
import { Link, Route, Routes, Navigate } from "react-router-dom";
import "./css/App.css";
import { connect } from "react-redux";
// 헤더
import Ent from "./components/ent/Ent";
import Home from "./components/Home";
import Chart from "./components/chart/Chart";
import Music from "./components/work/Music";
// 로그인 / 회원가입
import LoginContainer from "./containers/user/LoginContainer";
import Register from "./components/user/Register";
// 마이페이지
import MypageContainer from "./containers/user/MypageContainer";
<<<<<<< HEAD
import MyEntList from "./components/user/MyEntList";
import MyEntListContainer from "./containers/user/MyEntListContainer";
=======
import React from "react";
>>>>>>> cdd7d3dfe496fb3a8f4db4ae49f3a0c8358b94f9
// 엔터
import EntCreateContainer from "./containers/ent/EntCreateContainer";
import EntMain from "./components/ent/EntMain";
import EntApplyContainer from "./containers/ent/EntApplyContainer";
import EntApplicantsContainer from "./containers/ent/EntApplicantsContainer";
import EntFeed from "./components/ent/EntFeed";
<<<<<<< HEAD
// 엔터_프로젝트
import EntProjectCreate from "./components/ent/EntProjectCreate";
=======
import MyInfoContainer from "./containers/user/MyInfoContainer";

>>>>>>> cdd7d3dfe496fb3a8f4db4ae49f3a0c8358b94f9
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

      <Route
        path="/mypage/*"
        element={isLogin ? <MypageContainer /> : <Navigate to="/login" />}>
        <Route
          path=""
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <MyInfoContainer /> : <Navigate to="/login" />
          }
        />
        <Route
          path="myentlist"
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <MyEntListContainer /> : <Navigate to="/login" />
          }
        />
        {/* <Route
          path="mymusic"
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <MyMusic /> : <Navigate to="/mymusic" />
          }
        /> */}
      </Route>

      <Route
        path="/entcreate"
        element={
          // isLogin 상태에 따라 컴포넌트를 선택
          isLogin ? <EntCreateContainer /> : <Navigate to="/login" />
        }
      />

      <Route
        path="/entmain/:entId/:entMasterId/:entName/*"
        element={<EntMain />}>
        {/* 중첩된 라우트 설정 */}
        <Route
          path=""
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <EntFeed /> : <Navigate to="/login" />
          }
        />
        <Route
          path="entapply"
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <EntApplyContainer /> : <Navigate to="/login" />
          }
        />
        <Route
          path="entapplicants"
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <EntApplicantsContainer /> : <Navigate to="/login" />
          }
        />
        <Route
          path="entprojectcreate"
          element={
            // isLogin 상태에 따라 컴포넌트를 선택
            isLogin ? <EntApplicantsContainer /> : <Navigate to="/login" />
          }
        />
      </Route>
    </Routes>
  );
}
export default connect(mapStateToProps, null)(App);
