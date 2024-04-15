import { useUserStore } from '@/stores/modules/user';
import { CURRENT_OPTION } from '@/config';

export const reLogin = () => {
  const userStore = useUserStore();
  userStore.setToken('');
  window.location.replace(CURRENT_OPTION.authUrl);
};
