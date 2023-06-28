package io.directional.wine.payload.request

data class UpdateWineryRequest(
    val regionId: Long,
    val wineryNameKorean: String,
    val wineryNameEnglish: String,
)