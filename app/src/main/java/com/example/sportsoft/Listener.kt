package com.example.sportsoft

interface Listener<T: IParam> {
    fun onClickEdit(param: T)
    fun onClickOpen(param: T)
}
interface IParam