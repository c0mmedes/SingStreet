// LoginContainer는
// Login 컴포넌트를 리덕스와 연결하여 리덕스 액션을 props로 전달하는 역할.
// mapDispatchToProps 객체를 통해 addUserInfo 액션 생성자 함수를 props로 매핑해줌.
import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import EntCreate from "../../components/ent/EntCreate";

const EntCreateContainer = ({ userInfo, isLogin, addUserInfo, setIsLogin }) => {
  return (
    <EntCreate
      userInfo={userInfo}
      isLogin={isLogin}
      addUserInfo={addUserInfo}
      setIsLogin={setIsLogin}></EntCreate>
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

export default connect(mapStateToProps, mapDispatchToProps)(EntCreateContainer);
