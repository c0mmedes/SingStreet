// LoginContainer는
// Login 컴포넌트를 리덕스와 연결하여 리덕스 액션을 props로 전달하는 역할.
// mapDispatchToProps 객체를 통해 addUserInfo 액션 생성자 함수를 props로 매핑해줌.
import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin, addToMyEntList } from "../../modules/user/user";
import Login from "../../components/user/Login";

const LoginContainer = ({ userInfo, addUserInfo, myEntList, setIsLogin, addToMyEntList }) => {
	return (
		<Login
			userInfo={userInfo}
			addUserInfo={addUserInfo}
			myEntList={myEntList}
			setIsLogin={setIsLogin}
			addToMyEntList={addToMyEntList}
		></Login>
	);
};
const mapStateToProps = (state) => ({
	userInfo: state.user.userInfo,
	isLogin: state.user.isLogin,
	myEntList: state.user.myEntList,
});
const mapDispatchToProps = (dispatch) => ({
	addUserInfo: (userInfo) => {
		dispatch(addUserInfo(userInfo));
	},
	setIsLogin: () => {
		dispatch(setIsLogin());
	},
	addToMyEntList: (item) => {
		dispatch(addToMyEntList(item));
	},
});

// Login 컴포넌트에선 dispatch만 사용하고 리덕스 '스토어의 상태'를 사용하지않음
// => null
// 혹시 쓸수도 있어서 바꿨음
// => mapStateToProps
export default connect(mapStateToProps, mapDispatchToProps)(LoginContainer);
