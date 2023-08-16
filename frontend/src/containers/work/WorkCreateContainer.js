// LoginContainer는
// Login 컴포넌트를 리덕스와 연결하여 리덕스 액션을 props로 전달하는 역할.
// mapDispatchToProps 객체를 통해 addUserInfo 액션 생성자 함수를 props로 매핑해줌.
import { connect } from "react-redux";
import { addUserInfo, setIsLogin } from "../../modules/user/user";
import WorkCreate from "../../components/work/WorkCreate";

const WorkCreateContainer = ({
  userInfo,
  isLogin,
  addUserInfo,
  setIsLogin,
}) => {
  return (
    <WorkCreate
      userInfo={userInfo}
      isLogin={isLogin}
      addUserInfo={addUserInfo}
      setIsLogin={setIsLogin}></WorkCreate>
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WorkCreateContainer);
