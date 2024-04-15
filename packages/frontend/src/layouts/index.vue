<!-- 💥 这里是一次性加载 LayoutComponents -->
<template>
  <component :is="LayoutComponents[layout]" />
  <ThemeDrawer />
</template>

<script setup lang="ts" name="layout">
import { LayoutType } from '@/stores/interface';
import { useGlobalStore } from '@/stores/modules/global';
import ThemeDrawer from './components/ThemeDrawer/index.vue';
import LayoutVertical from './LayoutVertical/index.vue';
import LayoutClassic from './LayoutClassic/index.vue';
import LayoutTransverse from './LayoutTransverse/index.vue';
import LayoutColumns from './LayoutColumns/index.vue';

const LayoutComponents: Record<LayoutType, Component> = {
  vertical: LayoutVertical,
  classic: LayoutClassic,
  transverse: LayoutTransverse,
  columns: LayoutColumns
};

const globalStore = useGlobalStore();
const { layout, device, isCollapse, asidePopup } = storeToRefs(globalStore);
const mobile = ref(false);
let oldLayout = layout.value;

const resizeBody = () => {
  const bodyWidth = document.body.getBoundingClientRect().width;

  if (device.value === 'desktop') {
    if (!isCollapse.value && bodyWidth <= 1200) {
      // 页面宽度低于1200px时， 左侧菜单自动折叠
      isCollapse.value = true;
    } else if (isCollapse.value && bodyWidth > 1200) {
      // 否则 自动展开
      isCollapse.value = false;
    }
  }
  mobile.value = bodyWidth <= 992; // 是否转化为 mobile 手机格式
};

// 初始化
resizeBody();

// 监听手机端的变化
watch(
  mobile,
  val => {
    if (val) {
      oldLayout = layout.value;
      // 侧边栏 隐藏
      asidePopup.value = false;
    }
    const bodyWidth = document.body.getBoundingClientRect().width;
    layout.value = val ? 'vertical' : oldLayout;
    // 判定当前设备的类型
    device.value = val ? 'mobile' : 'desktop';
    // 左侧菜单的折叠状态
    isCollapse.value = bodyWidth > 992 && bodyWidth < 1200;
    // body标签是否添加 mobile标识class
    document.body.classList[val ? 'add' : 'remove']('mobile');
  },
  {
    immediate: true
  }
);

// 监听布局变化，在 body 上添加相对应的 layout class
watch(
  () => layout.value,
  (newValue, oldValue) => {
    const body = document.body as HTMLElement;
    if (oldValue && body.classList.contains(oldValue as string)) {
      body.classList.remove(oldValue);
    }
    body.classList.add(newValue);
  },
  { immediate: true }
);

// 监听页面尺寸的变化
const cleanup = useEventListener(window, 'resize', resizeBody);

// 监听页面卸载
onUnmounted(() => {
  if (mobile.value) layout.value = oldLayout;
  cleanup();
});
</script>
