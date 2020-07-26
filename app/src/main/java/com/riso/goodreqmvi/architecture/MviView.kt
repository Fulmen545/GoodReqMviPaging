package com.riso.goodreqmvi.architecture

import com.hannesdorfmann.mosby3.mvp.MvpView

interface MviView<VS> : MvpView {
    fun render(viewState: VS)
}
