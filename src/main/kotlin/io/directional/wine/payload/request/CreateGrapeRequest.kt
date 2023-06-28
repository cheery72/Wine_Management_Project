package io.directional.wine.payload.request

data class CreateGrapeRequest(

    val grapeNameKorean: String,

    val grapeNameEnglish: String,

    val grapeAcidity: Int,

    val grapeBody: Int,

    val grapeSweetness: Int,

    val grapeTannin: Int,
)