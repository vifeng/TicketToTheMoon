
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { plugin } from '@formkit/vue'
import config from '../formkit.config'

import App from './App.vue'
import router from '@/router'
import AppLink from './components/SmartLink.vue'
import './assets/index.css'
import { createHead } from '@vueuse/head'

const app = createApp(App)
app.component('AppLink', AppLink)
app.use(createPinia())
app.use(router)
app.use(createHead())
app.use(plugin, config)

app.mount('#app')


