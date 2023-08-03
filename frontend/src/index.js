import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import App from "./App";
import HeaderContainer from "./containers/user/HeaderContainer";
import Footer from "./components/layout/Footer.js";
import rootReducer from "./modules/index";
import { composeWithDevTools } from "@redux-devtools/extension"

const store = createStore(rootReducer, composeWithDevTools())

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <Provider store={store}>
      <HeaderContainer />
      <App />
      {/* <Footer/> */}
    </Provider>
  </BrowserRouter>
);
