import axios from 'axios';
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.baseURL = process.env.VUE_APP_BASE_API;
import { getToken, removeToken } from './token';
import router from './router/index';
const errorCode: any = {
  '401': '认证失败，无法访问系统资源',
  '403': '当前操作没有权限',
  '404': '访问资源不存在',
  default: '系统未知错误，请反馈给管理员'
};

axios.interceptors.request.use(
  config => {
    // 是否需要设置 token
    const mytoken = getToken();
    const isToken = (config.headers || {}).isToken === false;
    if (mytoken && !isToken) {
      config.headers['Authorization'] = getToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
    }

    return config;
  },
  (error: unknown) => {
    console.log(error);
    Promise.reject(error);
  }
);

axios.interceptors.response.use(
  res => {
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = errorCode.code || res.data.msg || errorCode['default'];
    if (res.config.url.slice(0, 11) == '/book/login') {
      return res.data;
    } else if (code === 2001 || code === 401) {
      setTimeout(() => {
        removeToken();
        router.push('/');
      }, 1000);
    } else if (code === 311) {
      return res.data;
    } else if (code === 500) {
      return Promise.reject(new Error(msg));
    } else if (code !== 200) {
      return Promise.reject('error');
    } else {
      return res.data;
    }
  },
  (error: string) => {
    console.log('err' + error);
    let message = error;
    if (message == 'Network Error') {
      message = '后端接口连接异常';
    } else if (message.includes('timeout')) {
      message = '系统接口请求超时';
    } else if (message.includes('Request failed with status code')) {
      message = '系统接口' + message.substr(message.length - 3) + '异常';
    }
    return Promise.reject(error);
  }
);

export default axios;
