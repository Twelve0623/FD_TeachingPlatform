import { createApp } from 'vue';
import App from './App.vue';
// reset style customer
import '@/styles/reset.scss';
// css common style customer
import '@/styles/common.scss';
// css public style
import '@/styles/public.scss';
// iconfont css
import '@/assets/iconfont/iconfont.scss';
// font css
import '@/assets/fonts/font.scss';
// element css
import 'element-plus/dist/index.css';
// element plus
import ElementPlus from 'element-plus';
// element dark css
import 'element-plus/theme-chalk/dark/css-vars.css';
// element display css
import 'element-plus/theme-chalk/display.css';
// custom element dark css
import '@/styles/theme/element-dark.scss';
// custom element css
import '@/styles/element.scss';
// svg icons
import 'virtual:svg-icons-register';
// element icons
import * as Icons from '@element-plus/icons-vue';
// custom directives
import directives from '@/directives/index';
// vue Router
import router from '@/routers';
// vue i18n
import I18n from '@/languages/index';
// pinia store
import pinia from '@/stores';

// vconsole 控制台 非生产环境下调用vConsole
import VConsole from 'vconsole';
if (import.meta.env.VITE_USER_NODE_ENV !== 'production') {
  new VConsole();
}

// errorHandler
import errorHandler from '@/utils/errorHandler';

const app = createApp(App);

app.config.errorHandler = errorHandler;

// register the element Icons component
Object.keys(Icons).forEach(key => {
  app.component(key, Icons[key as keyof typeof Icons]);
});

app.use(ElementPlus).use(directives).use(pinia).use(router).use(I18n).mount('#app');
