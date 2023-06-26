package io.directional.wine.entity

import io.directional.wine.dto.CreateRegionRequest
import jakarta.persistence.*

@Entity
class Region(

    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nameKorean: String,

    val nameEnglish: String,

    var deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Region?,

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
}