package com.example.base.models

data class ApiResponseWrapper<T>(
    var errorMessage:String?,
    var data:T?
){
    constructor() : this(null,null) {

    }
}