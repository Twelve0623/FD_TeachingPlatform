<template>
  <div class="home" :style="{ padding: isMobile ? '0 6px' : '0 12px' }">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="12" class="mb20">
        <el-card shadow="hover" :body-style="{ 'padding-bottom': 0 }">
          <template v-for="(a, i) in menuList" :key="a.name">
            <div class="menu">
              <div class="menu-title">{{ a.meta.title }}</div>
              <div class="menu-item flex wp-100">
                <div class="menu-item__left">
                  <span>场景模型层</span>
                  <icon-formkit-right class="icon" />
                </div>
                <div class="menu-item__right">
                  <template v-for="(b, ii) in a.children" :key="ii">
                    <div
                      :class="['menu-item__right-item', secondLayoutPath === b.path ? 'active' : '']"
                      @click="handleFirstRoute(i, b)"
                    >
                      {{ b.meta.title }}
                    </div>
                  </template>
                </div>
              </div>
              <div class="menu-item flex wp-100" v-show="a.baseLayer?.length">
                <div class="menu-item__left">
                  <span>指标体系层</span>
                  <icon-formkit-right class="icon" />
                </div>
                <div class="menu-item__right">
                  <template v-for="(c, iii) in a.children" :key="iii">
                    <div
                      :class="['menu-item__right-item', thirdLayoutPath === c.name ? 'active' : '']"
                      @click="handleSecondRoute(c)"
                    >
                      {{ c.meta.title }}
                    </div>
                  </template>
                </div>
              </div>
            </div>
          </template>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12">
        <el-card shadow="hover" :body-style="{ 'padding-bottom': 0 }">
          <el-row :gutter="20">
            <template v-for="item in panels" :key="item.id">
              <el-col :xs="24" :sm="12" :md="12" :lg="12">
                <div :class="['panel', `panel-${item.id}`]">
                  <div class="panel-header">
                    <div class="icon-container">
                      <icon-healthicons-chart-line class="icon" />
                    </div>
                    <span class="title pl10">日均访问量</span>
                    <span class="date ml-auto">日</span>
                  </div>
                  <RubCount :to="item.count" class="panel-count" />
                  <div class="panel-percent">
                    <icon-formkit-arrowup class="icon" />
                    <span>{{ item.percent }}%</span>
                  </div>
                </div>
              </el-col>
            </template>
          </el-row>
        </el-card>
        <!--    授权数    -->
        <el-card shadow="hover" class="mt20">
          <template #header>
            <div class="flex items-center">
              <el-icon size="24"><icon-ep-avatar /></el-icon>
              <div class="pl10 fz18 font-bold">授权数</div>
            </div>
          </template>
          <el-scrollbar>
            <div ref="chartRef" class="chart" />
          </el-scrollbar>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="home">
import { HOME_URL } from '@/config';
import { useEcharts } from '@/hooks/useEcharts';
import { EChartsOption } from 'echarts';
import * as echarts from 'echarts/core';
import { useGlobalStore } from '@/stores/modules/global';
import { useAuthStore } from '@/stores/modules/auth';
import { panelItem, HomeMenuOptions } from './interface';
import { cloneDeep } from 'lodash-es';
import { Menu } from '@/typings/global';

onMounted(() => {
  linesECharts.setOptions(options);
});

const router = useRouter();
const route = useRoute();

// 菜单数据
const authStore = useAuthStore();
const globalStore = useGlobalStore();
const isMobile = computed(() => globalStore.device === 'mobile');
const { showMenuListGet } = storeToRefs(authStore);
const menuList = ref<HomeMenuOptions[]>([]);
watchEffect(() => {
  menuList.value = showMenuListGet.value.slice(1).map(it => {
    let baseLayer: Menu.MenuOptions[] = [];
    let item: HomeMenuOptions = { ...cloneDeep(it), baseLayer };
    return item;
  });
});
const secondLayoutPath = ref<string>('');
const thirdLayoutPath = ref<string>('');
const handleFirstRoute = (index: number | string, item: Menu.MenuOptions): void => {
  secondLayoutPath.value = item.path;
  thirdLayoutPath.value = '';
  menuList.value.forEach(it => (it.baseLayer = []));
  if (item.children?.length) {
    menuList.value[index as number].baseLayer = item.children;
  } else {
    router.push({ name: item.name });
  }
};

const handleSecondRoute = (item: Menu.MenuOptions): void => {
  thirdLayoutPath.value = item.path;
  router.push({ name: item.name });
};

watch(
  () => route.path,
  (toPath: string, fromPath: string | undefined) => {
    if (!fromPath || toPath !== HOME_URL) return;
    const pathNames: string[] = fromPath.split('/').filter(it => !!it);
    const [firstName, secondName] = pathNames;
    const menus: Menu.MenuOptions[] = showMenuListGet.value.slice(1);
    const layoutItemIndex: number = menus.findIndex(item => item.path === `/${firstName}`);
    const layoutItem: Menu.MenuOptions | undefined = menus.find(item => item.path === `/${firstName}`);

    if (layoutItem && layoutItem?.children?.length) {
      const secondLayoutItem: Menu.MenuOptions | undefined = layoutItem?.children.find(
        it => it.path === `/${firstName}/${secondName}`
      );

      if (layoutItemIndex >= 0 && secondLayoutItem) {
        secondLayoutPath.value = secondLayoutItem.path;
        if (secondLayoutItem?.children?.length) {
          menuList.value[layoutItemIndex].baseLayer = secondLayoutItem.children;
          thirdLayoutPath.value = fromPath;
        }
      }
    }
  },
  { immediate: true }
);

// 面板数据
const panels = ref<panelItem[]>([
  { id: 'one', count: 12345, percent: 23.18 },
  { id: 'two', count: 2566, percent: 45.46 },
  { id: 'three', count: 56897, percent: 89.21 },
  { id: 'four', count: 65121, percent: 78.56 }
]);

// chart表格数据
const options = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  grid: {
    left: '0',
    right: '0',
    bottom: '3%',
    top: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    type: 'value',
    splitLine: {
      lineStyle: {
        color: '#e9e9eb',
        type: 'dashed'
      }
    }
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130],
      type: 'bar',
      barWidth: 22, // 柱子宽度，默认自适应
      itemStyle: {
        color: new echarts.graphic.LinearGradient(
          // 设定 0% 处的颜色
          0,
          0,
          0,
          1,
          [
            { offset: 0, color: '#83bff6' },
            { offset: 0.65, color: '#188df0' },
            { offset: 1, color: '#188df0' }
          ]
        )
      }
    }
  ]
} as EChartsOption;
const chartRef = ref<HTMLDivElement | null>(null);
const linesECharts = useEcharts(chartRef as Ref<HTMLDivElement>);
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
