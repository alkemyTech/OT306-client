package com.melvin.ongandroid.utils

sealed class ResulState<out T> {
    class Loading<out T>: ResulState<T>()
    data class Success<out T>(val data: T): ResulState<T>()
    data class Error(val exception: Exception): ResulState<Nothing>()
}