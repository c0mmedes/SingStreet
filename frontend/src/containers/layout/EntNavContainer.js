import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin, addToMyEntList } from "../../modules/user/user";
import EntNav from "../../components/layout/EntNav";

const EntNavContainer = ({
	entId,
	entMasterId,
	entName,
	userInfo,
	addUserInfo,
	myEntList,
	setIsLogin,
	addToMyEntList,
}) => {
	return (
		<EntNav
			entId={entId}
			entMasterId={entMasterId}
			entName={entName}
			userInfo={userInfo}
			addUserInfo={addUserInfo}
			myEntList={myEntList}
			setIsLogin={setIsLogin}
			addToMyEntList={addToMyEntList}
		></EntNav>
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

export default connect(mapStateToProps, mapDispatchToProps)(EntNavContainer);
