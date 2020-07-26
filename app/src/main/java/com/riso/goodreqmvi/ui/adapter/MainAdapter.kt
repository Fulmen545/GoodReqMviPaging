package com.riso.goodreqmvi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riso.goodreqmvi.R
import com.riso.goodreqmvi.data.model.User
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainAdapter (

    private val onClickSubject: Subject<Int>

): RecyclerView.Adapter<MainAdapter.DataViewHolder>() {
    private var users: List<User> = listOf(User(2,"fads", "dfas", "fads", "fdas"))


    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        fun bind(user: User){
            itemView.textViewFirstName.text = user.first_name
            itemView.textViewLasttName.text = user.last_name
            itemView.textViewUserEmail.text = user.email
            Glide.with(itemView.profileImage.context)
                .load(user.avatar)
                .into(itemView.profileImage)
        }

        override fun onClick(p0: View?) {
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )


    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        users[position].let { user ->
            holder.bind(user)
            holder.itemView.setOnClickListener { onClickSubject.onNext(user.id) }
        }
    }

    fun addData(list: List<User>) {
        this.users = list
        notifyDataSetChanged()
    }

    fun addToList(list: List<User>){
        var listTest: ArrayList<User> = ArrayList()
        listTest.addAll(users)
        listTest.addAll(list)

        this.users = listTest
        notifyDataSetChanged()
    }



}