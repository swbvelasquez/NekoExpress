package com.swbvelasquez.nekoexpress.core.util

object Constants {
    const val API_BASE_URL = "https://fakestoreapi.com/"

    const val ROOM_DB_NAME = "NekoExpress.db"
    const val ROOM_DB_OPERATION_FAIL = -1

    const val MAX_TIME_AVAILABLE_CART = 60 * 60 * 1000 //60 * 60 * 1000
    const val TAXES_PERCENT = 0.18
    const val DEFAULT_USER_ID = 1L
    const val DEFAULT_USER_EMAIL = "default.user@gmail.com"
    const val DEFAULT_USER_IMAGE = "https://cdn-icons-png.flaticon.com/512/5904/5904059.png"

    const val CLOTH_CATEGORY = "clothing"

    const val REGEX_CARD_EXPIRE_DATE = "^(0[1-9]|1[0-2])(/|-)[0-9]{2}$" // Regex MM/DD o MM-YY
    const val REGEX_PHONE = """^\+\d{1,4}-\d{1,20}$"""
}