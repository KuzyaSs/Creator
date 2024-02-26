package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemSelectSubscriptionBinding
import ru.ermakov.creator.domain.model.Subscription

class ChooseSubscriptionAdapter(
    private val selectedSubscriptionIds: MutableList<Long>,
    private val onItemClickListener: () -> Unit
) : ListAdapter<Subscription, ChooseSubscriptionAdapter.ChooseSubscriptionViewHolder>(DiffCallback) {
    fun getSelectedSubscriptionIds(): List<Long> {
        return selectedSubscriptionIds.toList()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChooseSubscriptionViewHolder {
        return ChooseSubscriptionViewHolder(
            ItemSelectSubscriptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChooseSubscriptionViewHolder, position: Int) {
        holder.bind(subscription = getItem(position))
    }

    inner class ChooseSubscriptionViewHolder(private val binding: ItemSelectSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subscription: Subscription) {
            binding.apply {
                textViewTitle.text = subscription.title
                checkBox.isChecked = selectedSubscriptionIds.contains(subscription.id)
                root.setOnClickListener {
                    checkBox.isChecked = !checkBox.isChecked
                    if (selectedSubscriptionIds.contains(subscription.id)) {
                        selectedSubscriptionIds.remove(subscription.id)
                    } else {
                        selectedSubscriptionIds.add(subscription.id)
                    }
                    onItemClickListener()
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