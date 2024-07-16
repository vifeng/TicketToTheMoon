import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import 'vuetify/styles'
import vuetify from './plugins/vuetify'

import App from './App.vue'
import router from './router'
import AppLink from './components/AppLink.vue'

const app = createApp(App)
app.use(vuetify)
app.component('AppLink', AppLink)
app.use(createPinia())
app.use(router)

app.mount('#app')

if (process.env.NODE_ENV === 'development') {
  app.config.devtools = true
}
