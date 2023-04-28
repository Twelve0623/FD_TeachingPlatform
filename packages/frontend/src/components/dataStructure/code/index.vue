<template>
  <div class="stack">
    <el-tabs type="border-card">
      <el-tab-pane label="Java">Java</el-tab-pane>
      <el-tab-pane label="JavaScript">JavaScript</el-tab-pane>
      <el-tab-pane label="Python">Python</el-tab-pane>
      <el-tab-pane label="Golang">Golang</el-tab-pane>
    </el-tabs>
    <div class="hide">这是一个div，触发了在COMMON-CSS文件里添加了class名</div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import commonJs from "@/assets/js/commonJs";
import { useStore } from "vuex";
let store = useStore();
onMounted(async () => {
  commonJs.test();
  console.log("vuex中test值为: ", store.state.test);
  store.commit("setTest", "testChange");
  console.log("vuex中test值为: ", store.state.test);
});
</script>
<style scoped lang="scss"></style>
