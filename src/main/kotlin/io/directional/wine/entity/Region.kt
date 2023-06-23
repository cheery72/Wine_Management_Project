package io.directional.wine.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

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

    @OneToMany(mappedBy = "region")
    val wine: List<Wine> = emptyList(),

    @OneToMany(mappedBy = "region")
    val winery: List<Winery> = emptyList(),

    @OneToMany(mappedBy = "region")
    val grapeShare: List<GrapeShare> = emptyList(),

) : BaseTime()