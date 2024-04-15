<template>
  <el-icon class="collapse-icon" @click="changeCollapse">
    <component :is="device === 'mobile' ? 'expand' : isCollapse ? 'expand' : 'fold'"></component>
  </el-icon>
</template>

<script setup lang="ts">
import { useGlobalStore } from '@/stores/modules/global';
import { storeToRefs } from 'pinia';

const globalStore = useGlobalStore();
const { isCollapse, device, asidePopup } = storeToRefs(globalStore);
const changeCollapse = () => {
  if (device.value === 'mobile') {
    asidePopup.value = true;
  } else {
    isCollapse.value = !isCollapse.value;
  }
};
</script>

<style scoped lang="scss">
.collapse-icon {
  margin-right: 20px;
  font-size: var(--el-font-size-extra-large);
  color: var(--el-text-color-regular);
  cursor: pointer;
}
</style>
