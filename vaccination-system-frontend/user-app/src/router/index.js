import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // 用户模块路由
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue'),
    meta: { requiresAuth: true }
  },

  // 疫苗管理模块路由
  {
    path: '/vaccine',
    name: 'VaccineList',
    component: () => import('../views/VaccineList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/vaccine/:id',
    name: 'VaccineDetail',
    component: () => import('../views/VaccineDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/vaccine-batch',
    name: 'VaccineBatch',
    component: () => import('../views/VaccineBatch.vue'),
    meta: { requiresAuth: true }
  },

  // 预约模块路由
  {
    path: '/appointment',
    name: 'Appointment',
    component: () => import('../views/Appointment.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/my-appointments',
    name: 'MyAppointments',
    component: () => import('../views/MyAppointments.vue'),
    meta: { requiresAuth: true }
  },

  // 接种记录模块路由
  {
    path: '/records',
    name: 'Records',
    component: () => import('../views/Records.vue'),
    meta: { requiresAuth: true }
  },

  // 家庭成员模块路由
  {
    path: '/family',
    name: 'Family',
    component: () => import('../views/Family.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')

  // 已登录用户访问登录页/注册页，自动跳转首页
  if (token && (to.path === '/login' || to.path === '/register')) {
    next('/')
  } else if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router