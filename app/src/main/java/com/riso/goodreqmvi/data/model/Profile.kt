package com.riso.goodreqmvi.data.model

import com.squareup.moshi.Json

data class Profile(
    @Json(name = "data")
    val `data`: User = User()
)

fun Profile.convert(): User {
    return User(
        data.id,
        data.email,
        data.first_name,
        data.last_name,
        data.avatar
    )
}