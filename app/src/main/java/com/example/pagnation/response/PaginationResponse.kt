package com.example.pagnation.response

import com.google.gson.annotations.SerializedName

data class PaginationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("owner") val owner: PaginationResponse?,
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val ImageUrl: String?,
    @SerializedName("followers_url") val followersUrl: String?,
    @SerializedName("following_url") val followingUrl: String?,
    @SerializedName("type") val type: String
)