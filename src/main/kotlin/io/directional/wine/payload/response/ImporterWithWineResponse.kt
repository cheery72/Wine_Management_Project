package io.directional.wine.payload.response

import com.querydsl.core.annotations.QueryProjection

data class ImporterWithWineResponse @QueryProjection constructor(
    val importerName: String,
    val importerWineNameEnglish: String,
    val importerWineNameKorean: String,
)