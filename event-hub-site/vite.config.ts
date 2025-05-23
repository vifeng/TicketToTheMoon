/// <reference types="vitest" />
import { fileURLToPath, URL } from 'node:url'
import VueRouter from 'unplugin-vue-router/vite'
import AutoImport from 'unplugin-auto-import/vite'
import { VueRouterAutoImports } from 'unplugin-vue-router'
import Components from 'unplugin-vue-components/vite'
import tailwindcss from '@tailwindcss/vite'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Inspect from 'vite-plugin-inspect'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    Inspect(),
    tailwindcss(),
    VueRouter(),
    Components({
      dirs: ['src'], // or even ['src/components', 'src/layouts', 'src/ui'] if you prefer
      deep: true, // enables recursive search
      directoryAsNamespace: false // keeps names clean: <Sidebar /> not <LayoutsSidebar />
    }),
    AutoImport({
      include: [
        /\.[tj]sx?$/, // .ts, .tsx, .js, .jsx
        /\.vue$/,
        /\.vue\?vue/, // .vue
        /\.md$/ // .md
      ],
      imports: [
        'vue',
        VueRouterAutoImports,
        {
          pinia: ['defineStore', 'storeToRefs', 'acceptHMRUpdate']
        },
        {
          'vue-meta': ['useMeta']
        }
      ],
      dts: true,
      viteOptimizeDeps: true,
      dirs: ['src/stores/**', 'src/composables/**']
    }),
    vue({
      template: {
        compilerOptions: {
          isCustomElement: (element) => element.startsWith('iconify-icon')
        }
      }
    })
  ],
   test: {
    globals: true,
    environment: 'jsdom',
    // setupFiles: './vitest.setup.ts', // optional setup file
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
