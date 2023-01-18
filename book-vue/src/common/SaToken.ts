export default interface SaResult<T> {
    code: number // 状态码
    msg: string // 提示消息
    data: T // 数据
}
