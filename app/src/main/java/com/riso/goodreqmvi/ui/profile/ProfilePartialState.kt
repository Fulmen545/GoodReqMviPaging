package com.riso.goodreqmvi.ui.profile

import com.riso.goodreqmvi.data.model.User

sealed class ProfilePartialState {
    data class Profile(val data: User) : ProfilePartialState()
    object Loading : ProfilePartialState()
    object Error: ProfilePartialState()
}