import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '@/stores/modules/user';
import { useAuthStore } from '@/stores/modules/auth';
import { LOGIN_URL, ROUTER_WHITE_LIST, HOME_URL } from '@/config';
import { staticRouter, errorRouter } from '@/routers/staticRouter';
import NProgress from '@/config/nprogress';

/**
 * @description 📚 路由参数配置简介
 * @param path ==> 路由菜单访问路径
 * @param name ==> 路由 name (对应页面组件 name, 可用作 KeepAlive 缓存标识 && 按钮权限筛选)
 * @param redirect ==> 路由重定向地址
 * @param component ==> 视图文件路径
 * @param meta ==> 路由菜单元信息
 * @param meta.icon ==> 菜单和面包屑对应的图标
 * @param meta.title ==> 路由标题 (用作 document.title || 菜单的名称)
 * @param meta.activeMenu ==> 当前路由为详情页时，需要高亮的菜单
 * @param meta.isLink ==> 路由外链时填写的访问地址
 * @param meta.isHide ==> 是否在菜单中隐藏 (通常列表详情页需要隐藏)
 * @param meta.isFull ==> 菜单是否全屏 (示例：数据大屏页面)
 * @param meta.isAffix ==> 菜单是否固定在标签页中 (首页通常是固定项)
 * @param meta.isKeepAlive ==> 当前路由是否缓存
 * */
const router = createRouter({
  history: createWebHistory(),
  routes: [...staticRouter, ...errorRouter],
  strict: false,
  scrollBehavior: () => ({ left: 0, top: 0 })
});

/**
 * @description 路由拦截 beforeEach
 * */
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  const authStore = useAuthStore();

  // NProgress 开始
  NProgress.start();

  // 动态设置标题
  const title = import.meta.env.VITE_GLOB_APP_TITLE;
  document.title = to.meta.title ? `${to.meta.title} - ${title}` : title;

  try {
    // 判断访问页面是否在路由白名单地址(静态路由)中，如果存在直接放行
    if (ROUTER_WHITE_LIST.includes(to.path)) {
      return next();
    }
    // 判断是否有 Token，没有重定向到 login 页面
    else if (userStore.token) {
      // 判断是访问登陆页
      if (to.path.toLocaleLowerCase() == LOGIN_URL) {
        return next(HOME_URL);
      } else {
        return next();
      }
    } else {
      throw '未登录';
    }
  } catch (error) {
    console.log('--------------------error:', error);
    console.log('去登录');
    if (to.path.toLocaleLowerCase() == LOGIN_URL) {
      return next();
    } else {
      return next({ path: LOGIN_URL, replace: false });
    }
  } finally {
    // 存储 routerName 做按钮权限筛选
    authStore.setRouteName(to.name as string);
  }
});

/**
 * @description 路由跳转错误
 * */
router.onError(error => {
  NProgress.done();
  console.warn('路由错误', error.message);
});

/**
 * @description 路由跳转结束
 * */
router.afterEach(() => {
  NProgress.done();
});

export default router;
