import HomeView from '@/views/HomeView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('@/views/AboutView.vue')
    },
    {
      path: '/employees',
      name: 'employees',
      component: () => import('@/views/EmployeeView.vue')
    },
    {
      path: '/venues',
      name: 'venues',
      component: () => import('@/views/VenueView.vue')
    },
    {
      path: '/ticketReservation',
      name: 'ticketReservation',
      component: () => import('@/views/ticketReservationView.vue')
    },
    {
      path: '/customers',
      name: 'customers',
      component: () => import('@/views/CustomerView.vue')
    },
    {
      path: '/apidoc',
      name: 'apidoc',
      component: () => import('@/views/ApiDocView.vue')
    },
    {
      path: '/chatbot',
      name: 'chatbot',
      component: () => import('@/views/AiChatbotView.vue')
    }
  ]
})

export default router
