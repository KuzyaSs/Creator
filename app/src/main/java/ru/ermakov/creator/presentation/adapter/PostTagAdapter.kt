package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.databinding.ItemPostTagBinding
import ru.ermakov.creator.domain.model.Tag

private const val INVALID_TAG_ID = -1L

class PostTagAdapter(
    private val onChangeClickListener: () -> Unit
) : ListAdapter<Tag, PostTagAdapter.PostTagViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostTagViewHolder {
        return PostTagViewHolder(
            ItemPostTagBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostTagViewHolder, position: Int) {
        holder.bind(tag = getItem(position))
    }

    inner class PostTagViewHolder(private val binding: ItemPostTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            if (tag.id != INVALID_TAG_ID) {
                binding.textViewName.text = tag.name
            } else {
                binding.textViewName.isVisible = false
                binding.textViewChange.isVisible = true
            }

            binding.textViewChange.setOnClickListener {
                onChangeClickListener()
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