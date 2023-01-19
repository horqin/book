export default interface Page<T> {
    current: number // 当前页码
    size: number // 每页数量
    pages: number // 总页数
    total: number // 总条数
    records: Array<T> // 分页数据
}
