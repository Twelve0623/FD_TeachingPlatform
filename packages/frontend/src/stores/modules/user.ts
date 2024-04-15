import { defineStore } from 'pinia';
import { UserState } from '@/stores/interface';
import piniaPersistConfig from '@/config/piniaPersist';
// import { loginPort } from '@/api/modules/login';
// import { ElNotification } from 'element-plus';

export const useUserStore = defineStore({
  id: 'report-user',
  state: (): UserState => ({
    // token: '11bf5139-59ba-487e-9c02-377cd332740f',
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
    // 授权
    // async userLogin() {
    //   const [, query] = window.location.href.split('?');
    //   const code = query && query.includes('=') ? query.split('=')[1] : '5c6173c752864c0887e87763d208b61d';
    //   if (this.code === code) return;
    //   this.code = code;
    //   try {
    //     const { val: token, success } = await loginPort({ code });
    //     if (success === '0') {
    //       this.token = token;
    //     } else {
    //       ElNotification({
    //         title: '验证失败',
    //         message: 'code验证码验证失败！',
    //         type: 'warning',
    //         duration: 3000
    //       });
    //     }
    //   } catch (e) {
    //     console.log('e : ', e);
    //   }
    // }
  },
  persist: piniaPersistConfig('report-user')
});
