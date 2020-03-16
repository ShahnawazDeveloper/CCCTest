package com.example.ccctest.api.respone.gallery
import com.google.gson.annotations.SerializedName


data class GetImagesResponse(
    @SerializedName("photos")
    val photos: Photos?,
    @SerializedName("stat")
    val stat: String?
)

data class Photos(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("perpage")
    val perpage: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("photo")
    val photo: List<Photo>?
)

data class Photo(
    @SerializedName("id")
    val id: String?,
    @SerializedName("owner")
    val owner: String?,
    @SerializedName("secret")
    val secret: String?,
    @SerializedName("server")
    val server: String?,
    @SerializedName("farm")
    val farm: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("ispublic")
    val ispublic: Int?,
    @SerializedName("isfriend")
    val isfriend: Int?,
    @SerializedName("isfamily")
    val isfamily: Int?,
    @SerializedName("is_primary")
    val isPrimary: Int?,
    @SerializedName("has_comment")
    val hasComment: Int?
)