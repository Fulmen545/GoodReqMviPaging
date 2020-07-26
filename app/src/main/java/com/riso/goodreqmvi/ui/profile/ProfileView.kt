package com.riso.goodreqmvi.ui.profile

import io.reactivex.Observable
import com.riso.goodreqmvi.architecture.MviView

interface ProfileView: MviView<ProfileViewState> {
    fun initIntent(): Observable<Int>
}