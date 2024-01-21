<template>
  <header class="simple-header van-hairline--bottom">
    <i v-if="!noback" class="iconfont icon-arrow-left-bold" @click="goBack"></i>
    <i v-else>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>
    <div class="simple-header-name">{{ name }}</div>
    <i class="iconfont icon-ellipsis"></i>
  </header>
  <div class="block" ></div>
</template>

<script>

import { useRouter } from 'vue-router'
export default {
  props: {
    name: {
      type: String,
      default: ''
    },
    back: {
      type: String,
      default: ''
    },
    noback: {
      type: Boolean,
      default: false
    }
  },
  emits: ['callback'],
  setup(props, ctx) {
    const router = useRouter()
    const goBack = () => {
      if (!props.back) {
        router.go(-1)
      } else {
        router.push({ path: props.back })
      }
      ctx.emit('callback')
    }
    return {
      goBack
    }
  }
}
</script>

<style lang="less" scoped>
  @import '@/common/less/mixin';
  .simple-header {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 10000;
    .fj();
    .wh(100%, 44px);
    line-height: 44px;
    padding: 0 10px;
    .boxSizing();
    color: #252525;
    background: #fff;
    .simple-header-name {
      font-size: 14px;
    }
  }
  .block {
    height: 44px;
  }
</style>
