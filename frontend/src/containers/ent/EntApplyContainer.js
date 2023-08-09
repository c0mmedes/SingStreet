// EntApplyContainer는
// EntApply 컴포넌트를 리덕스와 연결하여 리덕스 action,state를 props로 전달하는 역할.
// mapDispatchToProps,mapStateToProps 객체를 통해 isLogin상태와 ,setIsLogin 액션 생성자 함수를 props로 매핑해줌.
import React from "react";
import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import EntApply from "../../components/ent/EntApply";

const EntApplyContainer = ({ userInfo, isLogin, setIsLogin }) => {
  return (
    <EntApply
      userInfo={userInfo}
      isLogin={isLogin}
      setIsLogin={setIsLogin}></EntApply>
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

// Login 컴포넌트에선 dispatch만 사용하고 리덕스 '스토어의 상태'를 사용하지않음
// => null
export default connect(mapStateToProps, mapDispatchToProps)(EntApplyContainer);
