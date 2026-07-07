import api from "../api/axios";

export const getProducts = () => api.get("/products");

export const addProduct = (product) => api.post("/products", product);

export const deleteProduct = (id) => api.delete(`/products/${id}`);

export const updateProduct = (id, product) =>
    api.put(`/products/${id}`, product);

export const getDashboard = () => api.get("/dashboard");