import { RouteRecordRaw } from 'vue-router';
import { HOME_URL, LOGIN_URL } from '@/config';

/**
 * staticRouter (静态路由)
 *    icon: 'HomeFilled', // 菜单和面包屑对应的图标
      title: '首页', // 路由标题 (用作 document.title || 菜单的名称)
      activeMenu: '', // 是否在菜单中隐藏, 需要高亮的 path (通常用作详情页高亮父级菜单)
      sort: 1, // 菜单排序
      isLink: '', // 路由外链时填写的访问地址
      isHide: false, // 是否在菜单中隐藏 (通常列表详情页需要隐藏)
      isFull: false, // 菜单是否全屏 (示例：数据大屏页面)
      isAffix: true, // 菜单是否固定在标签页中 (首页通常是固定项)
      isKeepAlive: true // 当前路由是否缓存
 */
export const staticRouter: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: HOME_URL
  },
  {
    path: LOGIN_URL,
    name: 'login',
    component: () => import('@/views/login/index.vue'),
    meta: {
      title: '登录'
    }
  },
  {
    path: '/layout',
    name: 'layout',
    component: () => import('@/layouts/index.vue'),
    redirect: HOME_URL,
    children: []
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/home/index.vue'),
    meta: {
      title: '首页' // 路由标题 (用作 document.title || 菜单的名称)
    }
  }
];

/**
 * errorRouter (错误页面路由)
 */
export const errorRouter: RouteRecordRaw[] = [
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/ErrorMessage/403.vue'),
    meta: {
      title: '403页面'
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/ErrorMessage/404.vue'),
    meta: {
      title: '404页面'
    }
  },
  {
    path: '/500',
    name: '500',
    component: () => import('@/views/ErrorMessage/500.vue'),
    meta: {
      title: '500页面'
    }
  },
  // Resolve refresh page, route warnings
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/ErrorMessage/404.vue')
  }
];
