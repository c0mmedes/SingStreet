import { combineReducers } from "redux";
import { persistReducer } from "redux-persist";
import sessionStorage from "redux-persist/es/storage/session";
// import storage from "redux-persist/lib/storage";
import user from './user/user';

const userPersistConfig = {
    key: "user",
    storage: sessionStorage
  };

const rootReducer = combineReducers({
    user: persistReducer(userPersistConfig, user)
});

export default rootReducer;