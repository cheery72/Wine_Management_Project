package io.directional.wine.entity

import jakarta.persistence.*


@Entity
class Winery(

    @Id
    @Column(name = "winery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val nameKorean: String,

    val nameEnglish: String,

    @OneToMany(mappedBy = "winery")
    val wine: List<Wine> = emptyList(),

    )