// HeaderContainer는
// Header 컴포넌트를 리덕스와 연결하여 리덕스 액션을 props로 전달하는 역할.
// mapDispatchToProps 객체를 통해 setUser 액션 생성자 함수를 props로 매핑해줌.
import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin, logout } from "../../modules/user/user";
import Header from "../../components/layout/Header";

const HeaderContainer = ({ isLogin, setIsLogin, logout }) => {
	return <Header isLogin={isLogin} setIsLogin={setIsLogin} logout={logout}></Header>;
};

const mapStateToProps = (state) => ({
	userInfo: state.user.userInfo,
	isLogin: state.user.isLogin,
});
const mapDispatchToProps = (dispatch) => ({
	addUserInfo: (userInfo) => {
		dispatch(addUserInfo(userInfo));
	},
	setIsLogin: () => {
		dispatch(setIsLogin());
	},
	logout: () => {
		dispatch(logout());
	},
});

// Login 컴포넌트에선 dispatch만 사용하고 리덕스 '스토어의 상태'를 사용하지않음
// => null
export default connect(mapStateToProps, mapDispatchToProps)(HeaderContainer);
