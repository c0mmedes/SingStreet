// LoginContainer는
// Login 컴포넌트를 리덕스와 연결하여 리덕스 액션을 props로 전달하는 역할.
// mapDispatchToProps 객체를 통해 addUserInfo 액션 생성자 함수를 props로 매핑해줌.
import { connect } from "react-redux";
import { addUserInfo, setIsLogin, addToMyEntList } from "../../modules/user/user";
import EntProjectCreate from "../../components/ent/EntProjectCreate";

const EntProjectCreateContainer = ({ userInfo, addUserInfo, myEntList, setIsLogin, addToMyEntList }) => {
	return (
		<EntProjectCreate
			userInfo={userInfo}
			addUserInfo={addUserInfo}
			myEntList={myEntList}
			setIsLogin={setIsLogin}
			addToMyEntList={addToMyEntList}
		></EntProjectCreate>
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EntProjectCreateContainer);
