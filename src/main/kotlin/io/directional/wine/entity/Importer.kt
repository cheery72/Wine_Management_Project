package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class Importer(

    @Id
    @Column(name = "importer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    var deleted: Boolean,

    @OneToMany(mappedBy = "importer")
    val wineGrape: List<Wine> = emptyList(),

) : BaseTime()