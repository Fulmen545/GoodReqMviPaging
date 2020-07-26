package com.riso.goodreqmvi.ui.list

import io.reactivex.Observable
import com.riso.goodreqmvi.architecture.MviView

interface ListView : MviView<ListViewState> {
    fun initIntent(): Observable<Unit>
    fun itemClickIntent(): Observable<Int>
    fun pagingIntent(): Observable<Int>
}