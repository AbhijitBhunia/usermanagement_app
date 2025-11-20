import axios from 'axios';

const API_BASE_URL = 'http://192.168.78.128:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const authService = {
  register: async (userData) => {
    try {
      const response = await api.post('/auth/register', userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Registration failed' };
    }
  },

  login: async (credentials) => {
    try {
      const response = await api.post('/auth/login', credentials);
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Login failed' };
    }
  },

  resetPassword: async (resetData) => {
   try {
     const response = await api.post('/auth/reset-password', resetData);
     return response.data;
   } catch (error) {
     throw error.response?.data || { success: false, message: 'Password reset failed' };
   }
  },

};

export const userService = {
  getUserById: async (userId) => {
    try {
      const response = await api.get(`/user/${userId}`);
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Failed to fetch user' };
    }
  },

  getUserActivities: async (userId) => {
    try {
      const response = await api.get(`/user/${userId}/activities`);
      return response.data;
    } catch (error) {
      throw error.response?.data || { success: false, message: 'Failed to fetch activities' };
    }
  },
};

export default api;
