import React from "react";

const Mypage = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
	return (
		<div>
			마이페이지
			<div>{isLogin ? <h1>true</h1> : <h1>false</h1>}</div>
		</div>
	);
};

export default Mypage;
