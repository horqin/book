import axios from "axios";

import SaResult from "../common/SaToken";
import User from "../domain/User";

export async function login(user: User): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/user/login", user)
    return data
}

export async function logout(): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/user/logout")
    return data
}

export async function register(user: User): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/user/register", user)
    return data
}
