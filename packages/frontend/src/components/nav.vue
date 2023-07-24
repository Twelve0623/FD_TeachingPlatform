<template>
  <el-affix :offset="0">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#3ba1ff"
      text-color="#fff"
      active-text-color="#027DB4"
    >
      <router-link to="/">
        <el-menu-item index="-2">首页</el-menu-item>
      </router-link>
      <el-menu-item index="-1">
        <img src="../assets/book.svg" />
        <span style="font-size: 20px; margin-left: 5px"
          >{{ nav.className }}云课堂</span
        >
      </el-menu-item>

      <el-menu-item
        v-for="(item, index) in nav.navLsit"
        :key="index"
        :index="index.toString()"
        @click="goLink(item.link)"
      >
        {{ item.name }}
      </el-menu-item>
      <el-sub-menu index="15">
        <template #title>涂小样</template>
        <el-menu-item index="2-1" @click="goLink('/personal')"
          >个人中心</el-menu-item
        >
        <el-menu-item index="2-2" @click="goLink('/changePass')">修改密码</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-affix>
</template>
  
<script lang="ts">
import { defineComponent, reactive } from "vue";
import { useRouter } from "vue-router";
export default defineComponent({
  name: "navView",
  props: {
    classId: String,
    activeIndex: String,
  },
  setup(props) {
    const router = useRouter();
    // 根据课程名称来显示导航列表，暂时只有数据结构课程
    const classList = [
      {
        className: "数据结构",
        classId: "sjjg",
        navLsit: [
          {
            name: "绪论",
            link: "/",
          },
          {
            name: "线性表",
            link: "/",
          },
          {
            name: "栈",
            link: "/",
          },
          {
            name: "队列",
            link: "/",
          },
          {
            name: "字符串",
            link: "/",
          },
          {
            name: "树",
            link: "/",
          },
          {
            name: "图",
            link: "/",
          },
          {
            name: "查找算法",
            link: "/",
          },
          {
            name: "排序算法",
            link: "/",
          },
          {
            name: "综合测试",
            link: "/",
          },
        ],
      },
      {
        className: "计算机理论基础",
        classId: "jsj",
        navLsit: [],
      },
    ];

    const nav = reactive(
      classList.filter((item) => {
        if (item.classId == props["classId"]) {
          return item;
        }
      })[0]
    );
    const goLink = (path: string) => {
      router.push(path);
    };
    return {
      nav,
      goLink,
    };
  },
});
</script>
<style scoped>
.el-menu {
  justify-content: space-around;
}
.el-menu,
.el-menu-item {
  height: 70px;
  line-height: 70px;
}
.el-menu-item img {
  height: 80%;
}
.user {
  justify-content: flex-end;
}
</style>