<template>
  <div class="index">
    <div class="top">
      <img src="../assets/favicon.ico" alt="" />
      <span>复旦继续教育学院智慧云课堂平台</span>
    </div>
    <div class="classList">
      <div
        v-for="(item, index) in classList"
        :key="index"
        class="classList-item"
        @click="goChapter(item.classId)"
      >
        <div class="item-bg"></div>
        <div class="item-className">{{ item.className }}</div>
        <div class="item-chapterNum">{{ item.chapterNum }}章</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import { useRouter } from "vue-router";
export default defineComponent({
  name: "indexView",
  setup() {
    const router = useRouter();
    const classList = reactive([
      {
        className: "数据结构",
        classId: "sjjg",
        chapterNum: 10,
      },
    ]);
    const goChapter = (classId: string) => {
      router.push("/chapter/" + classId);
    };
    return {
      classList,
      goChapter,
    };
  },
});
</script>
<style lang="less" scoped>
.index {
  width: 100%;
  .top {
    background-image: url(../assets/bg.png);
    background-size: 100% 100%;
    display: flex;
    align-items: center;
    width: 100%;
    height: 80px;
    padding: 0 20px;
    box-sizing: border-box;
    img {
      height: 80%;
    }
    span {
      margin-left: 20px;
      color: #fff;
      font-weight: 700;
      font-size: 20px;
    }
  }
  .classList {
    width: 100%;
    padding: 70px 40px 0 40px;
    box-sizing: border-box;
    display: flex;
    .classList-item {
      width: 25%;
      cursor: pointer;
      box-shadow: 2px 2px 3px 1px rgba(59, 161, 255, 0.2);
      .item-bg {
        background-color: aqua;
        height: 200px;
      }
      .item-className {
        font-size: 14px;
        line-height: 24px;
        padding: 10px 20px;
        box-sizing: border-box;
      }
      .item-chapterNum {
        padding: 5px 20px;
        box-sizing: border-box;
      }
    }
  }
}
</style>
