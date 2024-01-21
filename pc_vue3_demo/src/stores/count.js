import { ref } from 'vue'
import { defineStore } from 'pinia'
export const useCountStore = defineStore('count', () => {
  const count = ref(0) // 初始化一个 count，相当于 Vuex 中的 state 操作
  // 定义一个动作，相当于 Vuex 中的 action，这个函数内可以进行请求操作
  function addCount(value) {
    // value 是外部调用的时候传入的值
    count.value += value // 赋值 count
  }
  // 最后将这两个属性 return 出去，外部调用的时候可以直接获取
  return { count, addCount }
})
