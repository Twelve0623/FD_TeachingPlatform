<template>
  <div id="questionStatus" ref="echartsRef" class="questionStatus" ></div>
</template>
<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import * as echarts from "echarts";
export default defineComponent({
  name: "demoView",
  setup() {
    const echartsRef = ref<HTMLElement | null>(
      document.getElementById("questionStatus")
    );
    onMounted(() => {
      //初始化echarts实例init（ dom ，'主题' ，其余参数如：{ renderer:'svg' }）
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
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

    return { echartsRef };
  },
});
</script>
<style lang="less" scoped>
.questionStatus {
  width: 400px;
  height: 500px;
}
</style>