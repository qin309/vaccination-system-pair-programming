import axios from 'axios'
import { ElMessage } from 'element-plus'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  response => {
    if (response.data.code !== 200) {
      ElMessage.error(response.data.message)
      return Promise.reject(response.data)
    }
    return response.data
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      ElMessage.error('登录已过期，请重新登录')
      window.location.href = '/login'
    } else if (error.response?.status === 403) {
      ElMessage.error('没有权限访问该功能')
    } else if (error.response?.status === 500) {
      ElMessage.error('服务器错误，请稍后重试')
    } else {
      ElMessage.error(error.response?.data?.message || '网络错误，请检查网络连接')
    }
    return Promise.reject(error)
  }
)

export default {
  login: (data) => api.post('/user/login', data),
  register: (data) => api.post('/user/register', data),
  getUserInfo: () => api.get('/user/info'),
  updateUserInfo: (data) => api.put('/user/update', data),
  changePassword: (oldPassword, newPassword) => api.post('/user/changePassword', null, { params: { oldPassword, newPassword } })
}
