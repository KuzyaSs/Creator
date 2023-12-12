package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemSubscriptionBinding
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.UserSubscription

class SubscriptionAdapter(
    private val userSubscriptions: List<UserSubscription>,
    private val isOwner: Boolean,
    private val onSubscribeButtonClickListener: (Subscription) -> Unit,
    private val onUnsubscribeButtonClickListener: (Subscription) -> Unit
) : ListAdapter<Subscription, SubscriptionAdapter.SubscriptionViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        return SubscriptionViewHolder(
            ItemSubscriptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        holder.bind(subscription = getItem(position))
    }

    inner class SubscriptionViewHolder(private val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subscription: Subscription) {
            val userSubscription = userSubscriptions.firstOrNull { userSubscription ->
                userSubscription.subscription.id == subscription.id
            }
            binding.textViewTitle.text = subscription.title
            binding.textViewDescription.text = subscription.description
            binding.buttonSubscribe.apply {
                isVisible = !isOwner && userSubscription == null
                text = binding.root.resources.getString(R.string.subscribe_for, subscription.price)
                setOnClickListener { onSubscribeButtonClickListener(subscription) }
            }
            if (userSubscription != null) {
                binding.buttonSubscribed.apply {
                    isVisible = !isOwner
                    text = binding.root.resources.getString(
                        R.string.subscribed_until,
                        userSubscription.endDate
                    )
                }
                binding.buttonUnsubscribe.apply {
                    isVisible = !isOwner
                    setOnClickListener { onUnsubscribeButtonClickListener(subscription) }
                }
            }
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