package com.riso.goodreqmvi.architecture

interface BaseNavigator<T> {
    fun navigateFrom(screen: T)
}