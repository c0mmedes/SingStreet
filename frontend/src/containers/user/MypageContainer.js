import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import Mypage from "../../components/user/Mypage";

const MypageContainer = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
  return (
    <Mypage
      isLogin={isLogin}
      userInfo={userInfo}
      addUserInfo={addUserInfo}
      setIsLogin={setIsLogin}></Mypage>
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

export default connect(mapStateToProps, mapDispatchToProps)(MypageContainer);
