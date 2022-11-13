import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _632a810a = () => interopDefault(import('..\\pages\\get.vue' /* webpackChunkName: "pages/get" */))
const _0e0b6299 = () => interopDefault(import('..\\pages\\get_tasks.vue' /* webpackChunkName: "pages/get_tasks" */))
const _17fd948e = () => interopDefault(import('..\\pages\\inspire.vue' /* webpackChunkName: "pages/inspire" */))
const _29c6eec6 = () => interopDefault(import('..\\pages\\login.vue' /* webpackChunkName: "pages/login" */))
const _87d936c8 = () => interopDefault(import('..\\pages\\post.vue' /* webpackChunkName: "pages/post" */))
const _5a902043 = () => interopDefault(import('..\\pages\\put.vue' /* webpackChunkName: "pages/put" */))
const _2174e586 = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages/index" */))

const emptyFn = () => {}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: '/',
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
    path: "/get",
    component: _632a810a,
    name: "get"
  }, {
    path: "/get_tasks",
    component: _0e0b6299,
    name: "get_tasks"
  }, {
    path: "/inspire",
    component: _17fd948e,
    name: "inspire"
  }, {
    path: "/login",
    component: _29c6eec6,
    name: "login"
  }, {
    path: "/post",
    component: _87d936c8,
    name: "post"
  }, {
    path: "/put",
    component: _5a902043,
    name: "put"
  }, {
    path: "/",
    component: _2174e586,
    name: "index"
  }],

  fallback: false
}

export function createRouter (ssrContext, config) {
  const base = (config._app && config._app.basePath) || routerOptions.base
  const router = new Router({ ...routerOptions, base  })

  // TODO: remove in Nuxt 3
  const originalPush = router.push
  router.push = function push (location, onComplete = emptyFn, onAbort) {
    return originalPush.call(this, location, onComplete, onAbort)
  }

  const resolve = router.resolve.bind(router)
  router.resolve = (to, current, append) => {
    if (typeof to === 'string') {
      to = normalizeURL(to)
    }
    return resolve(to, current, append)
  }

  return router
}
