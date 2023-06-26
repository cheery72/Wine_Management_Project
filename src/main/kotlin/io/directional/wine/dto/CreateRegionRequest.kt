package io.directional.wine.dto

data class CreateRegionRequest(
    val regionNameKorean: String,
    val regionNameEnglish: String,
    val regionParentId: Long?,
)