package com.riso.goodreqmvi.ui.profile

import com.riso.goodreqmvi.data.model.User

data class ProfileViewState (
    val error: Boolean = false,
    val user: User? = null,
    val loading: Boolean = false
    )