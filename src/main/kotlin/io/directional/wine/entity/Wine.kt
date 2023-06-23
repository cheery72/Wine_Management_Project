package io.directional.wine.entity

import io.directional.wine.dto.CreateWineRequest
import jakarta.persistence.*
@Entity
class Wine(

    @Id
    @Column(name = "wine_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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

    var price: Int,

    val style: String?,

    val grade: String?,

    val importer: String,

    @OneToMany(mappedBy = "wine")
    val aroma: List<WineAroma> = emptyList(),

    @OneToMany(mappedBy = "wine")
    val pairing: List<WinePairing> = emptyList(),

    @OneToMany(mappedBy = "wine")
    val wineGrape: List<WineGrape> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winery_id")
    val winery: Winery,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    val region: Region,

) {
    companion object {
        fun toEntity(createWineRequest: CreateWineRequest, winery: Winery, region: Region): Wine {
            // Wine 엔티티로 변환하는 로직 작성
            return Wine(
                type = createWineRequest.type,
                nameKorean = createWineRequest.nameKorean,
                nameEnglish = createWineRequest.nameEnglish,
                alcohol = createWineRequest.alcohol,
                acidity = createWineRequest.acidity,
                body = createWineRequest.body,
                sweetness = createWineRequest.sweetness,
                tannin = createWineRequest.tannin,
                servingTemperature = createWineRequest.servingTemperature,
                score = createWineRequest.score,
                price = createWineRequest.price,
                style = createWineRequest.style,
                grade = createWineRequest.grade,
                importer = createWineRequest.importer,
                winery = winery,
                region = region
            )
        }
    }
}

