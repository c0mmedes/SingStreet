import React from "react";
import ReactDOM from "react-dom/client";

import { BrowserRouter } from "react-router-dom";

import App from "./App";
import Header from "./layout/Header";
import Footer from "./layout/Footer";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <Header />
    <App />
    {/* <Footer/> */}
  </BrowserRouter>
);
