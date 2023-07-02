package io.directional.wine.payload.response

import io.directional.wine.payload.dto.ImporterWithWineDto

data class ImporterWithWineResponse(
    val importerName: String,
    val importerWineNameEnglish: String,
    val importerWineNameKorean: String,
) {
    companion object {
        fun ImporterWithWineDto.of() = ImporterWithWineResponse(
            this.importerName,
            this.importerWineNameEnglish,
            this.importerWineNameKorean,
        )
    }
}