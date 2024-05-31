package com.example.sportsoft

interface Listener<T: IParam> {
    fun onClickEdit(param: T)
    fun onClickStart(param: T)
}
interface IParam
