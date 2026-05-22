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
  // 用户模块接口
  login: (data) => api.post('/user/login', data),
  register: (data) => api.post('/user/register', data),
  getUserInfo: () => api.get('/user/info'),
  updateUserInfo: (data) => api.put('/user/update', data),
  changePassword: (oldPassword, newPassword) => api.post('/user/changePassword', null, { params: { oldPassword, newPassword } }),
  getUserList: () => api.get('/user/list'),

  // 疫苗管理模块接口
  getVaccines: () => api.get('/vaccine/list'),
  getVaccineDetail: (id) => api.get(`/vaccine/detail/${id}`),
  addVaccine: (data) => api.post('/vaccine/add', data),
  updateVaccine: (data) => api.put('/vaccine/update', data),
  getBatchList: () => api.get('/vaccine/batch/list'),
  getExpiringBatches: () => api.get('/vaccine/batch/expiring'),
  addBatch: (data) => api.post('/vaccine/batch/add', data),

  // 预约模块接口
  getAppointments: () => api.get('/appointment/my'),
  createAppointment: (data) => api.post('/appointment/create', data),
  cancelAppointment: (id) => api.post(`/appointment/cancel/${id}`),
  getAppointmentList: () => api.get('/appointment/list'),
  getAppointmentDetail: (id) => api.get(`/appointment/detail/${id}`),

  getSlots: (date) => api.get(`/appointment-slot/available?date=${date}`),
  getSlotList: () => api.get('/appointment-slot/list'),
  createSlots: (date, count) => api.post(`/appointment-slot/create?date=${date}&count=${count}`),

  // 接种记录模块接口
  getRecords: () => api.get('/record/my'),
  getFamilyRecords: (id) => api.get(`/record/family/${id}`),
  createRecord: (data) => api.post('/record/create', data),
  recordAdverse: (id, reaction) => api.post(`/record/adverse/${id}?reaction=${encodeURIComponent(reaction)}`),
  getRecordList: () => api.get('/record/list'),

  // 家庭成员模块接口
  getFamilyMembers: () => api.get('/family/list'),
  addFamilyMember: (data) => api.post('/family/add', data),
  updateFamilyMember: (data) => api.put('/family/update', data),
  deleteFamilyMember: (id) => api.delete(`/family/delete/${id}`),
  getFamilyDetail: (id) => api.get(`/family/detail/${id}`)
}