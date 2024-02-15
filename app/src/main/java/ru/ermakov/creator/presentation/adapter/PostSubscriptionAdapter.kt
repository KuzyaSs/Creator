package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemPostSubscriptionBinding
import ru.ermakov.creator.domain.model.Subscription

class PostSubscriptionAdapter :
    ListAdapter<Subscription, PostSubscriptionAdapter.PostSubscriptionViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostSubscriptionViewHolder {
        return PostSubscriptionViewHolder(
            ItemPostSubscriptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostSubscriptionViewHolder, position: Int) {
        holder.bind(subscription = getItem(position))
    }

    inner class PostSubscriptionViewHolder(private val binding: ItemPostSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subscription: Subscription) {
            binding.textViewTitle.text = subscription.title
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Subscription>() {
            override fun areItemsTheSame(oldItem: Subscription, newItem: Subscription): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Subscription, newItem: Subscription): Boolean {
                return oldItem == newItem
            }
        }
    }
}