import { combineReducers } from "redux";
import { persistReducer } from "redux-persist"; // load
import storageSession from "redux-persist/lib/storage/session";  // sessionStorage
// import storage from 'redux-persist/lib/storage // localstorage
import user from './user/user';

const persistConfig = {
    key: "root",
    storage: storageSession, // 사용할 스토리지를 정의해요.
    whitelist: ["isLogin"], // 유지 할 데이터를 정의해요
  };

const rootReducer = combineReducers({
    user,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);
// reducer와 위에서 설정 한 persist설정을 합쳐요

export default persistedReducer;