import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import MyInfo from "../../components/user/MyInfo";

const  MyInfoContainer = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
  return (
    <MyInfo
      isLogin={isLogin}
      userInfo={userInfo}
      addUserInfo={addUserInfo}
      setIsLogin={setIsLogin}>
    </MyInfo>
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

export default connect(mapStateToProps, mapDispatchToProps)(MyInfoContainer);
