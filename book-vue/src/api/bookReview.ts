import axios from "axios";

import SaResult from "../common/SaToken";
import BookReview from "../domain/BookReview";
import BookReviewReply from "../domain/BookReviewReply";

export async function createReview(bookReview: BookReview): Promise<SaResult<undefined>> {
    const {data} = await axios.post("/api/book-review", bookReview)
    return data
}

export async function createReviewReplyById(id: string, bookReviewReply: BookReviewReply): Promise<SaResult<undefined>> {
    const {data} = await axios.put(`/api/book-review/${id}`, bookReviewReply)
    return data
}

export async function readByBookId(bookId: number): Promise<SaResult<BookReview[]>> {
    const {data} = await axios.get(`/api/book-review/${bookId}`)
    return data
}
