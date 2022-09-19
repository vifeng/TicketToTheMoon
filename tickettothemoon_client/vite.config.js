import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://cli.vuejs.org/config/#vue-config-js
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  devServer: {
    proxy: {
      '/api': {
        // target: 'http://localhost:5173',
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, '')
      },
      overlay: {
        warnings: true,
        errors: true
      }
    },
  },
  server: {
    //   port: '8081',
    // cors: false,
    proxy: {
      '/api': {
        // target: 'http://localhost:5173',
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, '')
      },
    },
  },
  // Change build paths to make them Gradle compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'build/dist'

});

