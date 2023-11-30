package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemSelectCategoryBinding
import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.presentation.util.TextLocalizer

class ChooseCategoryAdapter(
    private val textLocalizer: TextLocalizer,
    private val onItemClickListener: (Category) -> Unit
) :
    ListAdapter<Category, ChooseCategoryAdapter.CategoryViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemSelectCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(category = getItem(position))
    }

    inner class CategoryViewHolder(private val binding: ItemSelectCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                textViewName.text = textLocalizer.localizeText(category.name)
                checkBox.isChecked = category.isSelected
                root.setOnClickListener {
                    val changedCategory = category.copy(isSelected = !category.isSelected)
                    onItemClickListener(changedCategory)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}