import { defineStore } from 'pinia';
import { GlobalState } from '@/stores/interface';
import piniaPersistConfig from '@/config/piniaPersist';
import { ObjToKeyValArray } from '@/typings/global';

export const useGlobalStore = defineStore({
  id: 'system-global',
  // 修改默认值之后，需清除 localStorage 数据
  state: (): GlobalState => ({
    // 当前系统语言
    language: null
  }),
  getters: {},
  actions: {
    // Set GlobalState
    setGlobalState(...args: ObjToKeyValArray<GlobalState>) {
      this.$patch({ [args[0]]: args[1] });
    }
  },
  persist: piniaPersistConfig('report-global')
});
