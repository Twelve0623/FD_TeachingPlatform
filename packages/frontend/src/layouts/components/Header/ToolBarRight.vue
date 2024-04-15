<template>
  <div class="tool-bar-ri">
    <div class="header-icon">
      <Question id="question" />
      <AssemblySize id="assemblySize" />
      <Language id="language" />
      <SearchMenu id="searchMenu" />
      <ThemeSetting id="themeSetting" />
      <!--<Message id="message" />-->
      <Fullscreen id="fullscreen" />
    </div>
    <!--<span class="username">{{ username }}</span>-->
    <Avatar />
  </div>
</template>

<script setup lang="ts">
// import { computed } from 'vue';
// import { useUserStore } from '@/stores/modules/user';
import AssemblySize from './components/AssemblySize.vue';

import SearchMenu from './components/SearchMenu.vue';
import ThemeSetting from './components/ThemeSetting.vue';

import Fullscreen from './components/Fullscreen.vue';
import Avatar from './components/Avatar.vue';
import Language from './components/Language.vue';
// import Message from './components/Message.vue';
import Question from './components/Question.vue';

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
