package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemPostCommentBinding
import ru.ermakov.creator.domain.model.PostCommentItem

class PostCommentAdapter(
    private val userId: String,
    private val onProfileAvatarClickListener: (PostCommentItem) -> Unit,
    private val onMoreClickListener: (PostCommentItem) -> Unit,
    private val onLikeClickListener: (PostCommentItem) -> Unit,
    private val onDislikeClickListener: (PostCommentItem) -> Unit,
    private val onReplyClickListener: (PostCommentItem) -> Unit
) : ListAdapter<PostCommentItem, PostCommentAdapter.CreatorViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        return CreatorViewHolder(
            ItemPostCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        holder.bind(postCommentItem = getItem(position))
    }

    inner class CreatorViewHolder(private val binding: ItemPostCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postCommentItem: PostCommentItem) {
            binding.apply {
                Glide.with(root)
                    .load(postCommentItem.user.profileAvatarUrl)
                    .placeholder(R.drawable.default_profile_avatar)
                    .into(imageViewCreatorProfileAvatar)

                textViewCreatorName.text = postCommentItem.user.username
                textViewPublicationDate.text = postCommentItem.publicationDate
                textViewLikeCount.text = postCommentItem.likeCount.toString()
                textViewContent.text = postCommentItem.content
                if (postCommentItem.isLiked) {
                    imageViewLike.setImageResource(R.drawable.ic_favorite_filled_accent)
                } else {
                    imageViewLike.setImageResource(R.drawable.ic_favorite)
                }
                textViewIsEdited.isVisible = postCommentItem.isEdited

                imageViewCreatorProfileAvatar.setOnClickListener {
                    onProfileAvatarClickListener(postCommentItem)
                }
                imageViewMore.isVisible = userId == postCommentItem.user.id
                imageViewMore.setOnClickListener {
                    onMoreClickListener(postCommentItem)
                }
                imageViewLike.setOnClickListener {
                    val changedPostItem = postCommentItem.copy(isLiked = !postCommentItem.isLiked)
                    if (changedPostItem.isLiked) {
                        onLikeClickListener(changedPostItem)
                    } else {
                        onDislikeClickListener(changedPostItem)
                    }
                }
                imageViewReply.setOnClickListener {
                    onReplyClickListener(postCommentItem)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PostCommentItem>() {
            override fun areItemsTheSame(oldItem: PostCommentItem, newItem: PostCommentItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostCommentItem, newItem: PostCommentItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}