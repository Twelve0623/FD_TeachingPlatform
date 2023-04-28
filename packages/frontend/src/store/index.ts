import { createStore } from "vuex";

export default createStore({
  state: {
    test: "test",
  },
  mutations: {
    setTest(state, data) {
      state.test = data;
    },
  },
  getters: {
    getTest(state) {
      return state.test;
    },
  },
});
