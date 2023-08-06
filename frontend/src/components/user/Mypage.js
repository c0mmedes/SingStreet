import React from "react";

const Mypage = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
	return (
		<div>
			마이페이지
			<div>{isLogin}</div>
		</div>
	);
};

export default Mypage;
