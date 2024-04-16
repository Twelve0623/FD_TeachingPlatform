// 请求参数类型
export interface ReqParams {
  [k: string]: any;
}

// 请求响应参数（不包含data）
export interface Result {
  code: string;
  msg: string;
  requestUUID: string;
  responseTime: string;
  ret: string;
}

// 分页请求参数
export interface ReqPage {
  pager: number;
  size: number;
  pages: number;
  total: number;
}

// 请求响应参数（包含data）
export interface ResultData<T = any> extends Result {
  data: T | any;
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
    password: string | false;
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
