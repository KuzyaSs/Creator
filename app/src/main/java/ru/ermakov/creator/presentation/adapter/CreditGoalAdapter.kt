package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemCreditGoalBinding
import ru.ermakov.creator.domain.model.CreditGoal

private const val MAX_PROGRESS_BAR_VALUE = 100

class CreditGoalAdapter(
    private val isOwner: Boolean,
    private val onImageViewMoreClickListener: (CreditGoal) -> Unit,
    private val onButtonDonateClickListener: (CreditGoal) -> Unit,
) : ListAdapter<CreditGoal, CreditGoalAdapter.CreditGoalViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditGoalViewHolder {
        return CreditGoalViewHolder(
            ItemCreditGoalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CreditGoalViewHolder, position: Int) {
        holder.bind(creditGoal = getItem(position))
    }

    inner class CreditGoalViewHolder(private val binding: ItemCreditGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(creditGoal: CreditGoal) {
            binding.apply {
                imageViewCheck.isVisible = creditGoal.balance >= creditGoal.targetBalance
                textViewAmount.text = root.resources.getString(
                    R.string.target_balance_of_balance,
                    creditGoal.balance,
                    creditGoal.targetBalance
                )
                binding.imageViewMore.apply {
                    visibility = if (isOwner) View.VISIBLE else View.INVISIBLE
                    setOnClickListener { onImageViewMoreClickListener(creditGoal) }
                }
                progressBarDonate.apply {
                    max = MAX_PROGRESS_BAR_VALUE
                    progress =
                        (creditGoal.balance.toDouble() / creditGoal.targetBalance * MAX_PROGRESS_BAR_VALUE).toInt()
                }
                textViewDescription.text = creditGoal.description
                buttonDonate.apply {
                    isVisible = !isOwner
                    setOnClickListener { onButtonDonateClickListener }
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CreditGoal>() {
            override fun areItemsTheSame(oldItem: CreditGoal, newItem: CreditGoal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CreditGoal, newItem: CreditGoal): Boolean {
                return oldItem == newItem
            }
        }
    }
}