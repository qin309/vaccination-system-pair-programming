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
      window.location.href = '/login'
    }
    ElMessage.error(error.response?.data?.message || '网络错误')
    return Promise.reject(error)
  }
)

export default {
  getVaccines: () => api.get('/vaccine/list'),
  getVaccineDetail: (id) => api.get(`/vaccine/detail/${id}`),
  addVaccine: (data) => api.post('/vaccine/add', data),
  updateVaccine: (data) => api.put('/vaccine/update', data),
  getBatchList: () => api.get('/vaccine/batch/list'),
  getExpiringBatches: () => api.get('/vaccine/batch/expiring'),
  addBatch: (data) => api.post('/vaccine/batch/add', data)
}
