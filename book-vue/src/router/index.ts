import {createRouter, createWebHashHistory} from "vue-router";

export default createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            name: "user",
            path: "/user",
            component: () => import("../components/UserPage.vue")
        },
        {
            name: "book",
            path: "/book",
            component: () => import("../components/BookPage.vue")
        },
        {
            name: "home",
            path: "/",
            redirect: "/user"
        }
    ]
})
