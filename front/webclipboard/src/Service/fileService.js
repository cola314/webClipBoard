import axios from "axios";

export const getFiles = creator => {
  return axios.get(`/api/files/${creator}`);
};

export const deleteFile = (creator, id) => {
  return axios.delete(`/api/file/${id}/${creator}`);
};

export const getTextContent = (creator, id) => {
  return axios.get(`/api/text/${id}/${creator}`);
};

export const createText = (creator, text) => {
  return axios.post(`/api/text/${creator}`, text, {
    headers: { "Content-Type": "application/json" },
  });
};

export const uploadFile = (creator, formData) => {
  return axios({
    method: "post",
    url: `/api/file/${creator}`,
    data: formData,
    headers: { "Content-Type": "multipart/form-data" },
  });
};

export const getDownloadFileUrl = (creator, id) => {
  return (
    window.location.protocol +
    "//" +
    window.location.host +
    `/api/file/${id}/${creator}`
  );
};
