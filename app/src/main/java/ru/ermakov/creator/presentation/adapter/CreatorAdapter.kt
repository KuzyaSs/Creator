package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemCreatorBinding
import ru.ermakov.creator.domain.model.Creator

class CreatorAdapter(
    private val onItemClickListener: (Creator) -> Unit
) : ListAdapter<Creator, CreatorAdapter.CreatorViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        return CreatorViewHolder(
            ItemCreatorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        holder.bind(creator = getItem(position))
    }

    inner class CreatorViewHolder(private val binding: ItemCreatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(creator: Creator) {
            binding.apply {
                Glide.with(root)
                    .load(creator.user.profileAvatarUrl)
                    .placeholder(R.drawable.default_profile_avatar)
                    .into(imageViewProfileAvatar)

                textViewName.text = creator.user.username
                textViewFollowerCount.text = root.resources.getQuantityString(
                    R.plurals.plural_follower_with_number,
                    creator.followerCount.toInt(),
                    creator.followerCount
                )
                root.setOnClickListener {
                    onItemClickListener(creator)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Creator>() {
            override fun areItemsTheSame(oldItem: Creator, newItem: Creator): Boolean {
                return oldItem.user.id == newItem.user.id
            }

            override fun areContentsTheSame(oldItem: Creator, newItem: Creator): Boolean {
                return oldItem == newItem
            }
        }
    }
}