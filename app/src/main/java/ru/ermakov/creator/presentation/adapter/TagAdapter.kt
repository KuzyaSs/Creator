package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemTagBinding
import ru.ermakov.creator.domain.model.Tag

class TagAdapter(
    private val onMoreClickListener: (Tag) -> Unit
) : ListAdapter<Tag, TagAdapter.TagViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            ItemTagBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tag = getItem(position))
    }

    inner class TagViewHolder(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.textViewName.text = tag.name
            binding.imageViewMore.setOnClickListener {
                onMoreClickListener(tag)
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