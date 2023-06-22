package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class Grape(

    @Id
    @Column(name = "grape_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val nameKorean: String,

    val nameEnglish: String,

    val acidity: Int,

    val body: Int,

    val sweetness: Int,

    val tannin: Int,

    @OneToMany(mappedBy = "grape")
    val wineGrape: List<WineGrape> = emptyList(),

)