import * as Vue from 'vue'

import * as VueRouter from 'vue-router';


Vue.use(VueRouter)

const routes = [
        {
            path: '/',
            alias: '/employees',
            name: 'employees',
            component: () => import('./components/Employees.vue')
        }
        // ,{
        //     path: '/',
        //     alias: '/employees/:id',
        //     name: 'edit-employees',
        //     component: () => import('./components/EditEmployee')
        // }
];

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
  });
  
  Vue.createApp(App).use(router).mount('#app');