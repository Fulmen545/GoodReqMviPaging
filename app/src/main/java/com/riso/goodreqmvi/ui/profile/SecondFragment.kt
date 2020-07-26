package com.riso.goodreqmvi.ui.profile

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.riso.goodreqmvi.R
import com.riso.goodreqmvi.architecture.BaseApplication
import com.riso.goodreqmvi.presenters.ProfilePresenter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_second.*
import com.riso.goodreqmvi.architecture.MviBaseFragment
import kotlinx.android.synthetic.main.error_layout.*

class SecondFragment : MviBaseFragment<ProfileView, ProfileViewState, ProfilePresenter>(),
    ProfileView {
    private val id: SecondFragmentArgs by navArgs()
    private val refreshSubject = PublishSubject.create<Unit>()
    override fun initIntent(): Observable<Int> =
        Observable.merge(Observable.just(Unit), refreshSubject).map { id.id }


    override fun createPresenter(): ProfilePresenter =
        BaseApplication.component.provideProfilePresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_second

    override fun init(view: View, savedInstanceState: Bundle?) {
        refresh_profile.setOnRefreshListener {
            refreshSubject.onNext(Unit)
        }
        recoverBtn.setOnClickListener {
            refreshSubject.onNext(Unit)
        }

    }


    override fun render(viewState: ProfileViewState) {
        if (viewState.loading) {
            loadingPb.visibility = View.VISIBLE
        } else {
            loadingPb.visibility = View.GONE
        }
        if (viewState.error) {
            error_image.visibility = View.VISIBLE
            recoverBtn.visibility = View.VISIBLE
            loadingPb.visibility = View.GONE
            tvFirstNameData.visibility = View.GONE
            tvLastNameData.visibility = View.GONE
            tvEmailData.visibility = View.GONE
            imageview_account_profile.visibility = View.GONE
            refresh_profile.isRefreshing = false
        }
        viewState.user?.let {
            error_image.visibility = View.GONE
            recoverBtn.visibility = View.GONE
            tvFirstNameData.visibility = View.VISIBLE
            tvLastNameData.visibility = View.VISIBLE
            tvEmailData.visibility = View.VISIBLE
            imageview_account_profile.visibility = View.VISIBLE
            refresh_profile.isRefreshing = false
            tvFirstNameData.text = it.first_name
            tvLastNameData.text = it.last_name
            tvEmailData.text = it.email
            Glide.with(imageview_account_profile.context)
                .load(it.avatar)
                .into(imageview_account_profile)


        }
    }
}
