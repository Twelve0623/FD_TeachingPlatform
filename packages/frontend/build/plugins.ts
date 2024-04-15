import { resolve } from 'path';
import { PluginOption } from 'vite';
import { VitePWA } from 'vite-plugin-pwa';
import { visualizer } from 'rollup-plugin-visualizer';
import { createHtmlPlugin } from 'vite-plugin-html';
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons';
import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import eslintPlugin from 'vite-plugin-eslint';
import viteCompression from 'vite-plugin-compression';
import vueSetupExtend from 'unplugin-vue-setup-extend-plus/vite';
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import Icons from 'unplugin-icons/vite';
import IconsResolver from 'unplugin-icons/resolver';
// import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';
import Inspect from 'vite-plugin-inspect';
// import VueDevTools from 'vite-plugin-vue-devtools';
import { ViteEnv } from '@/typings/global';

/**
 * 创建 vite 插件
 * @param viteEnv
 * @param pathSrc
 */
export const createVitePlugins = (viteEnv: ViteEnv, pathSrc: string): (PluginOption | PluginOption[])[] => {
  const { VITE_GLOB_APP_TITLE, VITE_REPORT, VITE_PWA } = viteEnv;
  return [
    vue(),
    // VueDevTools(),
    // vue 可以使用 jsx/tsx 语法
    vueJsx(),
    // esLint 报错信息显示在浏览器界面上
    eslintPlugin(),
    // name 可以写在 script 标签上
    vueSetupExtend({}),
    // 创建打包压缩配置
    createCompression(viteEnv),
    // 注入变量到 html 文件
    createHtmlPlugin({
      inject: {
        data: { title: VITE_GLOB_APP_TITLE }
      }
    }),
    // 使用 svg 图标
    createSvgIconsPlugin({
      iconDirs: [resolve(process.cwd(), 'src/assets/icons')],
      symbolId: 'icon-[dir]-[name]'
    }),
    // vitePWA
    VITE_PWA && createVitePwa(viteEnv),
    // 是否生成包预览，分析依赖包大小做优化处理
    VITE_REPORT && (visualizer({ filename: 'stats.html', gzipSize: true, brotliSize: true }) as PluginOption),
    // 自动导入
    AutoImport({
      // Auto import functions from Vue, e.g. ref, reactive, toRef...
      // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
      imports: ['vue', '@vueuse/core', 'vue-router', 'pinia'],
      resolvers: [
        // Auto import functions from Element Plus, e.g. ElMessage, ElMessageBox... (with style)
        // 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
        // ElementPlusResolver(),

        // Auto import icon components
        // 自动导入图标组件
        IconsResolver({
          prefix: 'Icon'
        })
      ],
      dts: resolve(pathSrc, 'typings/auto-imports.d.ts')
    }),

    Components({
      resolvers: [
        // Auto register Element Plus components
        // 自动导入 Element Plus 组件
        // ElementPlusResolver()

        // Auto register icon components
        // 自动注册图标组件
        IconsResolver({
          prefix: 'icon', // <-- By default, the prefix is set to i while you can customize via config
          enabledCollections: ['ep', 'formkit', 'healthicons']
        })
      ],
      dts: resolve(pathSrc, 'typings/components.d.ts')
    }),
    Icons({
      autoInstall: true
    }),
    Inspect()
  ];
};

/**
 * @description 根据 compress 配置，生成不同的压缩规则
 * @param viteEnv
 */
const createCompression = (viteEnv: ViteEnv): PluginOption | PluginOption[] => {
  const { VITE_BUILD_COMPRESS = 'none', VITE_BUILD_COMPRESS_DELETE_ORIGIN_FILE } = viteEnv;
  const compressList = VITE_BUILD_COMPRESS.split(',');
  const plugins: PluginOption[] = [];

  if (compressList.includes('gzip')) {
    plugins.push(
      viteCompression({
        ext: '.gz',
        algorithm: 'gzip',
        deleteOriginFile: VITE_BUILD_COMPRESS_DELETE_ORIGIN_FILE
      })
    );
  }
  if (compressList.includes('brotli')) {
    plugins.push(
      viteCompression({
        ext: '.br',
        algorithm: 'brotliCompress',
        deleteOriginFile: VITE_BUILD_COMPRESS_DELETE_ORIGIN_FILE
      })
    );
  }
  return plugins;
};

/**
 * @description VitePwa
 * @param viteEnv
 */
const createVitePwa = (viteEnv: ViteEnv): PluginOption | PluginOption[] => {
  const { VITE_GLOB_APP_TITLE } = viteEnv;
  return VitePWA({
    registerType: 'autoUpdate',
    manifest: {
      name: VITE_GLOB_APP_TITLE,
      short_name: VITE_GLOB_APP_TITLE,
      theme_color: '#ffffff',
      icons: [
        {
          src: '/favicon.ico',
          sizes: '192x192',
          type: 'image/png'
        },
        {
          src: '/favicon.ico',
          sizes: '512x512',
          type: 'image/png'
        },
        {
          src: '/favicon.ico',
          sizes: '512x512',
          type: 'image/png',
          purpose: 'any maskable'
        }
      ]
    }
  });
};
