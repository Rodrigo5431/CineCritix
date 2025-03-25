import React from "react";
import * as styles from "../Register/Register.module.css";
import { useForm } from "react-hook-form";
import ButtonLogin from "../../components/ButtonLogin";
import { cadastro } from "../../service/api";
import { useNavigate } from "react-router-dom";
import Faixa1 from "../../assets/Faixa1.jpeg";
import Faixa2 from "../../assets/Faixa2.jpeg";

export default function Register() {
  const navigation = useNavigate();
  const [showPassword, setShowPassword] = React.useState(false);
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm();

  const onSubmit = async (data) => {
    const credentials = {
      fullName: data.nome,
      email: data.email,
      password: data.senha,
      confirmPassword: data.confirmaSenha,
      avatar: null,
      profile: "USER"
    }
    try {
      const response = await cadastro(credentials);
      if (response.status === 201) {
        setTimeout(() => {
           navigation("/login")
        }, 1500);
      } else {
        alert("Erro ao fazer cadastro");
      }
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <main className={styles.container}>
      <img src={Faixa1} alt="filmes" className={styles.faixa1} />
      <div className={styles.faixaLeft} />
      <img src={Faixa2} alt="filmes" className={styles.faixa2} />
      <div className={styles.faixaRight} />
      <h1 className={styles.title}>Cadastre-se</h1>
      <div className={styles.box2}></div>
      <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
        <input
          className={styles.textInput}
          placeholder="Nome Completo"
          {...register("nome", {
            required: "O nome é obrigatória",
          })}
        />
        {errors.nome && <p className={styles.error}>{errors.nome.message}</p>}

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
        {errors.email && <p className={styles.error}>{errors.email.message}</p>}

        <input
          className={styles.textInput}
          placeholder="Senha"
          {...register("senha", {
            required: "A senha é obrigatória",
          })}
        />
        {errors.senha && <p className={styles.error}>{errors.senha.message}</p>}

        <input
          className={styles.textInput}
          placeholder="Confirma Senha"
          {...register("confirmaSenha", {
            required: "Confirma Senha é obrigatória",
          })}
        />
        {errors.confirmaSenha && (
          <p className={styles.error}>{errors.confirmaSenha.message}</p>
        )}

        <button className={styles.button} type="submit">
          Cadastrar
        </button>
      </form>
    </main>
  );
}
