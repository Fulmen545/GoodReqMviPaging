package com.riso.goodreqmvi.presenters

import com.riso.goodreqmvi.data.api.ApiService
import com.riso.goodreqmvi.data.model.Page
import com.riso.goodreqmvi.data.model.Profile
import com.riso.goodreqmvi.data.model.User
import io.reactivex.Single
import javax.inject.Inject

interface ApiInteractor{
    fun getList(page: Int): Single<Page>
    fun getProfile(id: Int): Single<Profile>
}

class ApiInteractorImpl @Inject constructor(
    private val apiService: ApiService
): ApiInteractor {
    override fun getList(page: Int): Single<Page> {
        return apiService.getUsers(page, 5)
    }

    override fun getProfile(id: Int): Single<Profile> {
        return apiService.getDetail(id)
    }


}