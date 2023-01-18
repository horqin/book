import axios from "axios";

import SaResult from "../common/SaToken";
import Page from "../common/Page";
import Book from "../domain/Book";

export async function create(book: Book): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/book/create", book)
    return data
}

export async function drop(id: number): Promise<SaResult<undefined>> {
    const {data} = await axios.post(`/api/book/delete/${id}`)
    return data
}

export async function update(id: number, book: Book): Promise<SaResult<undefined>> {
    const {data} = await axios.post(`/api/book/update/${id}`, book)
    return data
}

export async function search(id: number): Promise<SaResult<Book>> {
    const {data} = await axios.get(`/api/book/search/${id}`)
    return data
}

export async function searchWithPage(current = 1, size = 5): Promise<SaResult<Page<Book>>> {
    const {data} = await axios.get(`/api/book/search`, {
        params: {
            current: current,
            size: size
        }
    })
    return data
}

export async function searchWithSimplePage(keyword: string, current = 1, size = 5): Promise<SaResult<Page<Book>>> {
    const {data} = await axios.get(`/api/book`, {
        params: {
            keyword: keyword,
            current: current,
            size: size
        }
    })
    return data
}
