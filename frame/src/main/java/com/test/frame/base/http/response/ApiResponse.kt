package com.test.frame.base.http.response

class ApiResponse<T> {
    private var code: Int = 0
    private var message: String? = null
    private var data: T? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getMsg(): String {
        return message ?: ""
    }

    fun setMsg(msg: String) {
        this.message = msg
    }

    fun getDatas(): T? {
        return data
    }

    fun setDatas(datas: T) {
        this.data = datas
    }

    override fun toString(): String {
        val sb = StringBuffer()
        sb.append("code=$code msg=$message")
        if (null != data) {
            sb.append(" data:" + data.toString())
        }
        return sb.toString()
    }
}