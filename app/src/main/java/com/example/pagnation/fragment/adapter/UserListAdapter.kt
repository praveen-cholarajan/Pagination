package com.example.pagnation.fragment.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagnation.databinding.AdapterUserListBinding
import com.example.pagnation.fragment.UserListListener
import com.example.pagnation.response.PaginationResponse
import com.squareup.picasso.Picasso
import timber.log.Timber
import javax.inject.Inject

class UserListAdapter @Inject constructor() : PagingDataAdapter<PaginationResponse,
        UserListAdapter.UserViewHolder>(CharacterComparator) {

    var listener: UserListListener? = null
    var commentsInfo: String? = null

    fun userListener(listener: UserListListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            AdapterUserListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class UserViewHolder(val binding: AdapterUserListBinding) :
        RecyclerView.ViewHolder(binding.root), AdapterItemListener {


        fun bindData(paginationResponse: PaginationResponse) {
            binding.apply {
                model = paginationResponse
                listener = this@UserViewHolder
                Picasso.get().load(paginationResponse.owner?.ImageUrl).into(ivAvatar)
                initTextWatcher()
                Timber.d("Adapter position $absoluteAdapterPosition:$commentsInfo")
            }
        }


        override fun onItemClicked() {
            Timber.d("Adapter position click${getItem(absoluteAdapterPosition)}")
            val data = getItem(absoluteAdapterPosition)
            listener?.userData(data, commentsInfo)
        }
        private fun initTextWatcher(){
            binding.commands.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    commentsInfo = s.toString()
                }
            })
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<PaginationResponse>() {
        override fun areItemsTheSame(oldItem: PaginationResponse, newItem: PaginationResponse) =
            oldItem.fullName == newItem.fullName

        override fun areContentsTheSame(oldItem: PaginationResponse, newItem: PaginationResponse) =
            oldItem == newItem
    }

    interface AdapterItemListener {
        fun onItemClicked()
    }
}