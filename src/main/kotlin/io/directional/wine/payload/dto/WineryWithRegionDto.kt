package io.directional.wine.payload.dto

import com.querydsl.core.annotations.QueryProjection

data class WineryWithRegionDto @QueryProjection constructor(
    val wineryNameEnglish: String,
    val wineryNameKorean: String,
    val wineryRegionNameEnglish: String,
    val wineryRegionNameKorean: String,
)