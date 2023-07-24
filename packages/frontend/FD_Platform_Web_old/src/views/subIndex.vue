<template>
  <div class="sub-index">
    <el-container>
      <el-aside width="200px">
        <el-aside width="200px">
          <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
            <el-radio-button :label="false">expand</el-radio-button>
            <el-radio-button :label="true">collapse</el-radio-button>
          </el-radio-group>
          <el-menu default-active="/home/dataStructure/stack_code" class="el-menu-vertical-demo" :collapse="isCollapse"
            @open="handleOpen" @close="handleClose" router="true">
            <el-sub-menu>
              <template #title index="stack">
                <el-icon>
                  <location />
                </el-icon>
                <span>链表</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/home/dataStructure/stack_code">代码</el-menu-item>
                <el-menu-item index="/home/dataStructure/stack_chart">绘图</el-menu-item>

                <el-menu-item index="/home/dataStructure/BasicArray">顺序表</el-menu-item>

                <el-menu-item index="">单链表</el-menu-item>
                <el-menu-item index="">双链表</el-menu-item>
                <el-menu-item index="">循环链表</el-menu-item>
                <el-menu-item index="">队列</el-menu-item>
                <el-menu-item index="">栈</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-aside>
      </el-aside>
      <el-main><router-view /></el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from "@element-plus/icons-vue";

const isCollapse = ref(true);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
<style scoped lang="scss">
.sub-index {
  background: #fff;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
