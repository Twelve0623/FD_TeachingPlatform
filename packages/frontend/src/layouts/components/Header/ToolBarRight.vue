<template>
  <div class="tool-bar-ri">
    <div class="header-icon">
      <Language id="language" />
    </div>
    <!--<span class="username">{{ username }}</span>-->
    <Avatar />
  </div>
</template>

<script setup lang="ts">
import Language from './components/Language.vue';

// const userStore = useUserStore();
// const username = computed(() => userStore.userInfo.name);
</script>

<style scoped lang="scss">
.tool-bar-ri {
  display: flex;
  align-items: center;
  justify-content: center;
  .header-icon {
    display: flex;
    align-items: center;
    & > * {
      margin-left: 21px;
    }
    :deep(.iconfont) {
      font-size: var(--el-font-size-medium);
      color: var(--el-text-color-regular);
    }
  }
  .username {
    margin: 0 20px;
    font-size: 15px;
  }
}
</style>
