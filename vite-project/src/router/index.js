import { createRouter, createWebHashHistory } from 'vue-router'

// 通过 createRouter 创建路由实例。
const router = createRouter({
    history: createWebHashHistory(), // 这里有两种形式，createWebHistory 和 createWebHashHistory，我采用后者
    // 路径 —> 组件，对应配置。
    routes: [
      {
        path: '/',
        redirect: '/home'
      },
      {
        path: '/home',
        name: 'home',
        component: () => import('@/views/Home.vue')
      },
      {
        path: '/about',
        name: 'about',
        component: () => import('@/views/About.vue')
      },
      {
        path: '/Login',
        name: 'Login',
        component: () => import('@/views/Login.vue')
      },
      {
        path: '/User',
        name: 'user',
        component: () => import('@/views/User.vue')
      },
      {
        path: '/Setting',
        name: 'setting',
        component: () => import('@/views/Setting.vue')
      },
      {
        path: '/Category',
        name: 'category',
        component: () => import('@/views/Category.vue')
      },
      {
        path: '/product-list',
        name: 'product-list',
        component: () => import('@/views/ProductList.vue')
      },
      {
        path: '/Test',
        name: 'test',
        component: () => import('@/views/Test.vue')
      },
      {
        path: '/product/:id',
        name: 'product',
        component: () => import('@/views/ProductDetail.vue'),
      },
      {
        path: '/cart/',
        name: 'cart',
        component: () => import('@/views/Cart.vue'),
      },
      {
        path: '/create-order',
        name: 'create-order',
        component: () => import('@/views/CreateOrder.vue')
      },
      {
        path: '/address',
        name: 'address',
        component: () => import('@/views/Address.vue')
      },
      {
        path: '/address-edit',
        name: 'address-edit',
        component: () => import('@/views/AddressEdit.vue'),
      },
      {
        path: '/order',
        name: 'order',
        component: () => import('@/views/Order.vue'),
      },
      {
        path: '/order-detail',
        name: 'order-detail',
        component: () => import('@/views/OrderDetail.vue'),
      },
    ]
  })

  
  
  export default router // 将路由实例抛出