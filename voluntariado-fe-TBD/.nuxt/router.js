import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _7653fe3a = () => interopDefault(import('..\\pages\\get.vue' /* webpackChunkName: "pages/get" */))
const _96e3886e = () => interopDefault(import('..\\pages\\get_tasks.vue' /* webpackChunkName: "pages/get_tasks" */))
const _52c4f9be = () => interopDefault(import('..\\pages\\inspire.vue' /* webpackChunkName: "pages/inspire" */))
const _4c490c66 = () => interopDefault(import('..\\pages\\login.vue' /* webpackChunkName: "pages/login" */))
const _ee8e0ae0 = () => interopDefault(import('..\\pages\\minor_ranking.vue' /* webpackChunkName: "pages/minor_ranking" */))
const _0e198d6c = () => interopDefault(import('..\\pages\\post.vue' /* webpackChunkName: "pages/post" */))
const _6db99d73 = () => interopDefault(import('..\\pages\\put.vue' /* webpackChunkName: "pages/put" */))
const _1033d6b6 = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages/index" */))

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
    component: _7653fe3a,
    name: "get"
  }, {
    path: "/get_tasks",
    component: _96e3886e,
    name: "get_tasks"
  }, {
    path: "/inspire",
    component: _52c4f9be,
    name: "inspire"
  }, {
    path: "/login",
    component: _4c490c66,
    name: "login"
  }, {
    path: "/minor_ranking",
    component: _ee8e0ae0,
    name: "minor_ranking"
  }, {
    path: "/post",
    component: _0e198d6c,
    name: "post"
  }, {
    path: "/put",
    component: _6db99d73,
    name: "put"
  }, {
    path: "/",
    component: _1033d6b6,
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
