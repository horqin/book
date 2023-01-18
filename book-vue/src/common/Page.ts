export default interface Page<T> {
    current: number // 当前页
    size: number // 每页条数
    pages: number // 总页数
    total: number // 总条数
    records: T[] // 当前页的记录
}
