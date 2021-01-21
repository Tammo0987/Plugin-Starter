import Vue from 'vue';
import VueRouter from 'vue-router';

import GenerateView from '@/view/GenerateView.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: GenerateView,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
