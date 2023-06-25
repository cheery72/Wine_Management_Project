package io.directional.wine.entity

import io.directional.wine.dto.CreateWineryRequest
import jakarta.persistence.*


@Entity
class Winery(

    @Id
    @Column(name = "winery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nameKorean: String,

    val nameEnglish: String,

    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    val region: Region,

) : BaseTime() {

    companion object {
        fun toEntity(createWineryRequest: CreateWineryRequest, region: Region): Winery {
            return Winery(
                nameKorean = createWineryRequest.wineryNameKorean,
                nameEnglish = createWineryRequest.wineryNameEnglish,
                region = region
            )
        }
    }
}