import { Link, Route, Routes, Navigate  } from "react-router-dom";
import Ent from "./components/ent/Ent";
import Home from "./components/Home";
import Chart from "./components/chart/Chart";
import Music from "./components/work/Music";
import LoginContainer from "./containers/user/LoginContainer";
import Register from "./components/user/Register";
import Mypage from "./components/user/Mypage";
import React from "react";
import Entcreate from "./components/ent/EntCreate";
import Entmain from "./components/ent/EntMain";
import "./css/App.css";
import { connect } from "react-redux";

const mapStateToProps = (state) => ({
	userInfo: state.user.userInfo,
	isLogin: state.user.isLogin,
});


function PrivateRoute({ component: Component, isLogin, ...rest }) {
  return (
    <Route
      {...rest}
      element={isLogin ? <Component /> : <Navigate to="/login" />}
    />
  );
}

function App({ isLogin }) {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/ent" element={<Ent />} />
      <Route path="/chart" element={<Chart />} />
      <Route path="/music" element={<Music />} />
      <Route path="/register" element={<Register />} />
      <Route path="/login" element={<LoginContainer />} />
      <PrivateRoute path="/mypage" element={<Mypage />} isLogin={isLogin} />
      <PrivateRoute path="/entcreate" element={<Entcreate />} isLogin={isLogin} />
      <Route path="/entmain" element={<Entmain />} />
    </Routes>
  );
}

export default connect(mapStateToProps, null)(App);

