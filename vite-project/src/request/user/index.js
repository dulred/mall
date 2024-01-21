import axios from "@/request/axios";

/**
 * @description 登录请求
 * @author dulred
 * @date 27/08/2023  
 */
export function getUserInfo() {
    return axios.get('/user/info');
  }
  
  export function EditUserInfo(params) {
    return axios.put('/user/info', params);
  }
  
  export function login(params) {
    return axios.post('/user/login', params);
  }
  
  export function logout() {
    return axios.post('/user/logout')
  }
  
  export function register(params) {
    return axios.post('/user/register', params);
  }
  
  