import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
const path = require("path");

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: "./",
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "./src/assets/style/common.scss";`,
      },
    },
  },
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  server: {
    host: "0.0.0.0",
    port: 8080,
    proxy: {
      "^/api": {
        target: "", //接口域名
        // changeOrigin: true, //是否跨域
        // ws: true,            //是否代理 websockets
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
  build: {
    assetsDir: "static",
    chunkSizeWarningLimit: 2000,
  },
});
