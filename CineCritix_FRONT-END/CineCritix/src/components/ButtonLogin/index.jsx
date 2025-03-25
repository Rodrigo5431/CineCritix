import React from 'react'
import * as styles from "../ButtonLogin/ButtonLogin.module.css";
import "../../Global.css"

export default function ButtonLogin({ name}) {
  return (
    <button className={styles.button}>
        {name}
    </button>
  )
}
