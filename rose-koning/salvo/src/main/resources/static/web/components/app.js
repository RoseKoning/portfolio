const router = new VueRouter({
    routes: [
        { path: '/', redirect: '/home', component: Home },
        { path: "/home", component: Home },
        { path: "/register", component: Register },
        { path: "/login", component: Login },
        { path: "/games", component: Games },
        { path: "/game/:gamePlayerId", component: Game },
    ],
});

const App = {
    el: "#app",
    router,
}