package com.example.domain.model

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Int
)