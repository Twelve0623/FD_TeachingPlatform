// ? 全局默认配置项
export type EnvOptionsKeys = 'development' | 'production' | 'test';
export type EnvOptionsType = Record<EnvOptionsKeys, { corpId: string; agentId: string; authUrl: string }>;

// 项目版本号
export const VERSION: string = '1.0.2';

// 首页地址（默认）
export const HOME_URL: string = '/home';

// 登录页地址（默认）
export const LOGIN_URL: string = '/login';

// 路由白名单地址（必须是本地存在的路由 staticRouter.ts 中）
export const ROUTER_WHITE_LIST: string[] = ['/500', '/', '/home'];

// 高德地图 key
export const AMAP_MAP_KEY: string = '';

// 百度地图 key
export const BAIDU_MAP_KEY: string = '';

// 当前环境
export const CURRENT_ENV: string = import.meta.env.VITE_USER_NODE_ENV ?? 'development';

// 环境参数
export const ENV_OPTIONS: EnvOptionsType = {
  development: {
    corpId: '', // 企业ID
    agentId: '', // 代理人id
    authUrl: 'https://xxx/index.html'
  },
  production: {
    corpId: '', // 企业ID
    agentId: '', // 代理人id
    authUrl: 'https://xxx/index.html'
  },
  test: {
    corpId: '', // 企业ID
    agentId: '', // 代理人id
    authUrl: 'https://xxx/index.html'
  }
};

const { corpId, agentId, authUrl } = ENV_OPTIONS[CURRENT_ENV as EnvOptionsKeys];

export const CURRENT_OPTION: Record<string, string> = { currentEnv: CURRENT_ENV, corpId, agentId, authUrl };
