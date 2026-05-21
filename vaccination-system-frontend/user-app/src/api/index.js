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
  getAppointments: () => api.get('/appointment/my'),
  createAppointment: (data) => api.post('/appointment/create', data),
  cancelAppointment: (id) => api.post(`/appointment/cancel/${id}`),
  getAppointmentList: () => api.get('/appointment/list'),
  getAppointmentDetail: (id) => api.get(`/appointment/detail/${id}`),

  getSlots: (date) => api.get(`/slot/available?date=${date}`),
  getSlotList: (date) => api.get(`/slot/list?date=${date}`),
  createSlots: (date, count) => api.post(`/slot/create?date=${date}&count=${count}`)
}
