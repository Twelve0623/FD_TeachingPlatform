import { ReqPage, ResultData } from '@/api/interface';
import { PORT1 } from '@/api/config/servicePort';
import http from '@/api';

/**
 *  首页
 */
// 课程列表

export const fetchHomeClassList = (params: ReqPage) => {
  return http.post<ResultData>(PORT1 + '/course/coursePage', params);
};
