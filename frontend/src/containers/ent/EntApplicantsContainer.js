import React from "react";
import { connect } from "react-redux";
import {
  addUserInfo,
  setIsLogin,
  addToMyEntList,
} from "../../modules/user/user";
import EntApplicants from "../../components/ent/EntApplicants";

const EntApplicantsContainer = ({
  userInfo,
  addUserInfo,
  myEntList,
  setIsLogin,
  addToMyEntList,
}) => {
  return (
    <EntApplicants
      userInfo={userInfo}
      addUserInfo={addUserInfo}
      myEntList={myEntList}
      setIsLogin={setIsLogin}
      addToMyEntList={addToMyEntList}></EntApplicants>
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
)(EntApplicantsContainer);
