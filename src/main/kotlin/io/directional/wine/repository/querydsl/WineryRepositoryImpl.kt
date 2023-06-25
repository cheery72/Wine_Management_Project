package io.directional.wine.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.dto.QWineryWithRegionDto
import io.directional.wine.dto.WineryWithRegionDto
import io.directional.wine.entity.QRegion
import io.directional.wine.entity.QWine
import io.directional.wine.entity.QWinery

class WineryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qWinery: QWinery = QWinery.winery,
    private val qRegion: QRegion = QRegion.region,
    private val qWine: QWine = QWine.wine
) : WineryRepositoryCustom {

    override fun findWineryWithRegion(wineryName: String, wineryRegion: String): WineryWithRegionDto? {
        return jpaQueryFactory
            .select(
                QWineryWithRegionDto(
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                    qWine.nameEnglish,
                    qWine.nameKorean
                )
            ).from(qWinery)
            .join(qWinery.region,qRegion)
            .join(qWinery.wine,qWine)
            .where(qWinery.nameEnglish.eq(wineryName).or(qWinery.nameKorean.eq(wineryName)).and(qWinery.region.nameEnglish.eq(wineryRegion).or(qWinery.region.nameKorean.eq(wineryRegion))))
            .orderBy(qWinery.nameEnglish.asc())
            .fetchFirst()
    }

}