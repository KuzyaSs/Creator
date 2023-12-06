package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toFollow
import ru.ermakov.creator.data.remote.api.FollowApi
import ru.ermakov.creator.data.remote.model.RemoteFollowRequest
import ru.ermakov.creator.domain.model.Follow

class FollowRemoteDataSourceImpl(
    private val followApi: FollowApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : FollowRemoteDataSource {
    override suspend fun getFollowsByUserId(userId: String): List<Follow> {
        val remoteFollowsResponse = followApi.getFollowsByUserId(userId = userId)
        if (remoteFollowsResponse.isSuccessful) {
            Log.d("MY_TAG", "getFollowsByUserId SUCCESS ${remoteFollowsResponse.body()}")
            remoteFollowsResponse.body()?.let { remoteFollows ->
                return remoteFollows.map { remoteFollow -> remoteFollow.toFollow() }
            }
        }
        Log.d("MY_TAG", "getFollowsByUserId ERROR ${remoteFollowsResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteFollowsResponse)
    }

    override suspend fun getFollowByUserAndCreatorIds(userId: String, creatorId: String): Follow {
        val remoteFollowResponse = followApi.getFollowByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId
        )
        if (remoteFollowResponse.isSuccessful) {
            remoteFollowResponse.body()?.let { remoteFollow ->
                return remoteFollow.toFollow()
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteFollowResponse)
    }

    override suspend fun getFollowerCountByUserId(userId: String): Long {
        val followerCountResponse = followApi.getFollowerCountByUserId(userId = userId)
        if (followerCountResponse.isSuccessful) {
            followerCountResponse.body()?.let { followerCount ->
                return followerCount
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = followerCountResponse)
    }

    override suspend fun insertFollow(userId: String, creatorId: String) {
        val response = followApi.insertFollow(
            RemoteFollowRequest(userId = userId, creatorId = creatorId)
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteFollowById(followId: Long) {
        val response = followApi.deleteFollowById(followId = followId)
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}