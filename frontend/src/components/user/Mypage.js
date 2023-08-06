import React from "react";

const Mypage = ({ userInfo, addUserInfo, setIsLogin }) => {
	return (
		<div>
			마이페이지
			<div>{userInfo && userInfo.nickname && <div>{userInfo.nickname}</div>}</div>
		</div>
	);
};

export default Mypage;
