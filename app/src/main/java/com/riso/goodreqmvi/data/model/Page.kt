package com.riso.goodreqmvi.data.model

import com.squareup.moshi.Json

data class Page(
    @Json(name = "page")
    val page: Int = 1,
    @Json(name = "per_page")
    val per_page: Int = 2,
    @Json(name = "total")
    val total: Int = 12,
    @Json(name = "total_pages")
    val total_pages: Int = 2,
    @Json(name = "data")
    val data: List<User> = listOf()
)
fun Page.convert(): List<User> {
    return this.data.map {
        User(
            it.id,
            it.first_name,
            it.last_name,
            it.email,
            it.avatar
        )
    }    }