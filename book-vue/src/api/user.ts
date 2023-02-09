import axios from "axios";
import {removeCookie} from "typescript-cookie";

import SaResult from "../common/SaToken";
import User from "../domain/User";

export async function register(user: User): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/user/register", user)
    return data
}

export async function login(user: User): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/user/login", user)
    return data
}

export async function isLogin(): Promise<SaResult<boolean>> {
    const {data} = await axios.post("/api/user/isLogin")
    return data
}

export function logout() {
    removeCookie("satoken")
}
