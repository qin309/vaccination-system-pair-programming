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
  getRecords: () => api.get('/record/my'),
  getFamilyRecords: (id) => api.get(`/record/family/${id}`),
  createRecord: (data) => api.post('/record/create', data),
  recordAdverse: (id, reaction) => api.post(`/record/adverse/${id}?reaction=${encodeURIComponent(reaction)}`),
  getRecordList: () => api.get('/record/list'),

  getFamilyMembers: () => api.get('/family/list'),
  addFamilyMember: (data) => api.post('/family/add', data),
  updateFamilyMember: (data) => api.put('/family/update', data),
  deleteFamilyMember: (id) => api.delete(`/family/delete/${id}`),
  getFamilyDetail: (id) => api.get(`/family/detail/${id}`)
}
