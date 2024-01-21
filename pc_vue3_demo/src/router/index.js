import { createRouter, createWebHashHistory } from 'vue-router'

// 通过 createRouter 创建路由实例。
const router = createRouter({
  history: createWebHashHistory(), // 这里有两种形式，createWebHistory 和 createWebHashHistory，我采用后者
  // 路径 —> 组件，对应配置。
  routes: [
    {
      path: '/',
      redirect: '/introduce'
    },
    {
      path: '/introduce',
      name: 'introduce',
      component: () => import('@/views/Introduce.vue')
    },
    {
      path: '/add',
      name: 'add',
      component: () => import('@/views/AddGood.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/index',
      name: 'index',
      component: () => import('@/views/Index.vue')
    },
    {
      path: '/swiper',
      name: 'swiper',
      component: () => import('@/views/Swiper.vue')
    },
    {
      path: '/test',
      name: 'test',
      component: () => import('@/views/Test.vue')
    },
    {
      path: '/hot',
      name: 'hot',
      component: () => import('@/views/IndexConfig.vue')
    },
    {
      path: '/new',
      name: 'new',
      component: () => import('@/views/IndexConfig.vue')
    },
    {
      path: '/recommend',
      name: 'recommend',
      component: () => import('@/views/IndexConfig.vue')
    },
    {
      path: '/category',
      name: 'category',
      component: () => import(/* webpackChunkName: "category" */ '@/views/Category.vue'),
      children: [
        {
          path: '/category/level2',
          name: 'level2',
          component: () => import(/* webpackChunkName: "level2" */ '@/views/Category.vue'),
        },
        {
          path: '/category/level3',
          name: 'level3',
          component: () => import(/* webpackChunkName: "level3" */ '@/views/Category.vue'),
        },

      ]
    },
    {
      path: '/good',
      name: 'good',
      component: () => import(/* webpackChunkName: "new" */ '@/views/Good.vue')
    },
    {
      path: '/order',
      name: 'order',
      component: () => import(/* webpackChunkName: "order" */ '@/views/Order.vue')
    },
    {
      path: '/order_detail',
      name: 'order_detail',
      component: () => import(/* webpackChunkName: "order_detail" */ '@/views/OrderDetail.vue')
    },
    {
      path: '/account',
      name: 'account',
      component: () => import(/* webpackChunkName: "account" */ '@/views/Account.vue')
    },
    {
      path: '/guest',
      name: 'guest',
      component: () => import(/* webpackChunkName: "guest" */ '@/views/Guest.vue')
    },
  ]
})

export default router // 将路由实例抛出
