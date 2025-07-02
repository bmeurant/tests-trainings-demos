import { defineConfig } from 'vite'
import { resolve } from 'path'
import buildInfoPlugin from './vite-plugin-build-info.js'

export default defineConfig({
  plugins: [
    buildInfoPlugin(),
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "sass:color"; @use "${resolve(__dirname, 'src/styles/_global.scss')}" as *;`
      }
    }
  }
})