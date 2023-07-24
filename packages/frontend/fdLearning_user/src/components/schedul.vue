<template>
  <!--进度卡 -->
  <div class="schedul">
    <el-card>
      <div class="schedul-title">学习进度</div>
      <div class="progress-item">
        <span class="progress-title">第一章</span>
        <div class="progress-right"><el-progress :percentage="50" /></div>
      </div>
      <div class="progress-item">
        <span class="progress-title">第一章</span>
        <div class="progress-right"><el-progress :percentage="50" /></div>
      </div>
      <div class="progress-item">
        <span class="progress-title">第一章</span>
        <div class="progress-right"><el-progress :percentage="50" /></div>
      </div>
      <div class="progress-item">
        <span class="progress-title">第一章</span>
        <div class="progress-right"><el-progress :percentage="50" /></div>
      </div>
      <div class="progress-item">
        <span class="progress-title">第一章</span>
        <div class="progress-right"><el-progress :percentage="50" /></div>
      </div>
      <div class="progress-item">
        <span class="progress-title">第一章</span>
        <div class="progress-right"><el-progress :percentage="50" /></div>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  name: "schedulView",
});
</script>
<style lang="less" scoped>
.schedul {
  .el-card {
    box-shadow: 2px 2px 3px 1px rgba(59, 161, 255, 0.4);
    .schedul-title {
      font-weight: 700;
      margin-bottom: 20px;
    }
    .progress-item {
      display: flex;
      align-items: center;
      margin-bottom: 10px;
      .progress-title {
        font-size: 12px;
        margin-right: 4px;
      }
      .progress-right {
        // width: 80%;
        flex-grow: 1;
        .el-progress {
          width: 100%;
        }
      }
    }
  }
}
</style>