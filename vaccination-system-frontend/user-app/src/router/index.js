import { createRouter, createWebHistory } from 'vue-router'

const routes = [
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
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
