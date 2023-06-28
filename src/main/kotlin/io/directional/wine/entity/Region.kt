package io.directional.wine.entity

import io.directional.wine.payload.request.CreateRegionRequest
import jakarta.persistence.*

@Entity
@Table(name = "region", indexes =[
    Index(name = "idx_region_name_english", columnList = "name_english"),
    Index(name = "idx_region_name_korean", columnList = "name_korean")
])
class Region(

    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name_korean")
    var nameKorean: String,

    @Column(name = "name_english")
    var nameEnglish: String,

    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Region?,

    @OneToMany(mappedBy = "parent")
    val children: List<Region> = emptyList(),

    @OneToMany(mappedBy = "region")
    val winery: List<Winery> = emptyList(),

    @OneToMany(mappedBy = "region")
    val grapeShare: List<GrapeShare> = emptyList(),

    ) : BaseTime() {
    companion object {
        fun toEntity(createRegionRequest: CreateRegionRequest, region: Region?): Region {
            return Region(
                nameEnglish = createRegionRequest.regionNameEnglish,
                nameKorean = createRegionRequest.regionNameKorean,
                parent = region
            )
        }
    }

    fun setRegionInfo(createRegionRequest: CreateRegionRequest, parentRegion: Region?) {
        this.nameKorean = createRegionRequest.regionNameKorean
        this.nameEnglish = createRegionRequest.regionNameEnglish
        this.parent = parentRegion
    }

    fun setDeleted() {
        this.deleted = true
    }
}