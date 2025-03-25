import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import { FcGoogle } from "react-icons/fc";
import { FaFacebookSquare } from "react-icons/fa";
import { createClient } from "@supabase/supabase-js";
import Demo from "../../assets/demo.jpg";
import ButtonLogin from "../../components/ButtonLogin";
import "../../Global.css";
import * as styles from "../Login/Login.module.css";
import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { login } from "../../service/api";

const supabase = createClient(
  "https://pyobmpozoesyxoxsfgbq.supabase.co",
  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB5b2JtcG96b2VzeXhveHNmZ2JxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgxNTg5NjQsImV4cCI6MjA1MzczNDk2NH0.RgfIieHPOg9AxVTv0YRm6302m2Bui2OX0BFu3VdzM0M"
);

export default function Login() {
  const navigation = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const [showPassword, setShowPassword] = useState(false);
  const [user, setUser] = useState(null);
  const [Loading, setLoading] = useState(false);
  const [sucess, setSucess] = useState(false);
  const [error, setError] = useState(false);

  useEffect(() => {
    const getUser = async () => {
      const { data, error } = await supabase.auth.getUser();
      if (data?.user) {
        setUser(data.user);
        console.log("Usuário já autenticado:", data.user);
      }
    };
    getUser();
  }, []);

  const loginWithProvider = async (provider) => {
    const { error } = await supabase.auth.signInWithOAuth({
      provider,
      options: {
        redirectTo: window.location.origin,
      },
    });

    if (error) {
      console.error("Erro ao autenticar:", error.message);
    }
  };

  const logout = async () => {
    await supabase.auth.signOut();
    setUser(null);
  };

  const onSubmit = async (data) => {
    const credentials = {
      email: data.email,
      password: data.password
    }
    setLoading(true);
    try {
      const response = await login(credentials);
    if (response.status === 200) {
      setLoading(false);
      setSucess(true);
      setError(false)
      setTimeout(() => {
        navigation("/")
      },1000)
    }
    } catch (error) {
     setError(true); 
    }
  };

  return (
    <motion.main
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <main>
        <div className={styles.container}>
          <div className={styles.positionPicture}>
            <img
              src={Demo}
              alt="coleção de filmes"
              className={styles.picture}
            />
            <div className={styles.box}></div>
          </div>
          <div className={styles.positionForm}>
            <h1>Login</h1>
            <div className={styles.positionIcons}>
              {user ? (
                <button onClick={logout} className={styles.logoutButton}>
                  Sair
                </button>
              ) : (
                <>
                  <div
                    className={styles.icon}
                    onClick={() => loginWithProvider("google")}
                    style={{ cursor: "pointer" }}
                  >
                    <div className={styles.emBreve}><p>Em Breve</p></div>
                    <FcGoogle size={40} />
                  </div>
                  <div
                    className={styles.icon}
                    onClick={() => loginWithProvider("facebook")}
                    style={{ cursor: "pointer" }}
                  >
                    <div className={styles.emBreve}><p>Em Breve</p></div>
                    <FaFacebookSquare size={40} color="#1877F2" />
                  </div>
                </>
              )}
            </div>

            <div className={styles.or}>
              <div className={styles.line}></div>
              <h2>Ou</h2>
              <div className={styles.line}></div>
            </div>

            <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
              <div>
                <input
                  className={styles.textInput}
                  placeholder="Email"
                  {...register("email", {
                    required: "O email é obrigatório",
                    pattern: {
                      value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
                      message: "Formato de email inválido",
                    },
                  })}
                />
                {errors.email && (
                  <p className={styles.error}>{errors.email.message}</p>
                )}
              </div>
              <div style={{ position: "relative" }}>
                <input
                  className={styles.textInput}
                  type={showPassword ? "text" : "password"}
                  placeholder="Senha"
                  {...register("password", {
                    required: "A senha é obrigatória",
                    minLength: {
                      value: 6,
                      message: "A senha deve ter pelo menos 6 caracteres",
                    },
                  })}
                />
                <span
                  style={{
                    position: "absolute",
                    right: "10px",
                    top: "50%",
                    transform: "translateY(-50%)",
                    cursor: "pointer",
                  }}
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? (
                    <AiOutlineEyeInvisible size={24} />
                  ) : (
                    <AiOutlineEye size={24} />
                  )}
                </span>
                {errors.password && (
                  <p className={styles.error}>{errors.password.message}</p>
                )}
              </div>
              <ButtonLogin name={"Entrar"} type="submit" />
            </form>
            <p>
              Não tem uma conta?{" "}
              <button
                className={styles.register}
                onClick={() => navigation("/cadastrar")}
              >
                Registre-se
              </button>
            </p>
          </div>
        </div>
      </main>
    </motion.main>
  );
}
