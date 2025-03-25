import React from "react";
import * as styles from "./TextInputLogin.module.css";
import "../../Global.css"

export default function TextInputLogin({ placeholder }) {
  return (
    <input
      type="text"
      placeholder={placeholder || ""}
      className={styles.textInput}
    />
  );
}
