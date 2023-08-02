// LoginContainer는
// Login 컴포넌트를 리덕스와 연결하여 리덕스 액션을 props로 전달하는 역할.
// mapDispatchToProps 객체를 통해 setUser 액션 생성자 함수를 props로 매핑해줌.

import {connect} from "react-redux";
import {setUser} from "../../modules/user/user";
import Login from "../../components/user/Login";

const mapDispatchToProps = {
    setUser,
};

// Login 컴포넌트에선 리덕스 스토어의 상태를 사용하지않음
// => null
export default connect(null, mapDispatchToProps)(Login);