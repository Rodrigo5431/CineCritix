import axios from "axios";
import { jwtDecode } from "jwt-decode";

const API = "https://projectcinecritixback-end-production.up.railway.app";

export const api = axios.create({
  baseURL: API,
});

export function getUserIdFromToken() {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const decoded = jwtDecode(token);
    console.log("Token decodificado:", decoded);
    return decoded.id;
  } catch (error) {
    console.error("Erro ao decodificar o token:", error);
    return null;
  }
}

export async function getUserById() {
  try {
    const userId = getUserIdFromToken();
    if (!userId) throw new Error("Usuário não autenticado");

    const response = await api.get(`/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    console.log("Usuário autenticado:", response.data);

    return response.data;
  } catch (error) {
    console.error("Erro ao buscar usuário:", error.message);
    throw error;
  }
}

export const getAllMovies = async () => {
  try {
    const response = await api.get("/movies");
    console.log(response.data);

    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export async function getMovieById(id) {
  try {
    const response = await api.get(`/movies/${id}`);
    console.log("Detalhes do filme:", response.data);

    return response.data;
  } catch (error) {
    console.error("Erro ao buscar detalhes do filme:", error);
    throw error;
  }
}

export async function login(data) {
  console.log(data);

  try {
    const response = await api.post(`/login`, data);
    if (response.status === 200) {
      const token = response.headers["authorization"];
      localStorage.setItem("token", token);
      return response;
    }
  } catch (error) {
    console.log("erro na requisicao " + error.message);
  }
}

export async function cadastro(data) {
  try {
    const response = await api.post(`/users`, data);
    if (response.status === 201) {
      return response;
    }
  } catch (error) {
    console.log("erro na requisicao " + error.message);
  }
}

export async function getUser() {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("Usuário não está autenticado");
    }

    const response = await api.get(`/users/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.log("Erro ao buscar usuário:", error.message);
    throw error;
  }
}

export async function updateUser(userData) {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("Usuário não está autenticado");
    }

    const response = await api.put(`/users`, userData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("Usuário atualizado com sucesso: ", response.data);
    return response.data;
  } catch (error) {
    console.error("Erro ao atualizar usuário: ", error.message);
    throw error;
  }
}

export async function getAllPublications() {
  try {
    const response = await api.get("/publications");
    console.log("Publicações:", response.data);
    return response;
  } catch (error) {
    console.error("Erro ao buscar publicações: ", error.message);
    throw error;
  }
}

export async function getPublicationsByMovieId() {
  try {
    const response = await api.get("/publications");

    return response.data;
  } catch (error) {
    console.error("Erro ao buscar publicações: ", error.message);
  }
}

export async function deletePublicationById(publicationId) {
  try {
    const response = await api.delete(`/publications/${publicationId}`);

    return response.data;
  } catch (error) {
    console.error("Erro ao deletar publicação: ", error.message);
  }
}

export async function publications(data) {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("Token de autenticação não encontrado.");
    }

    const response = await api.post(`/publications`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 200 || response.status === 201) {
      return response.data;
    } else {
      console.error("Erro inesperado na resposta da API:", response);
      throw new Error("Erro ao enviar a publicação.");
    }
  } catch (error) {
    console.error("Erro na requisição:", error.response?.data || error.message);
    throw error;
  }
}

export async function uploadImageToCloudinary(file) {
  const CLOUDINARY_URL =
    "https://api.cloudinary.com/v1_1/deb585wpe/image/upload";
  const UPLOAD_PRESET = "agoraVai";

  const formData = new FormData();
  formData.append("file", file);
  formData.append("upload_preset", UPLOAD_PRESET);

  try {
    const response = await fetch(CLOUDINARY_URL, {
      method: "POST",
      body: formData,
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.error?.message || "Erro no upload da imagem");
    }

    console.log("Imagem enviada com sucesso:", data.secure_url);
    return data.secure_url;
  } catch (error) {
    console.error("Erro ao enviar imagem:", error.message);
    throw error;
  }
}
