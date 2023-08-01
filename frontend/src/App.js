import { Link, Route, Routes } from "react-router-dom";
import Ent from "./routes/Ent";
import Home from "./routes/Home";
import Chart from "./routes/Chart";
import Music from "./routes/Music";
import Login from "./routes/Login";
import Register from "./routes/Register";

import "./style/App.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/ent" element={<Ent />} />
      <Route path="/chart" element={<Chart />} />
      <Route path="/music" element={<Music />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
    </Routes>
  );
}

export default App;
