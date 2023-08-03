// 액션 타입 정의
const SET_USER = "user/SET_USER";
const SET_ISLOGIN = "user/SET_ISLOGIN";
// 액션 생성자 함수 정의
export const setUser = (user) => ({
    type: SET_USER,
    payload: user,
});
export const setIsLogin = () => ({
    type: SET_ISLOGIN,
});

// 초기 상태 정의
const initialState ={
    user: null,
    isLogin: false,
};

// 리듀서 함수 정의
const userReducer = (state = initialState, action) =>{
    switch(action.type){
        case SET_USER:
            return {
                ...state,
                user: action.payload,
            };
        case SET_ISLOGIN:
            return{
                isLogin: !state.isLogin,
            }; 
        default:
            return state;
    }
};

export default userReducer;