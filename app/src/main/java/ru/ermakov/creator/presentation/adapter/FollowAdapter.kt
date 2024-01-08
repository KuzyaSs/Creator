package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemFollowBinding
import ru.ermakov.creator.domain.model.Follow

class FollowAdapter(
    private val onItemClickListener: (Follow) -> Unit
) : ListAdapter<Follow, FollowAdapter.FollowViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder(
            ItemFollowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(follow = getItem(position))
    }

    inner class FollowViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(follow: Follow) {
            binding.apply {
                Glide.with(root)
                    .load(follow.creator.user.profileAvatarUrl)
                    .placeholder(R.drawable.default_profile_avatar)
                    .into(imageViewProfileAvatar)

                textViewName.text = follow.creator.user.username
                textViewFollowerCount.text = root.resources.getQuantityString(
                    R.plurals.plural_follower_with_number,
                    follow.creator.followerCount.toInt(),
                    follow.creator.followerCount
                )
                root.setOnClickListener {
                    onItemClickListener(follow)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Follow>() {
            override fun areItemsTheSame(oldItem: Follow, newItem: Follow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Follow, newItem: Follow): Boolean {
                return oldItem == newItem
            }
        }
    }
}