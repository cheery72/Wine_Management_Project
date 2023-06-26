package io.directional.wine.dto

import com.querydsl.core.annotations.QueryProjection

data class WineryWithRegionWithWineNameDto @QueryProjection constructor(
    val wineryNameEnglish: String,
    val wineryNameKorean: String,
    val wineryRegionNameEnglish: String,
    val wineryRegionNameKorean: String,
    val wineryWineNameEnglish: String,
    val wineryWineNameKorean: String,
)