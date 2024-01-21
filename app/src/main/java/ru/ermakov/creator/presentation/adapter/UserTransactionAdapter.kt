package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemUserTransactionBinding
import ru.ermakov.creator.domain.model.UserTransactionItem
import ru.ermakov.creator.presentation.util.TextLocalizer

class UserTransactionAdapter(
    private val textLocalizer: TextLocalizer,
    private val onItemClickListener: (UserTransactionItem) -> Unit
) : ListAdapter<UserTransactionItem, UserTransactionAdapter.UserTransactionViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTransactionViewHolder {
        return UserTransactionViewHolder(
            ItemUserTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserTransactionViewHolder, position: Int) {
        holder.bind(userTransactionItem = getItem(position))
    }

    inner class UserTransactionViewHolder(
        private val binding: ItemUserTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userTransactionItem: UserTransactionItem) {
            binding.apply {
                Glide.with(root)
                    .load(userTransactionItem.user.profileAvatarUrl)
                    .placeholder(R.drawable.default_profile_avatar)
                    .into(imageViewAvatar)
                textViewTitle.text = userTransactionItem.title
                textViewTransactionTypeWithDate.text = root.resources.getString(
                    R.string.transaction_type_with_date,
                    textLocalizer.localizeText(text = userTransactionItem.transactionType),
                    userTransactionItem.date
                )
                textViewAmount.text = userTransactionItem.amount.toString()
                textViewMessage.apply {
                    text = userTransactionItem.message
                    isVisible = userTransactionItem.message.isNotBlank()
                }
                imageViewAvatar.setOnClickListener {
                    onItemClickListener(userTransactionItem)
                }
                textViewAmount.requestLayout()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<UserTransactionItem>() {
            override fun areItemsTheSame(
                oldItem: UserTransactionItem,
                newItem: UserTransactionItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserTransactionItem,
                newItem: UserTransactionItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}