package io.directional.wine.payload.response

import com.querydsl.core.annotations.QueryProjection

data class WineryWithRegionResponse @QueryProjection constructor(
    val wineryNameEnglish: String,
    val wineryNameKorean: String,
    val wineryRegionNameEnglish: String,
    val wineryRegionNameKorean: String,
)