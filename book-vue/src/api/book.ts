import axios from "axios";

import SaResult from "../common/SaToken";
import Page from "../common/Page";
import Book from "../domain/Book";

export async function create(book: Book): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/book", book)
    return data
}

export async function deleteById(id: number): Promise<SaResult<undefined>> {
    const {data} = await axios.delete(`/api/book/${id}`)
    return data
}

export async function updateById(id: number, book: Book): Promise<SaResult<undefined>> {
    const {data} = await axios.put(`/api/book/${id}`, book)
    return data
}

export async function readById(id: number): Promise<SaResult<Book>> {
    const {data} = await axios.get(`/api/book/${id}`)
    return data
}

export async function readBatchByPage(current = 1, size = 5): Promise<SaResult<Page<Book>>> {
    const {data} = await axios.get(`/api/book`, {
        params: {
            current: current,
            size: size
        }
    })
    return data
}

export async function readBatchByPageWithKeyword(keyword: string, current = 1, size = 5): Promise<SaResult<Page<Book>>> {
    const {data} = await axios.get(`/api/book`, {
        params: {
            keyword: keyword,
            current: current,
            size: size
        }
    })
    return data
}
