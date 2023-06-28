package io.directional.wine.payload.request

data class CreateRegionRequest(
    val regionNameKorean: String,
    val regionNameEnglish: String,
    val regionParentId: Long?,
)