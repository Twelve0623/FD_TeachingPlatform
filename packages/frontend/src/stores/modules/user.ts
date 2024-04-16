import { defineStore } from 'pinia';
import { UserState } from '@/stores/interface';
import piniaPersistConfig from '@/config/piniaPersist';

export const useUserStore = defineStore({
  id: 'system-user',
  state: (): UserState => ({
    token: '',
    code: '',
    userInfo: { name: 'Geeker' }
  }),
  getters: {},
  actions: {
    setToken(token: string) {
      this.token = token;
    },
    setUserInfo(userInfo: UserState['userInfo']) {
      this.userInfo = userInfo;
    }
  },
  persist: piniaPersistConfig('report-user')
});
