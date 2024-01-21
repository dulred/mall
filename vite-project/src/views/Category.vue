<template>
  <div class="categray">
    <div>
      <header class="category-header wrap van-hairline--bottom">
       
        <i class="iconfont icon-arrow-left-bold" @click="goHome"></i>
        <div class="header-search">
          <van-icon name="search" />
          <router-link class="search-title" to="product-list?from=category">
            全场50元起步
          </router-link>
        </div>
      </header>
      <div class="search-wrap" ref="searchWrap">
        <list-scroll :scroll-data="state.categoryData" class="nav-side-wrapper">
          <ul class="nav-side">
            <li
              v-for="item in state.categoryData"
              :key="item.categoryId"
              v-text="item.categoryName"
              :class="{'active' : state.currentIndex == item.categoryId}"
              @click="selectMenu(item.categoryId)"
            ></li>
          </ul>
        </list-scroll>
        <div class="search-content">
          <list-scroll :scroll-data="state.categoryData" >
            <div class="swiper-container">
              <div class="swiper-wrapper">
                <template v-for="(category, index) in state.categoryData">
                  <div class="swiper-slide" v-if="state.currentIndex == category.categoryId" :key="index">
                    <!-- <img class="category-main-img" :src="category.mainImgUrl" v-if="category.mainImgUrl"/> -->
                    <div class="category-list" v-for="(products, index) in category.secondLevelCategoryVOS" :key="index">
                      <p class="catogory-title">{{products.categoryName}}</p>
                      <div class="product-item" v-for="(product, index) in products.thirdLevelCategoryVOS" :key="index" @click="selectProduct(product)">
                        <img src="http://127.0.0.1:8081/images/goods-img/apple.png" class="product-img"/>
                        <p  class="product-title">{{product.categoryName  }}</p>
                      </div>
                    </div>
                  </div>
                </template>
              </div>
            </div>
          </list-scroll>
        </div>
      </div>
      <nav-bar></nav-bar>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import navBar from '@/components/NavBar.vue'
import listScroll from '@/components/ListScroll.vue'
import { getCategory } from "@/request/good"
import { showLoadingToast, closeToast } from 'vant'
const router = useRouter()
// composition API 获取 refs 的形式
const searchWrap = ref(null)
const state = reactive({
  categoryData: [],
  currentIndex: 15
})

onMounted(async () => {
  let screenHeight = document.documentElement.clientHeight
  console.log(screenHeight)
  searchWrap.value.style.height = screenHeight - 100 + 'px'
  showLoadingToast('加载中...')
  const { data } = await getCategory()
  closeToast()
  state.categoryData = data
})

const goHome = () => {
  router.push({ path: 'home' })
}

const selectMenu = (index) => {
  state.currentIndex = index
}

const selectProduct = (item) => {
  console.log('item', item.categoryId)
  router.push({ path: '/product-list', query: { categoryId: item.categoryId } })
}
</script>
<style lang="less" scoped>
  @import '@/common/less/mixin';
  .categray {
    .category-header {
      background: #fff;
      position: fixed;
      left: 0;
      top: 0;
      .fj();
      .wh(100%, 50px);
      line-height: 50px;
      padding: 0 15px;
      box-sizing: border-box;
      font-size: 15px;
      color: #656771;
      z-index: 10000;
      &.active {
        background: @primary;
      }
      .icon-left {
        font-size: 25px;
        font-weight: bold;
      }
      .header-search {
     
        display: flex;
        width: 80%;
        height: 30px;
        line-height: 30px;
        margin: 10px 0;
        padding: 5px 0;
        color: #232326;
        background: #F7F7F7;
        border-radius: 20px;
        padding-left: 5px;
      .van-icon-search:before {
          position: relative;
          top: 50%;
          transform: translateY(-50%);
      }
        .search-title {
          padding-left: 10px;
          font-size: 12px;
          color: #666;
          line-height: 21px;
        }
      }
      .icon-More {
        font-size: 20px;
      }
    }
  }
  .search-wrap {
    .product-title{
        font-size: 13px;
    }
    .fj();
    width: 100%;
    margin-top: 50px;
    background: #F8F8F8;
    .nav-side-wrapper {
      width: 28%;
      height: 100%;
      overflow: hidden;
      .nav-side {
        width: 100%;
        .boxSizing();
        background: #F8F8F8;
        li {
          width: 100%;
          height: 56px;
          text-align: center;
          line-height: 56px;
          font-size: 14px;
          &.active {
            color: @primary;
            background: #fff;
          }
        }
      }
    }
    .search-content {
      width: 72%;
      height: 100%;
      padding: 0 10px;
      background: #fff;
      overflow-y: scroll;
      touch-action: pan-y;
      * {
          touch-action: pan-y;
        }
      .boxSizing();
      .swiper-container {
        width: 100%;
        .swiper-slide {
          width: 100%;
          .category-main-img {
            width: 100%;
          }
          .category-list {
            display: flex;
            flex-wrap: wrap;
            flex-shrink: 0;
            width: 100%;
            .catogory-title {
              width: 100%;
              font-size: 17px;
              font-weight: 500;
              padding: 20px 0;
            }
            .product-item {
              width: 33.3333%;
              margin-bottom: 10px;
              text-align: center;
              font-size: 15px;
              .product-img {
                .wh(30px, 30px);
              }
            }
          }
        }
      }
    }
  }
  .fade-out-enter-active, .fade-out-leave-active {
    // transition: opacity 0.5s;
  }

  .fade-out-enter, .fade-out-leave-to {
    // opacity: 0;
  }
</style>