package io.directional.wine.repository.querydsl

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.dto.QWineryWithRegionDto
import io.directional.wine.dto.QWineryWithRegionWithWineNameDto
import io.directional.wine.dto.WineryWithRegionDto
import io.directional.wine.dto.WineryWithRegionWithWineNameDto
import io.directional.wine.entity.QRegion
import io.directional.wine.entity.QWine
import io.directional.wine.entity.QWinery

class WineryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qWinery: QWinery = QWinery.winery,
    private val qRegion: QRegion = QRegion.region,
    private val qWine: QWine = QWine.wine
) : WineryRepositoryCustom {

    override fun findWineryWithRegion(wineryName: String, wineryRegion: String): WineryWithRegionWithWineNameDto? {

        return jpaQueryFactory
            .select(
                QWineryWithRegionWithWineNameDto(
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                    qWine.nameEnglish,
                    qWine.nameKorean
                )
            ).from(qWinery)
            .join(qWinery.region, qRegion)
            .join(qWinery.wine, qWine)
            .where(
                qWinery.deleted.isFalse.and(
                    qRegion.deleted.isFalse.and(
                        qWine.deleted.isFalse
                            .and(
                                qWinery.nameEnglish.eq(wineryName).or(qWinery.nameKorean.eq(wineryName))
                                    .and(
                                        qWinery.region.nameEnglish.eq(wineryRegion)
                                            .or(qWinery.region.nameKorean.eq(wineryRegion))
                                    )
                            )
                    )
                )
            )
            .orderBy(qWinery.nameEnglish.asc())
            .fetchFirst()
    }

    override fun findWineryWithRegionAll(wineryName: String, wineryRegion: String): List<WineryWithRegionDto> {
        val wineryNameExpression = getExpressionLike(wineryName)
        val wineryRegionExpression = getExpressionLike(wineryRegion)

        return jpaQueryFactory
            .select(
                QWineryWithRegionDto(
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                )
            ).from(qWinery)
            .join(qWinery.region, qRegion)
            .where(
                qWinery.deleted.isFalse.and(
                    qRegion.deleted.isFalse
                        .and(
                            qWinery.nameEnglish.like(wineryNameExpression)
                                .or(qWinery.nameKorean.like(wineryNameExpression))
                                .and(
                                    qWinery.region.nameEnglish.like(wineryRegionExpression)
                                        .or(qWinery.region.nameKorean.like(wineryRegionExpression))
                                )
                        )
                )
            )
            .orderBy(qWinery.nameEnglish.asc())
            .fetch()
    }

    private fun getExpressionLike(search: String): StringExpression? {
        return Expressions.asString("$search%")
    }
}