import { isArray } from '@/utils/is';
import { Menu } from '@/typings/global';
import { cloneDeep } from 'lodash-es';

/**
 * @description 获取localStorage
 * @param {String} key Storage名称
 * @returns {String}
 */
export function localGet(key: string) {
  const value = window.localStorage.getItem(key);
  try {
    return JSON.parse(window.localStorage.getItem(key) as string);
  } catch (error) {
    return value;
  }
}

/**
 * @description 存储localStorage
 * @param {String} key Storage名称
 * @param {*} value Storage值
 * @returns {void}
 */
export function localSet(key: string, value: any) {
  window.localStorage.setItem(key, JSON.stringify(value));
}

/**
 * @description 清除localStorage
 * @param {String} key Storage名称
 * @returns {void}
 */
export function localRemove(key: string) {
  window.localStorage.removeItem(key);
}

/**
 * @description 清除所有localStorage
 * @returns {void}
 */
export function localClear() {
  window.localStorage.clear();
}

/**
 * @description 判断数据类型
 * @param {*} val 需要判断类型的数据
 * @returns {String}
 */
export function isType(val: any) {
  if (val === null) return 'null';
  if (typeof val !== 'object') return typeof val;
  else return Object.prototype.toString.call(val).slice(8, -1).toLocaleLowerCase();
}

/**
 * @description 生成唯一 uuid
 * @returns {String}
 */
export function generateUUID() {
  let uuid = '';
  for (let i = 0; i < 32; i++) {
    let random = (Math.random() * 16) | 0;
    if (i === 8 || i === 12 || i === 16 || i === 20) uuid += '-';
    uuid += (i === 12 ? 4 : i === 16 ? (random & 3) | 8 : random).toString(16);
  }
  return uuid;
}

/**
 * 判断两个对象是否相同
 * @param {Object} a 要比较的对象一
 * @param {Object} b 要比较的对象二
 * @returns {Boolean} 相同返回 true，反之 false
 */
export function isObjectValueEqual(a: { [key: string]: any }, b: { [key: string]: any }) {
  if (!a || !b) return false;
  let aProps = Object.getOwnPropertyNames(a);
  let bProps = Object.getOwnPropertyNames(b);
  if (aProps.length != bProps.length) return false;
  for (let i = 0; i < aProps.length; i++) {
    let propName = aProps[i];
    let propA = a[propName];
    let propB = b[propName];
    if (!b.hasOwnProperty(propName)) return false;
    if (propA instanceof Object) {
      if (!isObjectValueEqual(propA, propB)) return false;
    } else if (propA !== propB) {
      return false;
    }
  }
  return true;
}

/**
 * @description 生成随机数
 * @param {Number} min 最小值
 * @param {Number} max 最大值
 * @returns {Number}
 */
export function randomNum(min: number, max: number): number {
  let num = Math.floor(Math.random() * (min - max) + max);
  return num;
}

/**
 * @description 获取当前时间对应的提示语
 * @returns {String}
 */
export function getTimeState() {
  let timeNow = new Date();
  let hours = timeNow.getHours();
  if (hours >= 6 && hours <= 10) return '早上好 ⛅';
  if (hours >= 10 && hours <= 14) return '中午好 🌞';
  if (hours >= 14 && hours <= 18) return '下午好 🌞';
  if (hours >= 18 && hours <= 24) return '晚上好 🌛';
  if (hours >= 0 && hours <= 6) return '凌晨好 🌛';
}

/**
 * @description 获取浏览器默认语言
 * @returns {String}
 */
export function getBrowserLang() {
  let browserLang = navigator.language ? navigator.language : navigator.browserLanguage;
  let defaultBrowserLang = '';
  if (['cn', 'zh', 'zh-cn'].includes(browserLang.toLowerCase())) {
    defaultBrowserLang = 'zh';
  } else {
    defaultBrowserLang = 'en';
  }
  return defaultBrowserLang;
}

/**
 * @description 使用递归扁平化菜单，方便添加动态路由
 * @param {Array} menuList 菜单列表
 * @param {Array} permissionList 权限code列表
 * @returns {Array}
 */
export function getFlatMenuList(menuList: Menu.MenuOptions[], permissionList: string[]): Menu.MenuOptions[] {
  // let newMenuList: Menu.MenuOptions[] = JSON.parse(JSON.stringify(menuList));
  let newMenuList: Menu.MenuOptions[] = cloneDeep(menuList);
  return newMenuList.flatMap(item => [
    ...(permissionList.includes(item.meta.permissionCode) || item.meta.title === '首页' ? [item] : []),
    ...(item.children ? getFlatMenuList(item.children, permissionList) : [])
  ]);
}

/**
 * @description 使用递归过滤出需要渲染在左侧菜单的列表 (需剔除 isHide == true 的菜单)
 * @param {Array} menuList 菜单列表
 * @param {Array} permissionList 权限code列表
 * @returns {Array}
 */
export function getShowMenuList(menuList: Menu.MenuOptions[], permissionList: string[]): Menu.MenuOptions[] {
  let newMenuList: Menu.MenuOptions[] = JSON.parse(JSON.stringify(menuList));
  return newMenuList.filter(item => {
    item.children?.length && (item.children = getShowMenuList(item.children, permissionList));
    return !item.meta?.isHide && (permissionList.includes(item.meta.permissionCode) || item.meta.title === '首页');
  });
}

/**
 * @description 使用递归找出所有面包屑存储到 pinia/vuex 中
 * @param {Array} menuList 菜单列表
 * @param {Array} parent 父级菜单
 * @param {Object} result 处理后的结果
 * @returns {Object}
 */
export const getAllBreadcrumbList = (menuList: Menu.MenuOptions[], parent = [], result: { [key: string]: any } = {}) => {
  for (const item of menuList) {
    result[item.path] = [...parent, item];
    if (item.children) getAllBreadcrumbList(item.children, result[item.path], result);
  }
  return result;
};

/**
 * @description 使用递归处理路由菜单 path，生成一维数组 (第一版本地路由鉴权会用到，该函数暂未使用)
 * @param {Array} menuList 所有菜单列表
 * @param {Array} menuPathArr 菜单地址的一维数组 ['**','**']
 * @returns {Array}
 */
export function getMenuListPath(menuList: Menu.MenuOptions[], menuPathArr: string[] = []): string[] {
  for (const item of menuList) {
    if (typeof item === 'object' && item.path) menuPathArr.push(item.path);
    if (item.children?.length) getMenuListPath(item.children, menuPathArr);
  }
  return menuPathArr;
}

/**
 * @description 递归查询当前 path 所对应的菜单对象 (该函数暂未使用)
 * @param {Array} menuList 菜单列表
 * @param {String} path 当前访问地址
 * @returns {Object | null}
 */
export function findMenuByPath(menuList: Menu.MenuOptions[], path: string): Menu.MenuOptions | null {
  for (const item of menuList) {
    if (item.path === path) return item;
    if (item.children) {
      const res = findMenuByPath(item.children, path);
      if (res) return res;
    }
  }
  return null;
}

/**
 * @description 使用递归过滤需要缓存的菜单 name (该函数暂未使用)
 * @param {Array} menuList 所有菜单列表
 * @param {Array} keepAliveNameArr 缓存的菜单 name ['**','**']
 * @returns {Array}
 */
export function getKeepAliveRouterName(menuList: Menu.MenuOptions[], keepAliveNameArr: string[] = []) {
  menuList.forEach(item => {
    item.meta.isKeepAlive && item.name && keepAliveNameArr.push(item.name);
    item.children?.length && getKeepAliveRouterName(item.children, keepAliveNameArr);
  });
  return keepAliveNameArr;
}

/**
 * @description 格式化表格单元格默认值 (el-table-column)
 * @param {Number} row 行
 * @param {Number} col 列
 * @param {*} callValue 当前单元格值
 * @returns {String}
 */
export function formatTableColumn(row: number, col: number, callValue: any) {
  // 如果当前值为数组，使用 / 拼接（根据需求自定义）
  if (isArray(callValue)) return callValue.length ? callValue.join(' / ') : '--';
  return callValue ?? '--';
}

/**
 * @description 处理值无数据情况
 * @param {*} callValue 需要处理的值
 * @returns {String}
 */
export function formatValue(callValue: any) {
  // 如果当前值为数组，使用 / 拼接（根据需求自定义）
  if (isArray(callValue)) return callValue.length ? callValue.join(' / ') : '--';
  return callValue ?? '--';
}

/**
 * @description 处理 prop 为多级嵌套的情况，返回的数据 (列如: prop: user.name)
 * @param {Object} row 当前行数据
 * @param {String} prop 当前 prop
 * @returns {*}
 */
export function handleRowAccordingToProp(row: { [key: string]: any }, prop: string) {
  if (!prop.includes('.')) return row[prop] ?? '--';
  prop.split('.').forEach(item => (row = row[item] ?? '--'));
  return row;
}

/**
 * @description 处理 prop，当 prop 为多级嵌套时 ==> 返回最后一级 prop
 * @param {String} prop 当前 prop
 * @returns {String}
 */
export function handleProp(prop: string) {
  const propArr = prop.split('.');
  if (propArr.length == 1) return prop;
  return propArr[propArr.length - 1];
}

/**
 * @description 递归查找 callValue 对应的 enum 值
 */
export function findItemNested(enumData: any, callValue: any, value: string, children: string) {
  return enumData.reduce((accumulator: any, current: any) => {
    if (accumulator) return accumulator;
    if (current[value] === callValue) return current;
    if (current[children]) return findItemNested(current[children], callValue, value, children);
  }, null);
}

/**
 * @description 递归过滤 按钮权限的指定的 key
 */
export function filterPermissionsKey<T, K extends keyof T, U extends keyof T>({
  target,
  children,
  key,
  isIncludeTrack = false
}: {
  target: T;
  children: K;
  key: U;
  isIncludeTrack?: boolean; // 是否包含路径依赖的节点key
}): Array<T[U]> {
  return (target[children] as Array<T>).reduce(
    (cache, item) => {
      if ((item[children] as Array<T>)?.length) {
        isIncludeTrack && cache.push(item[key]);
        cache.push(
          ...filterPermissionsKey<T, K, U>({
            target: item,
            children,
            key,
            isIncludeTrack: isIncludeTrack
          })
        );
      } else {
        cache.push(item[key]);
      }
      return cache;
    },
    [] as Array<T[U]>
  );
}

/**
 * 数字千分位格式化
 * @param {number} n 原始值
 * @returns {string}
 */
export const formatNumber = (n: number | string): string => {
  n = Number(n);
  if (n < 1000) return `${n}`;
  let num = n.toString(),
    decimals = '';
  if (num.includes('.')) {
    [num, decimals] = num.split('.');
  }
  let remainder = num.length % 3;
  if (remainder > 0) {
    // 不是3的整数倍
    num = num.slice(0, remainder) + ',' + num.slice(remainder).match(/\d{3}/g)?.join(',');
  } else {
    // 3的整数倍
    num = num.match(/\d{3}/g)?.join(',') || '0';
  }
  return decimals ? `${num}.${decimals}` : num;
};

/**
 * 对table表格的单项cell数据进行过滤
 * @returns {string | number}
 * @param row 对象
 * @param key 对象的key
 */
export const handleFilterTableRow = <T>(row: T, key: keyof T): T[keyof T] | string | number => {
  const value = row[key];
  if (
    (key as string).endsWith('Per') ||
    (key as string).endsWith('Rate') ||
    (key as string).endsWith('RateGrandTotal') ||
    (key as string).endsWith('Ratio') ||
    (key as string).endsWith('Margin') ||
    (key as string).endsWith('MarginSigma') ||
    (key as string).endsWith('Structure')
  ) {
    return value || value === 0 ? `${value}%` : '/';
  }
  return value || value === 0 ? value : '/';
};

/**
 * 获取地址栏参数
 * @returns {string | number}
 * @param name string
 * @param search 网络地址的path
 * @return  string | null
 */
export function getQueryString(name: string, search = window.location.href): string | null {
  search = search.split('?')[1];
  if (!search) return null;
  const reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
  const r = search.match(reg);
  if (r !== null) return decodeURIComponent(r[2]);
  // if (r !== null) return unescape(r[2]);
  return null;
}

/**
 * @description: 根据ID获取对应的字段提示描述
 * @param id 字段ID
 * @return  string
 */
export const getQuestionMapItem = (id: number) => {
  let data: any = localGet('questionMap');
  let tip: string = '无';
  data.map((item: any) => {
    if (item.id == id) {
      tip = item.targetDescription;
    }
  });
  return tip;
};

/**
 * @description: 根据ID获取对应的字段键值对数组 eg. [{name:'A',description:'B'}]
 * @param ids 字段ID数组集合
 * @return  array
 */
export const getQuestionMapObj = (ids: any) => {
  let data: any = localGet('questionMap');
  let tips = <any>[];
  data.map((item: any) => {
    if (ids.includes(item.id)) {
      tips.push({
        name: item.targetName,
        description: item.targetDescription
      });
    }
  });
  return tips;
};
