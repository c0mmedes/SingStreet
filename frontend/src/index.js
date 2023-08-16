import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { createStore } from "redux";
import { persistStore } from "redux-persist";
import { Provider } from "react-redux";
import App from "./App";
import HeaderContainer from "./containers/layout/HeaderContainer";
import Footer from "./components/layout/Footer.js";
import rootReducer from "./modules/index";
import { composeWithDevTools } from "@redux-devtools/extension";
import { PersistGate } from "redux-persist/integration/react";
import "./css/index.css";
const store = createStore(rootReducer, composeWithDevTools());
const persistor = persistStore(store);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <HeaderContainer />
        <App />
        <Footer />
      </PersistGate>
    </Provider>
  </BrowserRouter>
);
