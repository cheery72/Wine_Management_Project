package io.directional.wine.dto

data class UpdateWineryRequest(
    val regionId: Long,
    val wineryNameKorean: String,
    val wineryNameEnglish: String,
)