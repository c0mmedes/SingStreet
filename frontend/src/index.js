import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import { persistStore } from "redux-persist"; // load
import { PersistGate } from "redux-persist/integration/react"; // load
import App from "./App";
import HeaderContainer from "./containers/user/HeaderContainer";
import Footer from "./components/layout/Footer.js";
import persistedReducer from "./modules/index";
import { composeWithDevTools } from "@redux-devtools/extension"

const store = createStore(persistedReducer, composeWithDevTools())
const persistor = persistStore(store); // 정의

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <HeaderContainer />
        <App />
        {/* <Footer/> */}
      </PersistGate>
      </Provider>
  </BrowserRouter>
);
