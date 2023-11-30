import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import Chat from "../../components/chat/Chat";

const ChatContainer = ({ userInfo, isLogin, setIsLogin, entId }) => {
	return <Chat userInfo={userInfo} isLogin={isLogin} setIsLogin={setIsLogin} propsEntId={entId}></Chat>;
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
});

// Login 컴포넌트에선 dispatch만 사용하고 리덕스 '스토어의 상태'를 사용하지않음
// => null
export default connect(mapStateToProps, mapDispatchToProps)(ChatContainer);
