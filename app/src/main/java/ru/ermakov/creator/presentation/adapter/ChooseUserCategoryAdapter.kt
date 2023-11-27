package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemSelectUserCategoryBinding
import ru.ermakov.creator.domain.model.UserCategory

class ChooseUserCategoryAdapter(private val onItemClickListener: (UserCategory) -> Unit) :
    ListAdapter<UserCategory, ChooseUserCategoryAdapter.CategoryViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemSelectUserCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(userCategory = getItem(position))
    }

    inner class CategoryViewHolder(private val binding: ItemSelectUserCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userCategory: UserCategory) {
            binding.apply {
                textViewName.text = userCategory.name
                checkBox.isChecked = userCategory.isSelected
                root.setOnClickListener {
                    val changedCategory = userCategory.copy(isSelected = !userCategory.isSelected)
                    onItemClickListener(changedCategory)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<UserCategory>() {
            override fun areItemsTheSame(oldItem: UserCategory, newItem: UserCategory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserCategory, newItem: UserCategory): Boolean {
                return oldItem == newItem
            }
        }
    }
}