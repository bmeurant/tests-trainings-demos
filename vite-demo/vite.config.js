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
})