// import { createApp } from 'vue'
import * as Vue from 'vue'
// import router from './router'
import * as VueRouter from 'vue-router';
import App from './App.vue'
import axios from "axios"
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './assets/main.css'


const base = axios.create({
  baseURL: "http://localhost:5173"
});



Vue.prototype.$http = base;
Vue.config.productionTip = false;

new Vue({
    VueRouter,
    render: h => h(App)
}).$mount("#app");


