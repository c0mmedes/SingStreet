import React from "react";
import ReactDOM from "react-dom/client";

import { BrowserRouter } from "react-router-dom";

import App from "./App";
import Header from "./components/layout/Header.js";
import Footer from "./components/layout/Footer.js";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <Header />
    <App />
    {/* <Footer/> */}
  </BrowserRouter>
);
