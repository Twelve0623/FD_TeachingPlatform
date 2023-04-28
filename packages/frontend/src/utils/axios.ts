import axios from "axios";
// import router from "../router";

// axios.defaults.baseURL = "http://192.168.8.187:8888/";
// axios.defaults.withCredentials = true;
// axios.defaults.headers["X-Requested-With"] = "XMLHttpRequest";
// axios.defaults.headers["token"] = localStorage.getItem("token") || "";
// axios.defaults.headers.post["Content-Type"] = "application/json";

// axios.interceptors.response.use((res) => {
//   if (typeof res.data !== "object") {
//     return Promise.reject(res);
//   }
//   if (res.data.resultCode != 200) {
//     if (res.data.resultCode == 416) {
//       //   router.push({ path: "/login" });
//     }
//     return Promise.reject(res.data);
//   }

//   return res.data;
// });

// axios.defaults.headers.common["token"] = "";
// axios.interceptors.request.use(
//   (config: any) => {
//     const token = window.sessionStorage.getItem("accessToken");
//     if (token && config.method === "post") {
//       config.headers.common["token"] = token;
//     }
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

// export default axios;
const service = axios.create({
  baseURL: "http://localhost/",
});

// 添加请求拦截器
// service.interceptors.request.use(
//   function (config: any) {
//     // 在发送请求之前做些什么
//     const token = window.sessionStorage.getItem("accessToken");
//     console.log(token);
//     if (
//       token != null &&
//       token != "" &&
//       token != undefined &&
//       token != "undefined"
//     ) {
//       config.headers.common["token"] = token;
//     }
//     return config;
//   },
//   function (error) {
//     // 对请求错误做些什么
//     return Promise.reject(error);
//   }
// );

// 添加响应拦截器
// service.interceptors.response.use(
//   function (response) {
//     // 对响应数据做点什么
//     console.log("res", response);
//     return response;
//   },
//   function (error) {
//     // 对响应错误做点什么
//     return Promise.reject(error);
//   }
// );
// const service = axios;
export default service;
