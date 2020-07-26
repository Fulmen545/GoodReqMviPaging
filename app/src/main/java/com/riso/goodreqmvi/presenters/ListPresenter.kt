package com.riso.goodreqmvi.presenters

import com.riso.goodreqmvi.ui.list.ListPartialState
import com.riso.goodreqmvi.ui.list.ListView
import com.riso.goodreqmvi.ui.list.ListViewState
import io.reactivex.Observable
import com.riso.goodreqmvi.architecture.MviReducePresenter
import javax.inject.Inject

class ListPresenter @Inject constructor(
    private val apiInteractor: ApiInteractor
) : MviReducePresenter<ListView, ListViewState, ListPartialState>() {
    override fun intents(): List<Observable<out ListPartialState>> {
        val initIntent = intent { it.initIntent() }
            .flatMap {
                apiInteractor.getList(1).toObservable()
                    .map { ListPartialState.UserList(it.data) as ListPartialState }
                    .onErrorReturn { ListPartialState.Error }
                    .startWith(ListPartialState.Loading)
            }

        val clickedIntent = intent { it.itemClickIntent() }
            .flatMap { Observable.just(
                ListPartialState.Profile(it),
                    ListPartialState.Profile(null))  }

        val pagingIntent = intent { it.pagingIntent() }
            .flatMap {
                apiInteractor.getList(it).toObservable()
                    .map { ListPartialState.Paging(it.data) as ListPartialState }
                    .onErrorReturn { ListPartialState.Error }
                    .startWith(ListPartialState.Loading)
            }

        return listOf(
            initIntent,
            clickedIntent,
            pagingIntent
            )
    }



    override fun initState(): ListViewState =
        ListViewState()

    override fun reduce(previousState: ListViewState, partialState: ListPartialState): ListViewState {
        return when(partialState) {
            is ListPartialState.UserList -> previousState.copy(data = partialState.data, error = false, loading = false, paging = null)
            is ListPartialState.Profile -> previousState.copy(id = partialState.id)
            ListPartialState.Error -> previousState.copy(error = true)
            ListPartialState.Loading -> previousState.copy(loading = true, paging = null)
            is ListPartialState.Paging -> previousState.copy(paging = partialState.pagingData, error = false, loading = false, data = null)
        }
    }
}