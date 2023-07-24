export function toFixed(n: number, fixed: number) {
  return ~~(Math.pow(10, fixed) * n) / Math.pow(10, fixed);
}

export function numFormat(num: any) {
  return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
}

// 日期格式化方法，例："YYYY-mm-dd HH-MM-SS"
export function dateFormat(fmt: string, date: Date) {
  let ret;
  const opt: any = {
    "Y+": date.getFullYear().toString(),        // 年
    "m+": (date.getMonth() + 1).toString(),     // 月
    "d+": date.getDate().toString(),            // 日
    "H+": date.getHours().toString(),           // 时
    "M+": date.getMinutes().toString(),         // 分
    "S+": date.getSeconds().toString()          // 秒
    // 有其他格式化字符需求可以继续添加，必须转化成字符串
  };
  for (const k in opt) {
    ret = new RegExp("(" + k + ")").exec(fmt);
    if (ret) {
      fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
    }
  }
  return fmt;
}

// 通过JSON转换方式对对象进行克隆。注意：该方法仅能克隆值类型，无法克隆函数/方法
export function simpleClone(data: any) {
  return JSON.parse(JSON.stringify(data));
}

export function formatNumber(num: string | number) {
  num = Number(num);
  let returnNum
  if (num == 0) {
    returnNum = num + '人'
  } else if (num > 1 && num < 10000) {
    returnNum = num + '人'
  } else {
    returnNum = (num / 10000).toFixed(2) + '万'
  }
  return returnNum

}