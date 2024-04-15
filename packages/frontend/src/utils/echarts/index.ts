// * Echarts 按需引入
import * as echarts from 'echarts/core';
import {
  BarChart,
  LineChart,
  PieChart,
  MapChart,
  PictorialBarChart,
  RadarChart,
  ScatterChart,
  TreemapChart,
  FunnelChart
} from 'echarts/charts';

import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  // 数据集组件
  DatasetComponent,
  PolarComponent,
  AriaComponent,
  ParallelComponent,
  RadarComponent,
  ToolboxComponent,
  DataZoomComponent,
  VisualMapComponent,
  TimelineComponent,
  CalendarComponent,
  MarkLineComponent,
  LegendComponent,
  // 内置数据转换器组件 (filter, sort)
  TransformComponent,
  // 线性渐变组件
  GraphicComponent
} from 'echarts/components';

import { LabelLayout, UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';

// 注册必须的组件
echarts.use([
  BarChart,
  LineChart,
  PieChart,
  MapChart,
  PictorialBarChart,
  RadarChart,
  ScatterChart,
  TreemapChart,
  FunnelChart,
  LegendComponent,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  PolarComponent,
  AriaComponent,
  ParallelComponent,
  RadarComponent,
  ToolboxComponent,
  DataZoomComponent,
  VisualMapComponent,
  TimelineComponent,
  CalendarComponent,
  MarkLineComponent,
  LabelLayout,
  UniversalTransition,
  GraphicComponent,
  CanvasRenderer
]);

export default echarts;
