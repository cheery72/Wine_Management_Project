package io.directional.wine.entity

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

) : BaseTime()