package io.directional.wine.entity

import io.directional.wine.dto.CreateWineryRequest
import io.directional.wine.dto.UpdateWineryRequest
import jakarta.persistence.*


@Entity
class Winery(

    @Id
    @Column(name = "winery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var nameKorean: String,

    var nameEnglish: String,

    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    var region: Region,

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

    fun setWineryInfo(updateWineryRequest: UpdateWineryRequest, region: Region) {
        this.nameEnglish = updateWineryRequest.wineryNameEnglish
        this.nameKorean = updateWineryRequest.wineryNameKorean
        this.region = region
    }
}