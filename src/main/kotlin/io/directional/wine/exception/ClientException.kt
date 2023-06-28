package io.directional.wine.exception


class ClientException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)