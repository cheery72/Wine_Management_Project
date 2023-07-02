package io.directional.wine.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.entity.QRegion
import io.directional.wine.entity.QWine
import io.directional.wine.entity.QWinery
import io.directional.wine.payload.dto.QWineryWithRegionDto
import io.directional.wine.payload.dto.QWineryWithRegionWithWineNameDto
import io.directional.wine.payload.dto.WineryWithRegionDto
import io.directional.wine.payload.dto.WineryWithRegionWithWineNameDto

class WineryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qWinery: QWinery = QWinery.winery,
    private val qRegion: QRegion = QRegion.region,
    private val qWine: QWine = QWine.wine,
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

        return jpaQueryFactory
            .select(
                QWineryWithRegionDto(
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                )
            ).from(qWinery)
            .where(
                qWinery.deleted.isFalse.and(
                    qRegion.deleted.isFalse
                        .and(
                            qWinery.nameEnglish.contains(wineryName)
                                .or(qWinery.nameKorean.contains(wineryName))
                                .and(
                                    qWinery.region.nameEnglish.eq(wineryRegion)
                                        .or(qWinery.region.nameKorean.eq(wineryRegion))
                                )
                        )
                )
            )
            .orderBy(qWinery.nameEnglish.asc())
            .fetch()
    }
}