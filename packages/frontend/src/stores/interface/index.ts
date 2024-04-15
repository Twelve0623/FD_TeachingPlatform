import { Menu } from '@/typings/global';

export type LayoutType = 'vertical' | 'classic' | 'transverse' | 'columns';

export type AssemblySizeType = 'large' | 'default' | 'small';

export type LanguageType = 'zh' | 'en' | null;

export type DeviceType = 'desktop' | 'mobile';

export type TabBarType = 'card' | 'smart' | 'smooth';

/* GlobalState */
export interface GlobalState {
  language: LanguageType;
}

/* UserState */
export interface UserState {
  token: string;
  code: string;
  userInfo: { name: string };
}

/* tabsMenuProps */
export interface TabsMenuProps {
  icon: string;
  title: string;
  path: string;
  name: string;
  close: boolean;
  isKeepAlive: boolean;
}

/* TabsState */
export interface TabsState {
  tabsMenuList: TabsMenuProps[];
}

/* AuthState */
export interface AuthState {
  routeName: string;
  permissionList: string[]; // 菜单 和 按钮的权限列表
  authMenuList: Menu.MenuOptions[];
}

/* KeepAliveState */
export interface KeepAliveState {
  keepAliveName: string[];
}
