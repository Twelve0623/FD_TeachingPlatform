<template>
  <div class="app-home">
    <div class="home-top">
      <img class="home-top-logo" src="@/assets/images/favicon.ico" alt="" />
      <span class="home-top-title">复旦继续教育学院智慧云课堂平台</span>
      <div class="home-top-btn">
        <el-button v-if="!isAuth" type="primary" @click="handleLogin()">登录</el-button>
        <el-button v-else type="primary" @click="handleLogout()">退出登录</el-button>
      </div>
    </div>
    <div class="home-body">
      <div v-for="(item, index) in classList" :key="index" class="classList-item" @click="goChapter()">
        <div class="item-bg"></div>
        <div class="item-className">{{ item.courseName }}</div>
        <div class="item-chapterNum">{{ item.courseChapterNumber }}章</div>
        <!-- <div class="item-learnedNum">{{ item.learnedNumber }}章</div> -->
      </div>
    </div>
    <div class="pager-wrap">
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="pageInfo.pager"
        :total="Number(pageInfo.total)"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts" name="home">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { classType } from './interface';
import { ReqPage } from '@/api/interface';
import { fetchHomeClassList } from '@/api/modules/home';
import { useUserStore } from '@/stores/modules/user';
const userStore = useUserStore();
const router = useRouter();
const classList = ref<classType | any>([]);
const isAuth = ref<boolean>(false);

const pageInfo = ref<ReqPage>({
  pager: 1,
  size: 10,
  pages: 0,
  total: 0
});
async function getClassList() {
  try {
    let { code, msg, data } = await fetchHomeClassList({ ...pageInfo.value });
    if (code == '0') {
      pageInfo.value.total = data?.total || 0;
      // classList.value = data.list;
      // mock
      classList.value = [
        {
          id: 1,
          courseName: '数据结构',
          courseProfile: '测试',
          courseImages: '',
          courseType: 0,
          courseExt: '',
          courseStatus: 0,
          courseChapterNumber: 20,
          learnedNumber: 1001
        }
      ];
    } else {
      throw { msg };
    }
  } catch (error: any) {
    console.log(error);
  }
}
const handleCurrentChange = (val: number) => {
  pageInfo.value.pager = val;
  getClassList();
};
const goChapter = () => {
  // router.push('/chapter/' + classId);
};
const handleLogin = () => {
  router.push('/login');
};
const handleLogout = () => {
  userStore.setToken('');
  router.push('/login');
};
onMounted(() => {
  isAuth.value = userStore.token ? true : false;
  getClassList();
});
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
