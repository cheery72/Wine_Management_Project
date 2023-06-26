package io.directional.wine.entity

import io.directional.wine.payload.request.CreateGrapeRequest
import jakarta.persistence.*

@Entity
class Grape(

    @Id
    @Column(name = "grape_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var nameKorean: String,

    var nameEnglish: String,

    var acidity: Int,

    var body: Int,

    var sweetness: Int,

    var tannin: Int,

    var deleted: Boolean = false,

    @OneToMany(mappedBy = "grape")
    val wineGrape: List<WineGrape> = emptyList(),

    @OneToMany(mappedBy = "grape")
    val grapeShare: List<GrapeShare> = emptyList(),

    ) : BaseTime() {

    companion object {
        fun toEntity(createGrapeRequest: CreateGrapeRequest): Grape {
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

    fun setGrapeInfo(createGrapeRequest: CreateGrapeRequest) {
        this.nameEnglish = createGrapeRequest.grapeNameEnglish
        this.nameKorean = createGrapeRequest.grapeNameKorean
        this.body = createGrapeRequest.grapeBody
        this.acidity = createGrapeRequest.grapeAcidity
        this.tannin = createGrapeRequest.grapeTannin
        this.sweetness = createGrapeRequest.grapeSweetness
    }

    fun setDeleted() {
        this.deleted = true
    }
}