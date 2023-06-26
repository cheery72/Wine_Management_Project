package io.directional.wine.entity

import io.directional.wine.dto.CreateRegionRequest
import jakarta.persistence.*

@Entity
class Region(

    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var nameKorean: String,

    var nameEnglish: String,

    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Region?,

    @OneToMany(mappedBy = "parent")
    val children: List<Region> = emptyList(),

) : BaseTime() {
    companion object {
        fun toEntity(createRegionRequest: CreateRegionRequest, region: Region?): Region{
            return Region(
                nameEnglish = createRegionRequest.regionNameEnglish,
                nameKorean = createRegionRequest.regionNameKorean,
                parent = region
            )
        }
    }

    fun setRegionInfo(createRegionRequest: CreateRegionRequest,parentRegion: Region?){
        this.nameKorean = createRegionRequest.regionNameKorean
        this.nameEnglish = createRegionRequest.regionNameEnglish
        this.parent = parentRegion
    }
}