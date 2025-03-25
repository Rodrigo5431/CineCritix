import React from "react";
import * as styles from "../Footer/Footer.module.css";
import { FaGithub, FaInstagram, FaLinkedin } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

export default function Footer() {

  return (
    <footer className={styles.footer}>
      <h3 className={styles.title}>Desenvolvido por</h3>
      <div className={styles.positionUser}>
        <div className={styles.user}>
          <h3>Davi</h3>
          <div className={styles.icons}>
          <a href="https://github.com/DaviSoaresA" className={styles.icon}>
            <FaGithub />
          </a>
          <a href="https://www.instagram.com/davisa_04/" className={styles.icon}>
            <FaInstagram />
          </a>
          <a href="https://www.linkedin.com/in/davi-soares-antunes-17a652293/" className={styles.icon}>
            <FaLinkedin />
          </a>
          </div>
        </div>

        <div className={styles.user}>
          <h3>Patrick</h3>
          <div className={styles.icons}>
          <a href="https://github.com/pckzin01" className={styles.icon}>
            <FaGithub />
          </a>
          <a href="https://www.instagram.com/patrickpaivagoncalves/" className={styles.icon}>
            <FaInstagram />
          </a>
          <a href="https://www.linkedin.com/in/patrick-gonçalves-66621b1b9/" className={styles.icon}>
            <FaLinkedin />
          </a>
          </div>
        </div>

        <div className={styles.user}>
          <h3>Rodrigo</h3>
          <div className={styles.icons}>
          <a href="https://github.com/Rodrigo5431" className={styles.icon}>
            <FaGithub />
          </a>
          <a href="https://www.instagram.com/rodrigo_karvalho/" className={styles.icon}>
            <FaInstagram />
          </a>
          <a href="https://www.linkedin.com/in/devrodrigo-carvalho/" className={styles.icon}>
            <FaLinkedin />
          </a>
          </div>
        </div>
      </div>
      <h3 className={styles.contact}>Contate-nos!</h3>
      <div className={styles.line}>
        ©2024 CineCritix. Todos os direitos reservados
        <a href="">. Termos de Uso</a> | <a href="">Politica de Privacidade</a>
      </div>
    </footer>
  );
}
