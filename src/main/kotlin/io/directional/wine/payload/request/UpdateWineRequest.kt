package io.directional.wine.payload.request


data class UpdateWineRequest(
    val type: String,
    val nameKorean: String,
    val nameEnglish: String,
    val alcohol: Double,
    val acidity: Int,
    val body: Int,
    val sweetness: Int,
    val tannin: Int,
    val servingTemperature: Double,
    val score: Double,
    val price: Int,
    val style: String?,
    val grade: String?,
    val wineryId: Long,
    val importerId: Long,
)