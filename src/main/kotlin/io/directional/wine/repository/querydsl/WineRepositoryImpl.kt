package io.directional.wine.repository.querydsl

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.dto.QWineDetailsDto
import io.directional.wine.dto.QWineWithTopRegionDto
import io.directional.wine.dto.WineDetailsDto
import io.directional.wine.dto.WineWithTopRegionDto
import io.directional.wine.entity.*


class WineRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qWine: QWine = QWine.wine,
    private val qImporter: QImporter = QImporter.importer,
    private val qWinery: QWinery = QWinery.winery,
    private val qWineAroma: QWineAroma = QWineAroma.wineAroma,
    private val qWinePairing: QWinePairing = QWinePairing.winePairing,
    private val qWineGrape: QWineGrape = QWineGrape.wineGrape,
    private val qGrape: QGrape = QGrape.grape,
    private val qRegion: QRegion = QRegion.region,
) : WineRepositoryCustom {

    override fun findWineDetails(
        wineName: String,
        wineType: String,
        alcoholMin: Double,
        alcoholMax: Double,
        priceMin: Int,
        priceMax: Int,
        wineStyle: String?,
        wineGrade: String?,
        wineRegion: String
    ): WineDetailsDto? {
        val booleanBuilder = getBooleanBuilder(wineGrade, wineStyle)

        return jpaQueryFactory
            .select(
                QWineDetailsDto(
                    qWine.type,
                    qWine.nameKorean,
                    qWine.nameEnglish,
                    qWine.alcohol,
                    qWine.acidity,
                    qWine.body,
                    qWine.sweetness,
                    qWine.tannin,
                    qWine.score,
                    qWine.price,
                    qWine.style,
                    qWine.grade,
                    qImporter.name,
                    qWineAroma.aroma,
                    qWinePairing.pairing,
                    qGrape.nameKorean,
                    qGrape.nameEnglish,
                    qRegion.nameKorean,
                    qRegion.nameEnglish,
                    qWinery.id,
                    qRegion.id,
                )
            ).from(qWine)
            .join(qWine.aroma, qWineAroma)
            .join(qWine.pairing,qWinePairing)
            .join(qWine.importer, qImporter)
            .join(qWine.winery, qWinery)
            .join(qWine.wineGrape,qWineGrape)
            .join(qWineGrape.grape,qGrape)
            .where(qWine.nameEnglish.eq(wineName).or(qWine.nameKorean.eq(wineName)).and(
                qWine.type.eq(wineType)
                    .and(qWine.alcohol.between(alcoholMin, alcoholMax))
                    .and(qWine.price.between(priceMin, priceMax))
                    .and(qWine.style.eq(wineStyle))
                    .and(booleanBuilder))
            )
            .groupBy(qWine.nameEnglish,
                qWine.nameKorean,
                qWine.type,
                qWine.alcohol,
                qWine.acidity,
                qWine.body,
                qWine.sweetness,
                qWine.tannin,
                qWine.score,
                qWine.price,
                qWine.style,
                qWine.grade,
                qImporter.name,
                qWineAroma.aroma,
                qWinePairing.pairing,
                qGrape.nameKorean,
                qGrape.nameEnglish,
                qRegion.nameKorean,
                qRegion.nameEnglish,
                qWinery.id,
                qRegion.id)
            .orderBy(qWine.nameEnglish.asc(),qWine.alcohol.asc(),qWine.acidity.asc(),
                    qWine.body.asc(),qWine.sweetness.asc(),qWine.tannin.asc(),
                    qWine.score.asc(),qWine.price.asc())
            .fetchFirst()

    }

    override fun findWineWithTopRegion(
        wineName: String,
        wineType: String,
        alcoholMin: Double,
        alcoholMax: Double,
        priceMin: Int,
        priceMax: Int,
        wineStyle: String?,
        wineGrade: String?,
        wineRegion: String
    ): List<WineWithTopRegionDto?> {
        val booleanBuilder = getBooleanBuilder(wineGrade, wineStyle)

        return jpaQueryFactory
            .select(
                QWineWithTopRegionDto(
                    qWine.type,
                    qWine.nameKorean,
                    qWine.nameEnglish,
                    qRegion.id,
                )
            ).from(qWine)
            .leftJoin(qWine.winery, qWinery)
            .where(
                qWine.nameEnglish.eq(wineName).or(qWine.nameKorean.eq(wineName)).and(
                qWine.type.eq(wineType)
                    .and(qWine.alcohol.between(alcoholMin, alcoholMax))
                    .and(qWine.price.between(priceMin, priceMax))
                    .and(qWine.style.eq(wineStyle))
                    .and(booleanBuilder))
            )
            .groupBy(qWine.nameEnglish,qWine.nameKorean,qWine.type,qRegion.id,
                qWine.alcohol,qWine.acidity,qWine.body,qWine.sweetness,qWine.tannin,
                qWine.score,qWine.price)
            .orderBy(qWine.nameEnglish.asc(),qWine.alcohol.asc(),qWine.acidity.asc(),
                qWine.body.asc(),qWine.sweetness.asc(),qWine.tannin.asc(),
                qWine.score.asc(),qWine.price.asc())
            .fetch()

    }

    private fun getBooleanBuilder(wineGrade: String?, wineStyle: String?): BooleanBuilder{
        val booleanBuilder = BooleanBuilder()

        if(wineGrade.isNullOrBlank()){
            booleanBuilder.and(qWine.grade.isNull)
        } else {
            booleanBuilder.and(qWine.grade.eq(wineGrade))
        }

        if(wineStyle.isNullOrBlank()){
            booleanBuilder.and(qWine.style.isNull)
        } else {
            booleanBuilder.and(qWine.style.eq(wineStyle))
        }

        return booleanBuilder
    }
}