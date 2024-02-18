package ru.ermakov.creator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ItemPostBinding
import ru.ermakov.creator.domain.model.PostItem

class PostAdapter(
    private val userId: String,
    private val onItemClickListener: (PostItem) -> Unit,
    private val onProfileAvatarClickListener: (PostItem) -> Unit,
    private val onMoreClickListener: (PostItem) -> Unit,
    private val onSubscribeClickListener: (PostItem) -> Unit,
    private val onLikeClickListener: (PostItem) -> Unit,
    private val onDislikeClickListener: (PostItem) -> Unit,
    private val onCommentClickListener: (PostItem) -> Unit,
) : ListAdapter<PostItem, PostAdapter.PostViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postItem = getItem(position))
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var postTagAdapter: PostTagAdapter? = null
        private var postSubscriptionAdapter: PostSubscriptionAdapter? = null

        fun bind(postItem: PostItem) {
            binding.apply {
                Glide.with(root)
                    .load(postItem.creator.user.profileAvatarUrl)
                    .placeholder(R.drawable.default_profile_avatar)
                    .into(imageViewCreatorProfileAvatar)
                textViewCreatorName.text = postItem.creator.user.username
                textViewPublicationDate.text = postItem.publicationDate
                textViewTitle.text = postItem.title
                textViewContent.text = postItem.content
                textViewContent.isVisible = postItem.isAvailable

                // viewPagerImages - later...

                postTagAdapter = PostTagAdapter()
                recyclerViewTags.adapter = postTagAdapter
                postTagAdapter?.submitList(postItem.tags)

                textViewIsEdited.isVisible = postItem.isEdited
                constraintLayoutIsNotAvailable.isVisible = !postItem.isAvailable

                postSubscriptionAdapter = PostSubscriptionAdapter()
                recyclerViewRequiredSubscriptions.adapter = postSubscriptionAdapter
                postSubscriptionAdapter?.submitList(postItem.requiredSubscriptions)

                root.setOnClickListener {
                    if (postItem.isAvailable) {
                        onItemClickListener(postItem)
                    }
                }

                imageViewCreatorProfileAvatar.setOnClickListener {
                    onProfileAvatarClickListener(postItem)
                }

                imageViewMore.isVisible = userId == postItem.creator.user.id
                imageViewMore.setOnClickListener {
                    onMoreClickListener(postItem)
                }

                buttonSubscribe.setOnClickListener {
                    onSubscribeClickListener(postItem)
                }

                textViewLikeCount.text = postItem.likeCount.toString()
                textViewLikeCount.isVisible = postItem.isAvailable
                if (postItem.isLiked) {
                    imageViewLike.setImageResource(R.drawable.ic_favorite_filled_accent)
                } else {
                    imageViewLike.setImageResource(R.drawable.ic_favorite)
                }
                imageViewLike.isVisible = postItem.isAvailable
                imageViewLike.setOnClickListener {
                    val changedPostItem = postItem.copy(isLiked = !postItem.isLiked)
                    if (changedPostItem.isLiked) {
                        onLikeClickListener(changedPostItem)
                    } else {
                        onDislikeClickListener(changedPostItem)
                    }
                }

                textViewCommentCount.text = postItem.commentCount.toString()
                textViewCommentCount.isVisible = postItem.isAvailable
                imageViewComment.isVisible = postItem.isAvailable
                imageViewComment.setOnClickListener {
                    onCommentClickListener(postItem)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PostItem>() {
            override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}