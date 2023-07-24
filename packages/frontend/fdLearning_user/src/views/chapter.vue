<template>
  <div class="chapter">
    <!-- 导航 -->
    <myNav :classId="classId" :activeIndex="activeIndex"></myNav>
    <div class="chapter-content">
      <!-- <el-button>21</el-button> -->
      <div class="content-content-left">
        <!-- 课程介绍 -->
        <div class="className">{{ chapter.className }}</div>
        <div class="introduce">
          <div class="introduce-title">
            <img src="../assets/book1.svg" alt="" />
            课程介绍
          </div>
          <div class="introduce-content">
            {{ chapter.introduce }}
          </div>
        </div>
        <!-- 课程内容 -->
        <div class="chapter-bottom">
          <!-- 子章节导航 -->
          <div class="chapter-nav">
            <div class="chapter-nav-title">
              <img src="../assets/book1.svg" alt="" />
              <span>第一章</span>
              <span>{{ chapter.navLsit[Number(activeIndex)].name }}</span>
            </div>
            <div
              class="chapter-nav-item"
              :class="
                chapterActiveIndex == index ? 'chapter-nav-item-active' : ''
              "
              v-for="(item, index) in chapter.navLsit[Number(activeIndex)].List"
              :key="index"
            >
              <div
                class="line"
                :class="chapterActiveIndex == index ? 'line-active' : ''"
              ></div>
              <span @click="chapterActiveIndex = index"> {{ item.name }}</span>
            </div>
          </div>
          <!-- 详情 -->
          <div class="chapter-details">
            <el-tabs class="demo-tabs" v-model="activeName">
              <el-tab-pane
                :label="item"
                :name="item"
                v-for="(item, index) in chapter.navLsit[Number(activeIndex)]
                  .List[chapterActiveIndex].minList"
                :key="index"
              ></el-tab-pane>
            </el-tabs>
            <Mydetails></Mydetails>
          </div>
        </div>
      </div>
      <div class="content-content-right">
        <el-card shadow="always">
          <div class="singIn">立即签到</div>
          <div class="date">
            <span>{{ dayTime }}</span>
          </div>
        </el-card>
        <el-card shadow="always">
          <div class="data">
            <img src="../assets/book1.svg" alt="" />
            <div>当前在线人数：</div>
          </div>
        </el-card>
        <el-card shadow="always">
          <div class="data">
            <img src="../assets/book1.svg" alt="" />
            <div>登录次数：</div>
          </div>
        </el-card>
        <el-card shadow="always">
          <div class="data">
            <img src="../assets/book1.svg" alt="" />
            <div>累计学习时长：</div>
          </div>
        </el-card>
        <el-card shadow="always">
          <div class="data">
            <img src="../assets/book1.svg" alt="" />
            <div>用户等级：</div>
          </div>
        </el-card>
        <schedul></schedul>
      </div>
    </div>
  </div>
</template>
  
<script lang="ts">
import { defineComponent, ref, reactive, watch } from "vue";
import { dateFormat } from "../utils";
import { useRoute } from "vue-router";
import myNav from "../components/nav.vue";
import schedul from "../components/schedul.vue";
import Mydetails from "../components/details.vue";
export default defineComponent({
  name: "chapterView",
  components: {
    myNav,
    schedul,
    Mydetails,
  },
  setup() {
    const route = useRoute();
    const classId = ref(""); //课程id（目前只有数据结构一门课程）
    const activeIndex = ref(""); //选中章节（顶部导航index)
    const chapterActiveIndex = ref(0); //子章节index,侧导航index
    const dayTime = ref(dateFormat("YYY年mm月dd日", new Date()));
    const classList = [
      {
        className: "数据结构",
        classId: "sjjg",
        navLsit: [
          {
            name: "绪论",
            List: [
              {
                name: "基本概念",
                minList: ["数据", "数据元素", "数据项", "数据对象"],
              },
              {
                name: "线性结构",
                minList: ["数据", "数据元素", "数据项", "数据对象"],
              },
              {
                name: "非线性结构",
                minList: ["数据", "数据元素", "数据项", "数据对象"],
              },
              {
                name: "储存结构",
                minList: ["数据", "数据元素", "数据项", "数据对象"],
              },
              {
                name: "算法",
                minList: ["数据", "数据元素", "数据项", "数据对象"],
              },
            ],
          },
          {
            name: "线性表",
            List: [],
          },
          {
            name: "栈",
            List: [],
          },
          {
            name: "队列",
            List: [],
          },
          {
            name: "字符串",
            List: [],
          },
          {
            name: "树",
            List: [],
          },
          {
            name: "图",
            List: [],
          },
          {
            name: "查找算法",
            List: [],
          },
          {
            name: "排序算法",
            List: [],
          },
          {
            name: "综合测试",
            List: [],
          },
        ],
        introduce:
          "本课程是计算机类专业的专业基础必修课。课程系统地介绍了基本数据结构知识、算法设计与分析方法，包括常见数据结构（线性表、树、图）表示方法、操作及应用；常用查找技术与排序算法等。课程使用中国大学 MOOC、慕课堂、头歌平台等进行线上线下混合教学。通过本课程的学习，学生能掌握数据抽象基本方法，理解计算机内部数据对象的表示和特性、常见数据结构上定义的基本操作和算法；在实际工程问题中能分析问题并为选用或设计适当的数据逻辑结构、存储结构，能设计相应的处理算法解决问题；能熟练运用算法的时间复杂度、空间复杂度分析方法分析算法并优化。",
      },
    ];
    classId.value = route.params.classId as string;
    activeIndex.value = route.query.activeIndex
      ? (route.query.activeIndex as string)
      : "0";
    const chapter = reactive(
      classList.filter((item) => {
        if (item.classId == classId.value) {
          return item;
        }
      })[0]
    ); //根据classId获取该课程下的数据列表
    const activeName = ref(
      chapter.navLsit[Number(activeIndex.value)].List[chapterActiveIndex.value]
        .minList[0]
    ); //子章节下的标题，标签页的选中名称
    watch(activeName, (value) => {
      console.log(value);
    });
    return {
      classId,
      chapter,
      activeIndex,
      chapterActiveIndex,
      activeName,
      dayTime,
    };
  },
});
</script>
<style scoped lang="less">
.chapter {
  width: 100%;
  min-height: 100%;
  .chapter-content {
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding: 20px 30px;
    box-sizing: border-box;
    .content-content-left {
      width: 80%;
      padding-right: 30px;
      box-sizing: border-box;
      .className {
        font-size: 24px;
        font-weight: 700;
        margin-bottom: 30px;
      }
      .introduce {
        .introduce-title {
          display: flex;
          align-items: center;
          font-weight: 700;
          img {
            width: 30px;
            margin-right: 4px;
          }
        }
        .introduce-content {
          font-size: 14px;
          line-height: 20px;
          margin-top: 20px;
        }
      }
      .chapter-bottom {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
        .chapter-nav {
          width: 200px;
          font-weight: 700;
          border: 1px solid #ccc;
          .chapter-nav-title {
            display: flex;
            background-color: #ccc;
            align-items: center;
            margin-bottom: 30px;
            line-height: 40px;
            padding-left: 10px;
            box-sizing: border-box;
          }
          .chapter-nav-item {
            color: #000;
            height: 30px;
            display: flex;
            align-items: center;
            margin-bottom: 12px;
            cursor: pointer;
            .line {
              width: 5px;
              height: 100%;
              background-color: transparent;
              margin-right: 7px;
            }
            .line-active {
              background-color: #3ba1ff;
            }
          }
          .chapter-nav-item-active {
            color: #3ba1ff;
          }
        }
        .chapter-details {
          margin: 0 0 0 40px;
          padding: 0 20px;
          border: 1px solid #ccc;
          box-sizing: border-box;
          flex-grow: 1;
        }
      }
    }
    .content-content-right {
      width: 20%;
      .el-card {
        box-shadow: 2px 2px 3px 1px rgba(59, 161, 255, 0.4);
        margin-bottom: 10px;
        .singIn {
          width: 80%;
          background-color: #3ba1ff;
          color: #fff;
          text-align: center;
          border-radius: 30px;
          line-height: 30px;
          margin: 0 auto;
        }
        .date {
          text-align: center;
          margin-top: 30px;
        }
        .data {
          width: 100%;
          display: flex;
          align-items: center;
          img {
            width: 20px;
            margin-right: 10px;
          }
        }
      }
    }
  }
}
</style>