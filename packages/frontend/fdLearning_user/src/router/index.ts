import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Login from '../views/login.vue'
// import { getToken } from "../token";
//没有token时也不跳转的页面
// const wxlist: Array<string> = ['/'];
const routes: Array<RouteRecordRaw> = [
  {
    path: '/Login',//登录页面
    name: 'Login',
    component: Login
  },
  {
    path: '/',//首页
    name: 'index',
    component: () => import('../views/index.vue')
  },
  {
    path: '/chapter/:classId',//章节页面
    name: 'chapter',
    component: () => import('../views/chapter.vue')
  },
  {
    path: '/personal',//个人中心
    name: 'personal',
    component: () => import('../views/personal.vue')
  },
  {
    path: '/changePass',//修改密码
    name: 'changePass',
    component: () => import('../views/changePass.vue')
  },
  {
    path: '/about',//关于我们
    name: 'about',
    component: () => import('../views/about.vue')
  },
  {
    path: '/demo',//echart使用实例
    name: 'demo',
    component: () => import('../views/echartsDemo.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
// router.beforeEach((to, from,next) => {
//   //当没有登录时跳转到登录页面
//   if (to.name !== 'Login' && !getToken()) next({ name: 'Login' })
//   else next()
// })
export default router
