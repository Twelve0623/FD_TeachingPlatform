<template>
  <div class="index">
    <div
      class="echarts"
      ref="echartsRef"
      :style="{
        width: '600px',
        height: '300px',
      }"
    ></div>
  </div>
</template>

<script setup lang="ts">
import { ref, markRaw, onMounted } from "vue";
import * as echarts from "echarts";
const echartsRef = ref<HTMLElement>();
onMounted(() => {
  const myEcharts = echarts.init(echartsRef.value!);
  //要操作的配置
  const option = {
    title: {
      text: "ECharts 入门示例",
    },
    tooltip: {},
    legend: {
      //根据name决定展示顺序
      data: ["销量"],
    },
    xAxis: {
      data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
    },
    yAxis: {},
    series: [
      {
        name: "销量",
        type: "bar", //bar为柱状图，pie为饼状图
        data: [5, 20, 36, 10, 10, 20],
      },
    ],
  };
  //设置配置
  myEcharts.setOption(option);
});
</script>
<style scoped lang="scss"></style>
