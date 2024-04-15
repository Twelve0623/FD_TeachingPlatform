/* Menu */
export declare namespace Menu {
  interface MenuOptions {
    path: string;
    name: string;
    component?: string | (() => Promise<unknown>);
    redirect?: string;
    meta: MetaProps;
    children?: MenuOptions[];
  }
  interface MetaProps {
    icon: string;
    title: string;
    sort?: number;
    activeMenu?: string;
    frameSrc?: string;
    isLink?: string;
    isHide: boolean;
    isFull: boolean;
    isAffix: boolean;
    isKeepAlive: boolean;
    permissionCode: string;
  }
}

/* FileType */
export declare namespace Files {
  type ImageMimeType =
    | 'image/apng'
    | 'image/bmp'
    | 'image/gif'
    | 'image/jpeg'
    | 'image/pjpeg'
    | 'image/png'
    | 'image/svg+xml'
    | 'image/tiff'
    | 'image/webp'
    | 'image/x-icon';

  type ExcelMimeType = 'application/vnd.ms-excel' | 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
}

/* Chart type */
export declare namespace Chart {
  interface Toolbox {
    exportVisible: boolean;
    exportPort: () => void;
  }
  interface SeriesItem {
    type?: string;
    name?: string;
    data: (number | { value: number; [key: string]: any })[];
    [key: string]: any;
  }

  interface DataZoom {
    type?: string;
    start: number;
    end: number;
    [key: string]: any;
  }

  interface Tooltip {
    trigger?: string;
    axisPointer?: { type?: string; [key: string]: any };
  }

  interface Grid {
    top?: string | number;
    bottom?: string | number;
    left?: string | number;
    right?: string | number;
    [key: string]: any;
  }

  type PieSeriesItemType = TreeMapItem;
  /*饼图图表类型*/
  interface PieChartType {
    position?: string;
    name?: string;
    data?: Array<PieSeriesItemType>;
    colors?: string[];
  }

  /*基础图表类型*/
  interface BaseChartType {
    axis?: string[];
    series?: Array<SeriesItem>;
    title?: string;
    colors?: string[];
    xAxisBoundaryGap?: boolean;
    xAxisLabelRotate?: number;
    yAxisUnit?: string;
    yAxisName?: string;
    yAxisVisible?: boolean;
    dataZoom?: DataZoom[];
    tooltip?: Tooltip;
    grid?: Grid;
    toolbox?: Toolbox;
  }

  interface DoubleYAxisChartType {
    title?: string;
    tooltip?: Tooltip;
    xAxisVisible?: boolean;
    xAxisLabelRotate?: number;
    yAxisLeftUnit?: string;
    yAxisRightUnit?: string;
    yAxisLeftVisible?: boolean;
    yAxisLeftTickVisible?: boolean;
    yAxisLeftLineVisible?: boolean;
    yAxisLeftLabelVisible?: boolean;
    yAxisLeftColor?: string;
    yAxisRightVisible?: boolean;
    yAxisRightTickVisible?: boolean;
    yAxisRightLineVisible?: boolean;
    yAxisRightLabelVisible?: boolean;
    yAxisRightColor?: string;
    yAxisNameVisible?: boolean;
    colors?: string[];
    axis?: string[];
    series?: Array<Chart.SeriesItem>;
    grid?: Grid;
    dataZoom?: DataZoom[];
    toolbox?: Toolbox;
  }

  /*rate柱状图 & 折线图二合一图表类型*/
  interface RateChartType {
    name?: string;
    gridBottom?: string;
    axisRotate?: number;
    labelShow?: boolean;
    isDashedLine?: boolean;
    legendData: string[];
    colors: string[];
    axis: string[];
    amountData: number[];
    rateData: number[];
  }

  /*雷达图表类型*/
  type IndicatorItemType = { name: string; max: number };
  interface RadarChartType {
    indicator: IndicatorItemType[];
    series: Array<{
      type: string;
      name?: string;
      data: Array<{ value: number[] }>;
    }>;
  }

  /*四象限图表类型*/
  interface FourQuadrantChartItem {
    name: string;
    code?: string;
    data: [number, number];
  }

  /* RFM矩阵项类型 */
  type RfmMatrixItemType = { xValue: number; yValue: number; name?: string };

  /* 门店矩阵项类型 */
  type ShopMatrixItemType = { yAxisValue: number; xAxisValue: number; name?: string; shopId?: number };

  interface FourQuadrantChartType<T = FourQuadrantChartItem> {
    title?: string;
    xAxisBaseLine?: number;
    xAxisTipsName?: string;
    xAxisUnit?: string;
    yAxisBaseLine?: number;
    yAxisTipsName?: string;
    yAxisUnit?: string;
    originalData?: Array<T>;
    colors?: string[];
    grid?: Grid;
    routeCallback?: (data: any) => void;
    toolbox?: Toolbox;
  }

  /* vertical bar图表类型 */
  interface VerticalBarChartType {
    title?: string;
    name?: string;
    colors?: string[];
    axis?: string[];
    grid?: Grid;
    seriesData?: number[];
    dataZoom?: DataZoom[];
    labelShow?: boolean;
    inverse?: boolean;
    labelUnit?: string;
    barMaxWidth?: number;
    isSettingBarWidth?: boolean;
  }

  interface TreeMapItem {
    name: string;
    value: number;
    itemStyle?: {
      color: string;
    };
    [key: string]: any;
  }

  /* treemap 图表类型 */
  interface TreeMapChartType {
    title?: string;
    colors?: string[];
    data?: TreeMapItem[];
  }

  /* funnel漏斗图 图表类型 */
  type FunnelItemType = {
    value: number;
    number: number;
    name: string;
    rate: number;
    subName: string;
    unit?: string;
    itemStyle?: {
      color: string;
    };
  };
  interface FunnelChartType {
    title?: string;
    colors?: string[];
    seriesData?: FunnelItemType[];
    markLines?: {
      value: number;
      itemValue: string;
      rate: string;
      show: boolean;
    }[];
  }
}

/* Vite */
export declare type Recordable<T = any> = Record<string, T>;

export declare interface ViteEnv {
  VITE_USER_NODE_ENV: 'development' | 'production' | 'test';
  VITE_GLOB_APP_TITLE: string;
  VITE_PORT: number;
  VITE_OPEN: boolean;
  VITE_REPORT: boolean;
  VITE_BUILD_COMPRESS: 'gzip' | 'brotli' | 'gzip,brotli' | 'none';
  VITE_BUILD_COMPRESS_DELETE_ORIGIN_FILE: boolean;
  VITE_DROP_CONSOLE: boolean;
  VITE_PWA: boolean;
  VITE_PUBLIC_PATH: string;
  VITE_API_URL: string;
  VITE_PROXY: [string, string][];
}

export interface ImportMetaEnv extends ViteEnv {
  __: unknown;
}

/* __APP_INFO__ */
export declare const __APP_INFO__: {
  pkg: {
    name: string;
    version: string;
    dependencies: Recordable<string>;
    devDependencies: Recordable<string>;
  };
  lastBuildTime: string;
};

/* Generic Tools */
export declare type ObjToKeyValUnion<T> = {
  [K in keyof T]: { key: K; value: T[K] };
}[keyof T];

export declare type ObjToKeyValArray<T> = {
  [K in keyof T]: [K, T[K]];
}[keyof T];
