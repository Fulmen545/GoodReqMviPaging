package com.riso.goodreqmvi.ui.list

import com.riso.goodreqmvi.data.model.User

sealed class ListPartialState {
    data class UserList(val data: List<User>) : ListPartialState()
    data class Paging(val pagingData: List<User>) : ListPartialState()
    data class Profile(val id: Int?) : ListPartialState()
    object Error: ListPartialState()
    object Loading: ListPartialState()
}