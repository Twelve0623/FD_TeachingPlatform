import type { EChartsOption } from 'echarts';
import echarts from '@/utils/echarts';
import { useGlobalStore } from '@/stores/modules/global';

export function useEcharts(elRef: Ref<HTMLDivElement> | null) {
  const globalStore = useGlobalStore();
  const { isCollapse } = storeToRefs(globalStore);

  let chartInstance: echarts.ECharts | null = null;
  let removeResizeFn: Fn = () => {};

  const resizeStatus = ref(false);

  // 缓存 options参数
  const cacheOptions: Ref<EChartsOption> = ref({});

  // resize 重置尺寸
  const resize = () => {
    resizeStatus.value = true;
    chartInstance?.resize();
  };

  const initCharts = () => {
    const el = toValue(elRef);
    if (!el || !toValue(el)) return;

    // chartInstance = echarts.init(el, isDark.value ? 'dark' : 'light');
    chartInstance = echarts.init(el);
    removeResizeFn = useEventListener(window, 'resize', resize);
  };

  // 设置 options
  const setOptions = (options: EChartsOption, clear = true) => {
    cacheOptions.value = options;
    if (toValue(elRef)?.offsetHeight === 0) {
      useTimeoutFn(() => {
        setOptions(cacheOptions.value);
      }, 30);
      return;
    }
    nextTick(() => {
      useTimeoutFn(() => {
        if (!chartInstance) {
          initCharts();
          if (!chartInstance) return;
        }
        clear && chartInstance?.clear();
        chartInstance?.setOption(cacheOptions.value);
      }, 30);
    });
  };

  function dispose() {
    if (!chartInstance) return;
    removeResizeFn();
    chartInstance.dispose();
    chartInstance = null;
  }

  function getInstance(): echarts.ECharts | null {
    if (!chartInstance) {
      initCharts();
    }
    return chartInstance;
  }

  // 安全的onUnmounted。如果onUnmounted()在组件生命周期内，就调用它，否则什么都不做
  tryOnUnmounted(dispose);

  // 防止 echarts 页面 keepAlive 时，还在继续监听页面
  // onDeactivated(dispose);

  // 页面 keepAlive 时，继续监听页面
  onActivated(() => {
    if (resizeStatus.value) {
      resizeStatus.value = false;
      resize();
    }
  });

  // 菜单折叠时 触发chart resize
  watch(isCollapse, () => {
    useTimeoutFn(resize, 300);
  });

  return { echarts, getInstance, setOptions, resize };
}
