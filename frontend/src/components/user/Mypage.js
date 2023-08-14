import React, { useEffect, useState } from "react";
import "../../css/user/Mypage.css";
import { Outlet, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import MypageNavContainer from "../../containers/layout/MypageNavContainer";
const Mypage = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
  const apiInstance = api();

  console.log(userInfo);
  return (
    <div className="myPageMasterWrap">
      <MypageNavContainer />
      {/* 하위 컴포넌트들이 Outlet에 뜰겁니다 */}
      <Outlet />
    </div>
  );
};

export default Mypage;
