export type AsideThemeType = 'light' | 'inverted' | 'dark';

export const asideTheme: Record<AsideThemeType, { [key: string]: string }> = {
  light: {
    '--el-logo-text-color': '#303133',
    '--el-logo-border-color': 'var(--el-border-color-light)',
    '--el-menu-bg-color': '#ffffff',
    '--el-menu-hover-bg-color': '#cccccc',
    '--el-menu-active-bg-color': 'var(--el-color-primary-light-9)',
    '--el-menu-text-color': '#333333',
    '--el-menu-active-color': 'var(--el-color-primary)',
    '--el-menu-hover-text-color': '#333333',
    '--el-menu-index-color': '#0F0541',
    '--el-menu-horizontal-sub-item-height': '50px'
  },
  inverted: {
    '--el-logo-text-color': '#dadada',
    '--el-logo-border-color': '#414243',
    '--el-menu-bg-color': '#191a20',
    '--el-menu-hover-bg-color': '#000000',
    '--el-menu-active-bg-color': '#000000',
    '--el-menu-text-color': '#bdbdc0',
    '--el-menu-active-color': 'var(--el-color-primary)',
    '--el-menu-hover-text-color': '#ffffff',
    '--el-menu-index-color': 'rgba(172,156,255,0.5)',
    '--el-menu-horizontal-sub-item-height': '50px'
  },
  dark: {
    '--el-logo-text-color': '#dadada',
    '--el-logo-border-color': 'var(--el-border-color-light)',
    '--el-menu-bg-color': '#141414',
    '--el-menu-hover-bg-color': '#000000',
    '--el-menu-active-bg-color': '#000000',
    '--el-menu-text-color': '#bdbdc0',
    '--el-menu-active-color': 'var(--el-color-primary)',
    '--el-menu-hover-text-color': '#ffffff',
    '--el-menu-index-color': 'rgba(172,156,255,0.5)',
    '--el-menu-horizontal-sub-item-height': '50px'
  }
};
