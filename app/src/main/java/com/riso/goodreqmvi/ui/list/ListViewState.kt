package com.riso.goodreqmvi.ui.list

import com.riso.goodreqmvi.data.model.User

data class ListViewState (
    val error: Boolean = false,
    val loading: Boolean = false,
    val data: List<User>? = listOf(),
    val paging: List<User>? = listOf(),
    val id: Int? = null
)