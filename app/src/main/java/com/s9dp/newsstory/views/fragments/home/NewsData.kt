package com.s9dp.newsstory.views.fragments.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class NewsData(
    @SerialName("articles")
    val articles: ArrayList<Article?>? = null,
    @SerialName("status")
    val status: String? = null, // ok
    @SerialName("totalResults")
    val totalResults: Int? = null // 2253
) {
    @Keep
    @Serializable
    data class Article(
        @SerialName("author")
        val author: String? = null, // Jon Fingas
        @SerialName("content")
        val content: String? = null, // You no longer need others to use the same phone OS to share your digital car keys. Google has added car key sharing to Pixel devices, making cross-platform swaps available in an early form. If you're… [+1415 chars]
        @SerialName("description")
        val description: String? = null, // You no longer need others to use the same phone OS to share your digital car keys. Google has added car key sharing to Pixel devices, making cross-platform swaps available in an early form. If you're using an iPhone running iOS 16.1, you can send keys stored …
        @SerialName("publishedAt")
        val publishedAt: String? = null, // 2022-12-02T16:25:05Z
        @SerialName("source")
        val source: Source? = null,
        @SerialName("title")
        val title: String? = null, // iPhone users can now share their digital car keys with Pixel owners
        @SerialName("url")
        val url: String? = null, // https://www.engadget.com/iphone-digital-car-keys-share-with-android-pixel-162505575.html
        @SerialName("urlToImage")
        val urlToImage: String? = null // https://s.yimg.com/os/creatr-uploaded-images/2022-12/5280b760-7254-11ed-acbd-f2b216bac9e7
    ) {
        @Keep
        @Serializable
        data class Source(
            @SerialName("id")
            val id: String? = null, // engadget
            @SerialName("name")
            val name: String? = null // Engadget
        )
    }
}