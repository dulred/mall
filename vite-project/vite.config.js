import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
// 解析vue
import vue from '@vitejs/plugin-vue'
// 按需导入vant组件
import Components from 'unplugin-vue-components/vite'
import { VantResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 5173,
    host:'0.0.0.0'
  },
  resolve:{
    alias:{
      "@":fileURLToPath(new URL("./src",import.meta.url))
    }
  },
  plugins: [
    vue(),
    Components({ resolvers: [VantResolver()] }) // 注册按需导入vant组件
  ],
})
