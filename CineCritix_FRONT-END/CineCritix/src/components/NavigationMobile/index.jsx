import React, { useEffect, useState } from "react";
import * as styles from "../NavigationMobile/NavigationMobile.module.css";
import { useNavigate } from "react-router-dom";

export default function NavigationMobile() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const navigation = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, []);
  return (
    <>
      <div className={styles.triagle}></div>
      <div className={styles.menu}>
        <h2 className={styles.movie}>Filmes</h2>
        <div className={styles.positionEmBreve}>
          <p className={styles.emBreve}>Em Breve</p>
          <h2 className={styles.serie}>Serie</h2>
        </div>
        {isAuthenticated ? (
          <h2
            className={styles.account}
            onClick={() => navigation("/minhaConta")}
          >
            Minha Conta
          </h2>
        ) : (
          <h2 className={styles.login} onClick={() => navigation("/login")}>
            Login
          </h2>
        )}
      </div>
    </>
  );
}
