import React from "react";
import { connect } from "react-redux";
import {
  addUserInfo,
  setIsLogin,
  addToMyEntList,
} from "../../modules/user/user";
import MypageInvitedProject from "../../components/user/MypageInvitedProject";

const MypageInvitedProjectContainer = ({
  isLogin,
  userInfo,
  addUserInfo,
  myEntList,
  setIsLogin,
  addToMyEntList,
}) => {
  return (
    <MypageInvitedProject
      userInfo={userInfo}
      isLogin={isLogin}
      addUserInfo={addUserInfo}
      myEntList={myEntList}
      setIsLogin={setIsLogin}
      addToMyEntList={addToMyEntList}></MypageInvitedProject>
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
)(MypageInvitedProjectContainer);
