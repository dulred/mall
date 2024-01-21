import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import { createPinia } from 'pinia'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 初始化 Vue 实例
const app = createApp(App)
// 注册路由
app.use(router)

// 注册 store
app.use(createPinia())

const orderStatus = {
  0: '待支付',
  1: '已支付',
  2: '配货完成',
  3: '出库成功',
  4: '交易成功',
  '-1': '手动关闭',
  '-2': '超时关闭',
  '-3': '商家关闭'
}
// 全局方法
app.config.globalProperties.$filters = {
  orderMap(status) {
    return orderStatus[status] || '未知状态'
  },
  prefix(url) {
    if (url && url.startsWith('http')) {
      return url
    } else {
      url = `http://localhost:8888${url}`
      return url
    }
  }
}

app.config.globalProperties.goTop = function () {
  const main = document.querySelector('.main')
  main.scrollTop = 0
}

// el-icon注册
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

// 挂载 Vue 实例
app.mount('#app')