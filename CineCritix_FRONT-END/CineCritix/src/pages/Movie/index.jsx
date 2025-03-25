import axios from "axios";
import { Star } from "lucide-react";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import styles from "../Movie/Movie.module.css";
import {
  deletePublicationById,
  getMovieById,
  getPublicationsByMovieId,
  getUserIdFromToken,
  publications,
} from "../../service/api";
import { div, h1, p } from "framer-motion/client";
import { FaPen, FaTrash } from "react-icons/fa";
import { RxHamburgerMenu } from "react-icons/rx";

const API_KEY = "a1597f569dafd7069822328e2bd0d446";
const API_KEY2 = "2fcfe92f";

export default function Movie() {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [backdrop, setBackdrop] = useState("");
  const [trailerUrl, setTrailerUrl] = useState("");
  const [cast, setCast] = useState([]);
  const [userRating, setUserRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [comentarios, setComentarios] = useState([]);
  const [charCount, setcharCount] = useState(0);
  const [avaliation, setAvaliation] = useState([]);
  const userId = getUserIdFromToken();
  const navigate = useNavigate();
  const charLimit = 500;
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
  } = useForm();
  const [isMobile, setIsMobile] = useState(window.innerWidth <= 769);
  const [openMenuMobile, setOpenMenuMobile] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleChange = (e) => {
    const text = e.target.value;
    if (text.length <= charLimit) {
      setcharCount(text.length);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, []);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 769);
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, [window.innerWidth]);

  useEffect(() => {
    setValue("rating", userRating);
  }, [userRating, setValue]);

  useEffect(() => {
    const fetchComentarios = async () => {
      try {
        const allComentarios = await getPublicationsByMovieId();

        const comentariosFiltrados = allComentarios.filter(
          (comentario) => String(comentario.movies.id) === String(id)
        );

        setComentarios(comentariosFiltrados);

        const avaliationUser = comentariosFiltrados.some(
          (comentario) => String(comentario.user.id) === String(userId)
        );

        setAvaliation(avaliationUser);
      } catch (error) {
        console.error("Erro ao buscar comentários:", error);
      }
    };

    const fetchMovie = async () => {
      try {
        const movieData = await getMovieById(id);

        if (!movieData) {
          console.error("Filme não encontrado na API.");
          return;
        }

        const tmdbResponse = await axios.get(
          `https://api.themoviedb.org/3/find/${movieData.imdbID}`,
          { params: { api_key: API_KEY, external_source: "imdb_id" } }
        );

        let tmdbMovie = null;
        let mediaType = "";

        if (tmdbResponse.data.movie_results.length > 0) {
          tmdbMovie = tmdbResponse.data.movie_results[0];
          mediaType = "movie";
        } else if (tmdbResponse.data.tv_results.length > 0) {
          tmdbMovie = tmdbResponse.data.tv_results[0];
          mediaType = "tv";
        } else {
          alert("Filme ou série não encontrado na TMDB.");
          return;
        }

        setMovie({
          ...movieData,
          tmdbId: tmdbMovie.id,
          mediaType,
          overview: tmdbMovie.overview || movieData.Plot,
          poster: tmdbMovie.poster_path
            ? `https://image.tmdb.org/t/p/original${tmdbMovie.poster_path}`
            : movieData.Poster,
          voteAverage: tmdbMovie.vote_average || movieData.imdbRating,
        });

        if (tmdbMovie.backdrop_path) {
          setBackdrop(
            `https://image.tmdb.org/t/p/original${tmdbMovie.backdrop_path}`
          );
        }

        const trailerResponse = await axios.get(
          `https://api.themoviedb.org/3/${mediaType}/${tmdbMovie.id}/videos`,
          { params: { api_key: API_KEY } }
        );

        if (trailerResponse.data.results.length > 0) {
          const trailer = trailerResponse.data.results.find(
            (video) => video.type === "Trailer" && video.site === "YouTube"
          );

          if (trailer) {
            setTrailerUrl(`https://www.youtube.com/watch?v=${trailer.key}`);
          } else {
            console.warn("Nenhum trailer encontrado.");
          }
        }

        const castResponse = await axios.get(
          `https://api.themoviedb.org/3/${mediaType}/${tmdbMovie.id}/credits`,
          { params: { api_key: API_KEY } }
        );

        if (castResponse.data.cast.length > 0) {
          setCast(castResponse.data.cast.slice(0, 10));
        } else {
          console.warn("Nenhum ator encontrado.");
        }
      } catch (error) {
        console.error("Erro ao buscar detalhes do filme:", error);
      }
    };

    fetchComentarios();

    fetchMovie();
  }, []);

  const renderStars = (rating) => {
    const stars = [];
    const fullStars = Math.floor(rating / 2);
    const hasHalfStar = rating % 2 >= 1;

    for (let i = 0; i < fullStars; i++) {
      stars.push(<Star key={i} color="#FFD700" fill="#FFD700" size={32} />);
    }

    if (hasHalfStar) {
      stars.push(
        <div
          key="half"
          style={{
            position: "relative",
            display: "inline-block",
            width: "32px",
          }}
        >
          <Star color="#ccc" fill="#ccc" size={32} />
          <div
            style={{
              position: "absolute",
              top: 0,
              left: 0,
              width: "50%",
              overflow: "hidden",
            }}
          >
            <Star color="#FFD700" fill="#FFD700" size={32} />
          </div>
        </div>
      );
    }

    while (stars.length < 5) {
      stars.push(
        <Star key={stars.length} color="#ccc" fill="#ccc" size={32} />
      );
    }

    return stars;
  };

  const handleRating = (value) => {
    setUserRating(value);
  };

  const renderInteractiveStars = () => {
    return Array.from({ length: 10 }, (_, index) => {
      const starValue = index + 1;
      return (
        <Star
          key={starValue}
          size={32}
          color={starValue <= (hoverRating || userRating) ? "#FFD700" : "#ccc"}
          fill={starValue <= (hoverRating || userRating) ? "#FFD700" : "none"}
          onClick={() => setUserRating(starValue)}
          onMouseEnter={() => setHoverRating(starValue)}
          onMouseLeave={() => setHoverRating(0)}
          style={{ cursor: "pointer", margin: "0 3px" }}
        />
      );
    });
    return <>{stars}</>;
  };

  const onSubmit = async (data) => {
    const token = localStorage.getItem("token");

    if (!token) {
      const logar = window.confirm("Necessário fazer login para avaliar. Deseja fazer login agora?");
      if (logar) {
        window.location.href = "/login";
      }
      return
    }

    const newComentario = {
      rate: Number(data.rating),
      notes: data.review || "",
      movieId: id,
    };

    try {
      const newPublication = await publications(newComentario);
      console.log("Resposta da API:", newPublication);

      if (!newPublication) {
        alert("Erro ao enviar a avaliação. Resposta inválida.");
        return;
      }

      setComentarios((prev) => [...prev, newPublication]);
      alert("Avaliação enviada com sucesso!");
      window.location.reload();
    } catch (error) {
      console.error(
        "Erro ao enviar a avaliação:",
        error.response?.data || error
      );
      alert(
        "Erro ao enviar a avaliação. Verifique os dados e tente novamente."
      );
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja excluir esta publicação?")) {
      try {
        await deletePublicationById(id);
        alert("Publicação excluída com sucesso!");
        window.location.reload();
      } catch (error) {
        console.error("Erro ao excluir publicação:", error.message);
      }
    }
  };

  return (
    <main>
      <Header />
      <div className={styles.header}>
        <div className={styles.navigation}>
          {isMobile ? (
            <>
              <RxHamburgerMenu
                size={32}
                className={styles.menu}
                onClick={() => setOpenMenuMobile(!openMenuMobile)}
              />
              {openMenuMobile && <NavigationMobile />}
            </>
          ) : (
            <>
              <div className={styles.active}>
                <h2>Filmes</h2>
              </div>
              <h2>Séries</h2>
              {isAuthenticated ? (
                <h2 onClick={() => navigate("/minhaConta")}>Minha Conta</h2>
              ) : (
                <h2 onClick={() => navigate("/login")}>Login</h2>
              )}
            </>
          )}
        </div>
      </div>
      {backdrop ? (
        <div
          className={styles.backdrop}
          style={{ backgroundImage: `url(${backdrop})` }}
        >
          {movie && (
            <div className={styles.box}>
              <div className={styles.movie}>
                <img
                  src={movie.Poster}
                  alt={movie.Title}
                  className={styles.poster}
                />
                {trailerUrl && (
                  <a
                    href={trailerUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className={styles.trailer}
                  >
                    <button>Assistir Trailer</button>
                  </a>
                )}
              </div>

              <div className={styles.info}>
                <div className={styles.line}>
                  <div className={styles.position}>
                    <h1>{movie.Title}</h1>
                    <h2>Gênero: {movie.Genre}</h2>
                    <h2>Duração: {movie.Runtime}</h2>
                    <h3>Data do lançamento: {movie.Released}</h3>
                    <h2>Sinopse: </h2>
                    <p className={styles.space}>{movie.Plot}</p>
                    <hr />
                    <h2 className={styles.space}>Diretor: {movie.Director}</h2>
                    <hr />
                    <h2 className={styles.space}>Roterista: {movie.Writer}</h2>
                    <hr />
                  </div>
                  <div className={styles.positionStar}>
                    <div className={styles.stars}>
                      {renderStars(movie.imdbRating)}
                    </div>
                    <h1>Nota: {movie.imdbRating}</h1>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      ) : (
        <p>Carregando...</p>
      )}
      <div className={styles.contain}>
        <h1>Elenco:</h1>
        <div className={styles.castContainer}>
          {cast.map((actor) => (
            <div key={actor.id} className={styles.actor}>
              <a
                href={`https://www.google.com/search?q=${encodeURIComponent(
                  actor.name
                )}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                <img
                  src={
                    actor.profile_path
                      ? `https://image.tmdb.org/t/p/w200${actor.profile_path}`
                      : "https://via.placeholder.com/150"
                  }
                  alt={actor.name}
                  className={styles.actorImage}
                />
                <p className={styles.actorName}>{actor.name}</p>
              </a>
            </div>
          ))}
        </div>

        <div className={styles.feedbackArea}>
          {avaliation ? (
            <p className={styles.jaAvaliou}>Você já avaliou este filme!</p>
          ) : (
            <form
              onSubmit={handleSubmit(onSubmit)}
              className={styles.feedbackArea}
            >
              <h1 className={styles.titleFeedback}>Avalie este Filme!</h1>
              <div>{renderInteractiveStars()}</div>
              <h2>Sua nota: {userRating}</h2>
              {errors.rating && (
                <p className={styles.error}>A nota é obrigatória!</p>
              )}

              <input
                type="hidden"
                {...register("rating", { required: "A nota é obrigatória!" })}
                value={userRating}
              />

              {errors.rating && (
                <p className={styles.error}>{errors.rating.message}</p>
              )}

              <textarea
                {...register("review")}
                placeholder="Escreva sua avaliação... (Opcional)"
                className={styles.textarea}
                rows="5"
                maxLength={500}
                onChange={handleChange}
              ></textarea>
              <p>
                {charCount} / {charLimit} caracteres
              </p>
              <button
                className={styles.button}
                type="submit"
                disabled={userRating === 0}
              >
                <h3>Enviar Avaliação</h3>
              </button>
            </form>
          )}

          <div className={styles.comentarios}>
            <h1>Avaliações:</h1>
            {comentarios.length > 0 ? (
              comentarios.map((comentario) => (
                <div key={comentario.id} className={styles.comentarioCard}>
                  <img
                    src={
                      comentario.user.avatar
                        ? comentario.user.avatar
                        : "https://cdn-icons-png.flaticon.com/512/149/149071.png"
                    }
                    alt={comentario.user.avatar}
                    className={styles.avataroto}
                  />

                  <div className={styles.comentarioInfo}>
                    <h3 className={styles.name}>{comentario.user.fullName}</h3>
                    <div className={styles.starsAvaliation}>
                      <h3 className={styles.nota}>Nota: {comentario.rate}</h3>
                      <div className={styles.star}>
                        {renderStars(comentario.rate)}
                      </div>
                    </div>
                    <p>{comentario.notes}</p>
                    {comentario.user.id === userId ? (
                      <h2
                        className={styles.trash}
                        onClick={() => handleDelete(comentario.id)}
                      >
                        <FaTrash />
                      </h2>
                    ) : (
                      ""
                    )}
                  </div>
                </div>
              ))
            ) : (
              <p>Nenhuma avaliação feita ainda.</p>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </main>
  );
}
