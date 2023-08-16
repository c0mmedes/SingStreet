import React from "react";
import { Link } from "react-router-dom";
import "../css/Home.css";
import myImage from "../assets/sample1.png";
import myImage2 from "../assets/sample2.png";
import mainGif from "../assets/메인영상.gif";
const Home = () => {
  const backgroundStyle = {
    backgroundImage: `url(${mainGif})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
    height: "100vh", // 높이를 화면 높이로 설정
    width: "100vw", // 너비를 화면 너비로 설정
  };
  return (
    <div style={backgroundStyle}>
      <Link to="/chat">
        <div>상우기의 채팅상담@@@@@@@@@@@@@@@@@@@@@@</div>
      </Link>
      <Link to="/studio">
        <div>studio</div>
      </Link>
    </div>
  );
};

export default Home;
