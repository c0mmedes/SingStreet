import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import MusicDetail from "../../components/work/MusicDetail";

const MusicDetailContainer = ({ userInfo, isLogin, addUserInfo, setIsLogin }) => {
	return (
		<MusicDetail
			userInfo={userInfo}
			isLogin={isLogin}
			addUserInfo={addUserInfo}
			setIsLogin={setIsLogin}
		></MusicDetail>
	);
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

export default connect(mapStateToProps, mapDispatchToProps)(MusicDetailContainer);
