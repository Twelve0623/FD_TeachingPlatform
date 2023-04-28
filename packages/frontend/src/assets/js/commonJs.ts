import router from "../../router";

export default {
  routerGo(n: number): void {
    router.go(n);
  },
  routerPush(path: string): void {
    router.push(path);
  },
  test(): void {
    console.log(`commonJS test`);
  },
};
