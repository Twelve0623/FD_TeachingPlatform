/**
 * v-auth
 * 按钮权限指令
 */
import type { Directive, DirectiveBinding } from 'vue';

interface ElType extends HTMLElement {
  __handleClick__: () => any;
  __disabled__: boolean;
}
interface ValueType {
  code: string;
  fn: (...arg: any[]) => void;
}

const auth: Directive = {
  mounted(el: ElType, binding: DirectiveBinding<ValueType>) {
    const { value } = binding;

    el.className.includes('is-disabled') && el.classList.remove('is-disabled');

    if (typeof value.fn !== 'function') {
      throw 'callback must be a function';
    }
    let timer: NodeJS.Timeout | null = null;
    el.__handleClick__ = function () {
      if (timer) {
        clearInterval(timer);
      }
      timer = setTimeout(() => {
        value.fn();
      }, 500);
    };
    el.addEventListener('click', el.__handleClick__);
  },
  beforeUnmount(el: ElType) {
    !el.__disabled__ && el.removeEventListener('click', el.__handleClick__);
  }
};

export default auth;
