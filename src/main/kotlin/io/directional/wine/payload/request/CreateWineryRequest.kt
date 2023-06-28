package io.directional.wine.payload.request

data class CreateWineryRequest(
    val wineryNameKorean: String,
    val wineryNameEnglish: String,
)