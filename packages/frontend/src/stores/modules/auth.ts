import { defineStore } from 'pinia';
import { AuthState } from '@/stores/interface';
import { Menu } from '@/typings/global';

// 获取模块的路由
const modules: Record<string, any> = import.meta.glob('@/routers/modules/**/*.ts', { eager: true });
const routeList: Menu.MenuOptions[] = [];
Object.keys(modules).forEach(key => {
  const route = modules[key].default || {};
  routeList.push(route);
});

function sortRoute(a: Menu.MenuOptions, b: Menu.MenuOptions) {
  return (a.meta?.sort || 0) - (b.meta?.sort || 0);
}

routeList.sort(sortRoute);

export const useAuthStore = defineStore({
  id: 'report-auth',
  state: (): AuthState => ({
    // 权限列表
    permissionList: [],
    // 菜单列表
    authMenuList: [],
    // 当前页面的 router name，用来做按钮权限筛选
    routeName: ''
  }),
  getters: {
    // 菜单权限列表 ==> 这里的菜单没有经过任何处理
    authMenuListGet: (state: AuthState) => state.authMenuList
  },
  actions: {
    // Set RouteName
    setRouteName(name: string) {
      this.routeName = name;
    }
  }
});
