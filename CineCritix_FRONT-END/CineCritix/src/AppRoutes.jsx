import { Route, BrowserRouter as Routers, Routes } from "react-router-dom";
import Register from './pages/Register'
import Home from "./pages/Home";
import Login from "./pages/Register";

export default function AppRoutes() {
  return (
    <Routers>
              <Routes>
                <Route exact path="/" element={<Home />} />
                <Route exact path="/register" element={<Register/>} />
                <Route exact path="/login" element={<Login />} />
                {/* <Route exact path="/*" element={<PageNotFound />} /> */}
              </Routes>
    </Routers>
  );
}
