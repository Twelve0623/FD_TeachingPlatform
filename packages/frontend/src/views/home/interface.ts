import { Menu } from '@/typings/global';

export type panelItem = {
  id: string;
  count: number;
  percent: number;
};

export interface HomeMenuOptions extends Menu.MenuOptions {
  baseLayer?: Menu.MenuOptions[];
}
