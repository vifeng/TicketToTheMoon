import { createApp } from 'vue'
import * as VueRouter from 'vue-router';
import App from './App.vue'
import axios from "axios"
// import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './assets/main.css'


createApp(App).config.productionTip = false;

const Vue = createApp({
  VueRouter,
  render: h => h(App)
});

createApp(App).use(Vue).mount("#app");

