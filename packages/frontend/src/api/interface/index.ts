// 请求参数类型
export interface ReqParams {
  [k: string]: any;
}

// 请求响应参数（不包含data）
export interface Result {
  success: string;
  msg: string;
  requestUUID: string;
  responseTime: string;
  ret: string;
}

export interface ResultPageInfo {
  count: number;
  pageIndex: number;
  pageSize: number;
  totalPage: number;
}

// 请求响应参数（包含data）
export interface ResultData<T = any, U = ResultPageInfo> extends Result {
  shops?: any;
  pageInfo?: U;
  val: T;
}

// 分页响应参数
export interface ResPage<T> {
  list: T[];
  pageNum: number;
  pageSize: number;
  total: number;
}

// 分页请求参数
export interface ReqPage {
  pageNum: number;
  pageSize: number;
}

// 文件上传模块
export namespace Upload {
  export interface ResFileUrl {
    fileUrl: string;
  }
}

// 登录模块
export namespace Login {
  export interface ReqLoginForm {
    studentNo: string;
    password: string;
    code: string;
    uuid: string;
  }
  export interface ResLogin {
    token: string;
    isDefault: string;
  }
  export interface ResLoginCode {
    img: string;
    uuid: string;
  }
  export interface ResAuthButtons {
    [key: string]: string[];
  }
}

// 用户信息
export interface User {
  username: string;
  gender: number;
  idCard: string;
  email: string;
  address: string;
  createTime: string[];
  status: number;
}

export interface BaseTableType {
  current: number;
  hasNext: boolean | null;
  hasPrevious: boolean | null;
  pages: number;
  size: number;
  total: number;
}
