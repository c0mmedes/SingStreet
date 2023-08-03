import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import App from "./App";
import Header from "./components/layout/Header.js";
import Footer from "./components/layout/Footer.js";
import rootReducer from "./modules/index";

const store = createStore(rootReducer)

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <Provider store={store}>
      <Header />
      <App />
      {/* <Footer/> */}
    </Provider>
  </BrowserRouter>
);
