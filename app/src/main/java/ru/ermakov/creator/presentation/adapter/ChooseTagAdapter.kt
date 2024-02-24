package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemSelectTagBinding
import ru.ermakov.creator.domain.model.Tag

class ChooseTagAdapter(
    private val selectedTagIds: MutableList<Long>,
    private val onItemClickListener: () -> Unit
) : ListAdapter<Tag, ChooseTagAdapter.ChooseTagViewHolder>(DiffCallback) {
    fun getSelectedTagIds(): List<Long> {
        return selectedTagIds.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseTagViewHolder {
        return ChooseTagViewHolder(
            ItemSelectTagBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChooseTagViewHolder, position: Int) {
        holder.bind(tag = getItem(position))
    }

    inner class ChooseTagViewHolder(private val binding: ItemSelectTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.apply {
                textViewName.text = tag.name
                checkBox.isChecked = selectedTagIds.contains(tag.id)
                root.setOnClickListener {
                    checkBox.isChecked = !checkBox.isChecked
                    if (selectedTagIds.contains(tag.id)) {
                        selectedTagIds.remove(tag.id)
                    } else {
                        selectedTagIds.add(tag.id)
                    }
                    onItemClickListener()
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
                return oldItem == newItem
            }
        }
    }
}