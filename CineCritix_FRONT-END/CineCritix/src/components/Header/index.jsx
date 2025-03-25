import React, { useState } from "react";
import * as styles from "../Header/Header.module.css";
import { FaSearch } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import Logo from "../../assets/Logo.png";

export default function Header() {
  return (
    <header className={styles.header}>
      <img
        src={Logo}
        alt="Logo CineCritix"
        className={styles.logo}
        onClick={() => navigate("/")}
      />
    </header>
  );
}
