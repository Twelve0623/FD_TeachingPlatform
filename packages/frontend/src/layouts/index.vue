<!-- 经典布局 -->
<template>
  <el-container class="layout">
    <el-header>
      <div class="header-lf">
        <div class="logo flx-center">
          <img class="logo-img" src="@/assets/images/favicon.ico" alt="logo" />
          <span class="logo-text">复旦学习平台</span>
        </div>
        <ToolBarLeft />
      </div>
      <div class="header-ri">
        <ToolBarRight />
      </div>
    </el-header>
    <el-container class="classic-content">
      <router-view v-slot="{ Component, route }">
        <transition appear name="fade-transform" mode="out-in">
          <keep-alive :include="keepAliveName">
            <component :is="Component" :key="route.fullPath" />
          </keep-alive>
        </transition>
      </router-view>
    </el-container>
  </el-container>
</template>

<script setup lang="ts" name="layoutClassic">
import ToolBarLeft from '@/layouts/components/Header/ToolBarLeft.vue';
import ToolBarRight from '@/layouts/components/Header/ToolBarRight.vue';
import { storeToRefs } from 'pinia';
import { useKeepAliveStore } from '@/stores/modules/keepAlive';
const keepAliveStore = useKeepAliveStore();
const { keepAliveName } = storeToRefs(keepAliveStore);
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
