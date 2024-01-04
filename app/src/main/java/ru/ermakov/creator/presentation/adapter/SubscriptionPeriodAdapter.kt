package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemSubscriptionPeriodBinding

class SubscriptionPeriodAdapter(
    private var selectedSubscriptionPeriod: Int,
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<Int, SubscriptionPeriodAdapter.SubscriptionPeriodViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubscriptionPeriodViewHolder {
        return SubscriptionPeriodViewHolder(
            ItemSubscriptionPeriodBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SubscriptionPeriodViewHolder, position: Int) {
        holder.bind(durationInMonths = getItem(position))
    }

    fun getSelectedPeriod(): Int {
        return selectedSubscriptionPeriod
    }

    inner class SubscriptionPeriodViewHolder(private val binding: ItemSubscriptionPeriodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(durationInMonths: Int) {
            binding.apply {
                if (durationInMonths == selectedSubscriptionPeriod) {
                    textViewSelectedDurationInMonths.isVisible = true
                    textViewSelectedDurationInMonths.text =
                        binding.root.resources.getQuantityString(
                            R.plurals.plural_month_with_number,
                            durationInMonths,
                            durationInMonths
                        )
                    textViewUnselectedDurationInMonths.isVisible = false
                } else {
                    textViewUnselectedDurationInMonths.isVisible = true
                    textViewUnselectedDurationInMonths.text =
                        binding.root.resources.getQuantityString(
                            R.plurals.plural_month_with_number,
                            durationInMonths,
                            durationInMonths
                        )
                    textViewSelectedDurationInMonths.isVisible = false
                }

                root.setOnClickListener {
                    selectedSubscriptionPeriod = durationInMonths
                    onItemClickListener(durationInMonths)
                    notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }
}