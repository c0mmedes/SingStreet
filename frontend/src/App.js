import { Link, Route, Routes } from "react-router-dom";
import Ent from "./components/ent/Ent";
import Home from "./components/Home";
import Chart from "./components/chart/Chart";
import Music from "./components/work/Music";
import LoginContainer from "./containers/user/LoginContainer";
import Register from "./components/user/Register";
import Mypage from "./components/user/Mypage";
import React, { useState } from "react";
import Entcreate from "./components/ent/EntCreate";
import Entmain from "./components/ent/EntMain";
import "./css/App.css";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태를 저장하는 state

  // 로그인 성공 시 호출되는 함수
  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  };

  //   // 로그아웃 시 호출되는 함수
  //   const handleLogout = () => {
  //     setIsLoggedIn(false);
  //   };
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/ent" element={<Ent />} />
      <Route path="/chart" element={<Chart />} />
      <Route path="/music" element={<Music />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/login"
        element={<LoginContainer onLoginSuccess={handleLoginSuccess} />}
      />
      <Route path="/mypage" element={<Mypage isLoggedIn={isLoggedIn} />} />
      <Route path="/entcreate" element={<Entcreate />} />
      <Route path="/entmain" element={<Entmain />} />
    </Routes>
  );
}

export default App;
