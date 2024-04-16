import { Login } from '@/api/interface';
import { PORT1 } from '@/api/config/servicePort';
import http from '@/api';

/**
 *  登录模块
 */
// 用户登录

export const loginApi = (params: Login.ReqLoginForm) => {
  return http.post<Login.ResLogin>(PORT1 + '/login/studentLogin', params, { noLoading: true }); // 正常 post json 请求  ==>  application/json
};

// 获取验证码
export const getCodeImg = () => {
  return http.get<Login.ResLoginCode>(PORT1 + '/login/getCode');
};

// 用户户退出登录
export const logoutApi = () => {
  return http.post(PORT1 + '/logout');
};
