import { defineConfig } from 'vite'
import { resolve } from 'path'
import buildInfoPlugin from './vite-plugin-build-info.js'
import { visualizer } from 'rollup-plugin-visualizer'
import react from '@vitejs/plugin-react'
import mockApiPlugin from './vite-plugin-mock-api.js'


export default defineConfig({
  plugins: [
    react(),
    buildInfoPlugin(),
    mockApiPlugin(),
    // Add the visualizer plugin
    visualizer({
      filename: './stats.html', // Output file for the visualization
      open: false, // Do not open automatically, we'll open it manually
      gzipSize: true, // Show gzip size
      brotliSize: true, // Show brotli size
    }),
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
  },
  server: {

  },
  build: {
    rollupOptions: {
      output: {
        // Manual chunking to separate specific modules into their own bundles
        manualChunks: {
          // Creates a 'vendor' chunk for commonly used libraries
          // We target node_modules to separate all third-party dependencies.
          vendor: ['uuid', 'react', 'react-dom'], // Example: add common libraries used
          // Creates a 'lazy-module' chunk for our dynamically imported module
          // The lazy-module.js is already split by dynamic import, but this shows manual control
          'lazy-module': ['./src/lazy-module.js'],
        },
      },
    },
  }
})