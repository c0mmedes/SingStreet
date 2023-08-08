import axios from "axios";

// 액션 타입 정의
const SET_USER = "user/SET_USER";

// 액션 생성자 함수 정의
export const setUser = (user) => ({
    type: SET_USER,
    payload: user,
});

// 초기 상태 정의
const initialState ={
    user: null,
    
};

// 리듀서 함수 정의
const userReducer = (state = initialState, action) =>{
    switch(action.type){
        case SET_USER:
            return {
                ...state,
                user: action.payload,
            };
        default:
            return state;
    }
};

export default userReducer;
