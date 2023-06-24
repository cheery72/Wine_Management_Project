package io.directional.wine.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: Int,
    val message: String,
    val httpStatus: HttpStatus
) {
    NOT_FOUND_WINERY(-1000, "존재하지 않는 와인 농장 입니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_REGION(-1001, "존재하지 않는 지역 입니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_IMPORTER(-1002, "존재하지 않는 수입사 입니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_WINE(-1003, "존재하지 않는 와인 입니다.", HttpStatus.NOT_FOUND);

}

