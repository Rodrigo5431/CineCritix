import React, { useEffect, useState } from "react";
import * as styles from "../MyAccount/MyAccount.module.css";
import Header from "../../components/Header";
import { useForm } from "react-hook-form";
import axios from "axios";
import { FaEdit, FaPen } from "react-icons/fa";
import { FiEdit3 } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { BiArrowBack } from "react-icons/bi";
import {
  getAllPublications,
  getPublicationsByMovieId,
  getUserById,
  getUserIdFromToken,
  updateUser,
  uploadImageToCloudinary,
} from "../../service/api";

const API_KEY = "2fcfe92f";

export default function MyAccount() {
  const [movies, setMovies] = useState([]);
  const [user, setUser] = useState(null);
  const [publications, setPublications] = useState([]);
  const [formData, setFormData] = useState({});
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserById();
        setUser(userData);
      } catch (error) {
        console.error("Erro ao buscar usuário:", error.message);
      }
    };

    const fetchPublications = async () => {
      try {
        const allPublications = await getPublicationsByMovieId();
        const userId = getUserIdFromToken();

        const filteredPblications = allPublications.filter(
          (publications) => publications.user.id === userId
        );
        setPublications(filteredPblications);
      } catch (error) {
        console.error("Erro ao buscar publicações:", error.message);
      }
    };

    fetchUser();
    fetchPublications();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const handleAvatarChange = async (event) => {
    const file = event.target.files[0];

    if (!file) return;

    try {
      const imageUrl = await uploadImageToCloudinary(file);
      console.log("Nova URL do avatar:", imageUrl);

      const response = await updateUser({ avatar: imageUrl });
      console.log("Resposta do updateUser:", response);

      alert("Avatar atualizado com sucesso!");
      window.location.reload();
    } catch (error) {
      console.error(
        "Erro ao atualizar avatar:",
        error.response?.data || error.message
      );
      alert(
        "Erro ao atualizar avatar. Verifique o console para mais detalhes."
      );
    }
  };

  const onSubmit = async (data) => {
    try {
      if (!user) {
        throw new Error("Usuário não autenticado");
      }

      const updateData = {
        fullName: data.fullName || null,
        email: data.email || null,
        password: data.password || null,
        confirmPassword: data.confirmPassword || null,
        avatar: data.avatar || null,
        profile: data.profile || null,
      };

      console.log("Enviando dados para atualização:", updateData);

      await updateUser(updateData);
      alert("Perfil atualizado com sucesso!");
    } catch (error) {
      console.error("Erro ao atualizar perfil:", error.message);
      alert("Erro ao atualizar perfil. Tente novamente mais tarde.");
    }
  };

  return (
    <main>
      <div className={styles.container}>
        <div className={styles.navbarAvatar}>
          <div className={styles.positionBack}>
            <button className={styles.back} onClick={() => navigate("/")}>
              <BiArrowBack />
            </button>
          </div>
          <div className={styles.boxAvatar}>
            <input
              type="file"
              accept="image/*"
              onChange={handleAvatarChange}
              style={{ display: "none" }}
              id="fileInput"
            />
            <label htmlFor="fileInput" className={styles.avatarEdit}>
              <FaPen />
            </label>

            <img
              src={
                user?.avatar ||
                "https://cdn-icons-png.flaticon.com/512/149/149071.png"
              }
              alt="Foto do usuário"
              className={styles.avatar}
            />
          </div>
          <div className={styles.avaliation}>
            <h2 className={styles.title}>Quantidade de avaliações</h2>
            <hr className={styles.verticalLine} />
            <h1 className={styles.quant}>{publications.length}</h1>
          </div>

          <button className={styles.button} onClick={handleLogout}>
            <h2>Sair</h2>
          </button>
        </div>

        <div className={styles.box}>
          <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
            <div className={styles.input}>
              <h3 className={styles.text}>Nome completo</h3>
              <input
                className={styles.textInput}
                defaultValue={user?.fullName}
                {...register("fullName", {
                  // required: "O nome é obrigatório",
                })}
              />
              {errors.nome && (
                <p className={styles.error}>{errors.nome.message}</p>
              )}
            </div>

            <div className={styles.input}>
              <h3 className={styles.text}>Email</h3>
              <input
                className={styles.textInput}
                defaultValue={user?.email}
                {...register("email", {
                  // required: "O email é obrigatório",
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

            <div className={styles.input}>
              <h3 className={styles.text}>Senha</h3>
              <input
                className={styles.textInput}
                placeholder="Senha"
                {...register("senha", {
                  // required: "O senha é obrigatório",
                })}
              />
              {errors.senha && (
                <p className={styles.error}>{errors.senha.message}</p>
              )}
            </div>

            <div className={styles.input}>
              <h3 className={styles.text}>Confirma Senha</h3>
              <input
                className={styles.textInput}
                placeholder="Confirma senha"
                {...register("confirmaSenha", {
                  // required: "Confirma senha é obrigatório",
                })}
              />
              {errors.confirmaSenha && (
                <p className={styles.error}>{errors.confirmaSenha.message}</p>
              )}
            </div>

            <div className={styles.input}>
              <button className={styles.buttonSave}>
                <h3>Salvar Alterações</h3>
              </button>
            </div>
          </form>

          <h1>Avaliações:</h1>

          <div className={styles.contain}>
            {publications.length > 0 ? (
              publications.map((p) => (
                <div key={p.movies.id} className={styles.card}>
                  <img src={p.movies.poster} alt={p.movies.title} />
                  <div className={styles.title}>
                    <h4>
                      {p.movies.title} ({p.movies.year})
                    </h4>
                  </div>
                  <button
                    className={styles.edit}
                    onClick={() => navigate(`/movie/${p.movies.id}`)}
                  >
                    <FaPen />
                  </button>
                </div>
              ))
            ) : (
              <p>Você ainda não avaliou nenhum filme.</p>
            )}
          </div>
        </div>
      </div>
    </main>
  );
}
