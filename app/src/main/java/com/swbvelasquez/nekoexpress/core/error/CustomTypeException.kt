package com.swbvelasquez.nekoexpress.core.error

enum class CustomTypeException(val code:Int, val message:String) {
    DB_GET_LIST(10000,"Error al obtener la lista de la base de datos"),
    DB_GET_ONE(10001,"Error al obtener la entidad de la base de datos"),
    DB_INSERT_LIST(10100,"Error al insertar la lista en la base de datos"),
    DB_INSERT_ONE(10101,"Error al insertar la entidad en la base de datos"),
    DB_UPDATE_LIST(10200,"Error al actualizar la lista en la base de datos"),
    DB_UPDATE_ONE(10201,"Error al actualizar la entidad en la base de datos"),
    DB_DELETE_LIST(10300,"Error al eliminar la lista de la base de datos"),
    DB_DELETE_ONE(10301,"Error al eliminar la entidad de la base de datos"),
    UNKNOWN(99999,"Error no controlado"),
    NONE(0,"")
}