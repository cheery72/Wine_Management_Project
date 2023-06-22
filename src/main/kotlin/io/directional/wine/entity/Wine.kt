package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class Wine(

    @Id
    @Column(name = "wine_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val type: String,

    val nameKorean: String,

    val nameEnglish: String,

    val alcohol: Double,

    val acidity: Int,

    val body: Int,

    val sweetness: Int,

    val tannin: Int,

    val servingTemperature: Double,

    val score: Double,

    var price: Int,

    val style: String?,

    val grade: String?,

    val importer: String,

    @OneToMany(mappedBy = "wine")
    val aroma: List<WineAroma> = emptyList(),

    @OneToMany(mappedBy = "wine")
    val pairing: List<WinePairing> = emptyList(),

    @OneToMany(mappedBy = "wine")
    val wineGrape: List<WineGrape> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winery_id")
    val winery: Winery,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    val region: Region,

)