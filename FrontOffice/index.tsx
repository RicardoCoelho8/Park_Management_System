import React from "react";
import ReactDOM from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "leaflet/dist/leaflet.css";

import { Provider } from "react-redux";
import { store } from "./src/store/store";
import { App } from "./src/screens";

document.body.style.backgroundColor = "#fff7e1";
ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById("root")
);
