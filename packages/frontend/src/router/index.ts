import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/home",
  },
  {
    path: "/home",
    component: () => import("@/views/index.vue"),
    redirect: "/home/dataStructure",
    children: [
      {
        path: "/home/dataStructure",
        component: () => import("@/views/subIndex.vue"),
        redirect: "/home/dataStructure/stack_code",
        children: [
          {
            path: "/home/dataStructure/stack_code",
            component: () =>
              import("@/components/dataStructure/code/index.vue"),
          },
          {
            path: "/home/dataStructure/stack_chart",
            component: () =>
              import("@/components/dataStructure/chart/index.vue"),
          },
        ],
      },
    ],
  },
];

const router = createRouter({
  routes,
  history: createWebHashHistory(),
});

export default router;
