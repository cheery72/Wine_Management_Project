package io.directional.wine.entity

import io.directional.wine.dto.CreateGrapeRequest
import jakarta.persistence.*

@Entity
class Grape(

    @Id
    @Column(name = "grape_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nameKorean: String,

    val nameEnglish: String,

    val acidity: Int,

    val body: Int,

    val sweetness: Int,

    val tannin: Int,

    var deleted: Boolean = false,

    @OneToMany(mappedBy = "grape")
    val wineGrape: List<WineGrape> = emptyList(),

    @OneToMany(mappedBy = "grape")
    val grapeShare: List<GrapeShare> = emptyList(),

) : BaseTime() {

    companion object {
        fun toEntity(createGrapeRequest: CreateGrapeRequest): Grape{
            return Grape(
                nameKorean = createGrapeRequest.grapeNameKorean,
                nameEnglish = createGrapeRequest.grapeNameEnglish,
                acidity = createGrapeRequest.grapeAcidity,
                body = createGrapeRequest.grapeBody,
                sweetness = createGrapeRequest.grapeSweetness,
                tannin = createGrapeRequest.grapeTannin
            )
        }
    }
}