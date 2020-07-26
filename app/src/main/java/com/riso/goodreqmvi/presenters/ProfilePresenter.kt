package com.riso.goodreqmvi.presenters

import com.riso.goodreqmvi.data.model.convert
import com.riso.goodreqmvi.ui.profile.ProfilePartialState
import com.riso.goodreqmvi.ui.profile.ProfileView
import com.riso.goodreqmvi.ui.profile.ProfileViewState
import io.reactivex.Observable
import com.riso.goodreqmvi.architecture.MviReducePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val apiInteractor: ApiInteractor
) : MviReducePresenter<ProfileView, ProfileViewState, ProfilePartialState>() {
    override fun intents(): List<Observable<out ProfilePartialState>> {

        val initIntent = intent { it.initIntent() }
            .flatMap { id ->
                apiInteractor.getProfile(id).toObservable()
                    .map {
                        ProfilePartialState.Profile(
                            it.convert()
                        ) as ProfilePartialState
                    }
                    .onErrorReturn { ProfilePartialState.Error }
                    .startWith(ProfilePartialState.Loading)
            }

        return listOf(initIntent)
    }

    override fun initState(): ProfileViewState = ProfileViewState()

    override fun reduce(
        previousState: ProfileViewState,
        partialState: ProfilePartialState
    ): ProfileViewState {
        return when(partialState){
            is ProfilePartialState.Profile -> previousState.copy(user = partialState.data, loading = false)
            ProfilePartialState.Loading -> previousState.copy(loading = true)
            ProfilePartialState.Error -> previousState.copy(error = true)
        }
    }

}
