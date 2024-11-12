package com.example.domain.model


 class SectionItem(

    var id: Int?,
    var digitalId: Int?,
    var title: String?,
    var issueNumber: Int?,
    var variantDescription: String?,
    var description: String?,
    var modified: String?,
    var isbn: String?,
    var upc: String?,
    var diamondCode: String?,
    var ean: String?,
    var issn: String?,
    var format: String?,
    var pageCount: Int?,
//    var textObjects: List<TextObjects>,
    var resourceURI: String?,
    var urls: List<Url>,
//    var series: Series?,
//    var variants: List<Variant>,
//    var collections: List<Collection>,
//    var collectedIssues: List<String>,
//    var dates: List<Date>,
//    var prices: List<Prices>,
    var thumbnail: Thumbnail,
    var images: List<Image>,
//    var creators: Creators,
//    var characters: Characters,
//    var stories: Stories?,
//    var events: Events?

)


data class Creators(

    var available: Int?,
    var collectionURI: String?,
    var items: List<Item>,
    var returned: Int?,

    )
data class Variant(
    val resourceURI: String,
    val name: String
)
data class Characters(

    var available: Int?,
    var collectionURI: String?,
    var items: List<Item>,
    var returned: Int?,

    )

data class TextObjects(

    var type: String? ,
    var language: String? ,
    var text: String?

)

data class Date(
    var type: String? ,
    var date: String?
)

data class Prices(

    var type: String? ,
    var price: Double?

)

data class Image(
    val extension: String,
    val path: String
)

