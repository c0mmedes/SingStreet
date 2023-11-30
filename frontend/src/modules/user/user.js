// 액션 타입 정의
const ADD_USERINFO = "user/ADD_USERINFO";
const SET_ISLOGIN = "user/SET_ISLOGIN";
const LOGOUT = "user/LOGOUT";
const ADD_TO_MY_ENT_LIST = "user/ADD_TO_MY_ENT_LIST";
const REMOVE_FROM_MY_ENT_LIST = "user/REMOVE_FROM_MY_ENT_LIST";
// 액션 생성자 함수 정의
export const addUserInfo = (userInfo) => ({
  type: ADD_USERINFO,
  payload: userInfo,
});
export const setIsLogin = () => ({
  type: SET_ISLOGIN,
});
export const logout = () => ({
  type: LOGOUT,
});
export const addToMyEntList = (item) => ({// 엔터목록을 받아와서 그걸로 대체
  type: ADD_TO_MY_ENT_LIST,
  payload: item,
});
export const removeFromMyEntList = (item) => ({
  type: REMOVE_FROM_MY_ENT_LIST,
  payload: item,
});

// 초기 상태 정의
const initialState = {
  userInfo: {
    email: "",
    gender: "",
    nickname: "",
    userImg: "",
    userId: "",
  },
  isLogin: false,
  myEntList: [],
};

// 리듀서 함수 정의
const user = (state = initialState, action) => {
  switch (action.type) {
    case ADD_USERINFO: 
      return {
        ...state,
        // userInfo: { ...action.data }
        userInfo: { ...action.payload },
      };
    case SET_ISLOGIN:
      return {
        ...state,
        isLogin: !state.isLogin,
      };
    case LOGOUT:
      return initialState; // 상태를 초기 상태로 리셋
    case ADD_TO_MY_ENT_LIST:// 엔터목록을 받아와서 그걸로 대체
      return {
        ...state,
        myEntList: Array.isArray(action.payload)
          ? action.payload // 배열 전체를 대체
          : [action.payload], // 단일 항목을 배열에 추가
      };
    case REMOVE_FROM_MY_ENT_LIST:
      return {
        ...state,
        myEntList: state.myEntList.filter((item) => item !== action.payload),
      };
    default:
      return state;
  }
};

export default user;
