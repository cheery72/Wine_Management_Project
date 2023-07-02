package io.directional.wine.payload.dto

import com.querydsl.core.annotations.QueryProjection

data class ImporterWithWineDto @QueryProjection constructor(
    val importerName: String,
    val importerWineNameEnglish: String,
    val importerWineNameKorean: String,
) {
}