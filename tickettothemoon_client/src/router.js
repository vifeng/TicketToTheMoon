import { createApp } from 'vue';
import * as VueRouter from 'vue-router';

const routes = [
        {
            path: '/',
            alias: '/employees',
            name: 'employees',
            component: () => import('./components/Employees')
        }
        ,{
            path: '/',
            alias: '/employees/:id',
            name: 'edit-employees',
            component: () => import('./components/EditEmployee')
        },
        {
            path: '/',
            alias: '/hello',
            name: 'helloWorld',
            component: () => import('./components/HelloWorld.vue')
        }
];

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
  });
  
createApp(App).use(router).mount('#app');
