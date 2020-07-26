package com.riso.goodreqmvi.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.riso.goodreqmvi.R
import com.riso.goodreqmvi.architecture.BaseApplication
import com.riso.goodreqmvi.presenters.ListPresenter
import com.riso.goodreqmvi.ui.adapter.MainAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_main.*
import com.riso.goodreqmvi.architecture.MviBaseFragment
import kotlinx.android.synthetic.main.error_layout.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : MviBaseFragment<ListView, ListViewState, ListPresenter>(),
    ListView {

    private val itemClickSubject = PublishSubject.create<Int>()
    private val pageNumberSubject = PublishSubject.create<Int>()

    private lateinit var adapter: MainAdapter
    private val refreshSubject = PublishSubject.create<Unit>()
    override fun initIntent(): Observable<Unit> =
        Observable.merge(Observable.just(Unit), refreshSubject)

    override fun itemClickIntent(): Observable<Int> = itemClickSubject
    override fun pagingIntent(): Observable<Int> = pageNumberSubject


    override fun createPresenter(): ListPresenter = BaseApplication.component.provideListPresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_main

    override fun init(view: View, savedInstanceState: Bundle?) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = MainAdapter(itemClickSubject)
        recyclerView.adapter = adapter
        var pageId = 1
        var visibleItemCount = 0
        var totalItemCount = 0
        var pastVisibleItemCount = 0
        swipe_refresh.setOnRefreshListener {
            pageId = 1
            visibleItemCount = 0
            totalItemCount = 0
            pastVisibleItemCount = 0
            refreshSubject.onNext(Unit)
        }
        recoverBtn.setOnClickListener {
            pageId = 1
            visibleItemCount = 0
            totalItemCount = 0
            pastVisibleItemCount = 0
            refreshSubject.onNext(Unit)
        }
        var currentPosition =
            (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        recyclerView.scrollToPosition(currentPosition)
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    recyclerView.layoutManager?.let {
                        visibleItemCount = it.childCount
                        totalItemCount = it.itemCount
                        pastVisibleItemCount =
                            (it as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                    if ((visibleItemCount + pastVisibleItemCount) >= totalItemCount) {
                        pageId++
                        pageNumberSubject.onNext(pageId)
                    }
                }
            }
        })
    }

    override fun render(viewState: ListViewState) {
        viewState.data?.let {
            swipe_refresh.visibility = View.VISIBLE
            swipe_refresh.isRefreshing = false
            error_image.visibility = View.GONE
            recoverBtn.visibility = View.GONE
            adapter.addData(it)

        }
        if (viewState.error) {
            swipe_refresh.visibility = View.GONE
            error_image.visibility = View.VISIBLE
            recoverBtn.visibility = View.VISIBLE
            loadingPb.visibility = View.GONE
        }
        viewState.id?.let {
            error_image.visibility = View.GONE
            recoverBtn.visibility = View.GONE
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    it
                )
            )
        }
        viewState.paging?.let {
            adapter.addToList(it)
        }
    }

}
