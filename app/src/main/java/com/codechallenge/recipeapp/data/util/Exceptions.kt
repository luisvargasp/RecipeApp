package com.codechallenge.recipeapp.data.util

class ConnectException(message: String = "Conexión interrumpida") : Exception(message)
class UnknownHostException(message: String = "No hay conexión a internet") : Exception(message)
class SocketTimeoutException(message: String = "No hay respuesta en la conexión") : Exception(message)
class UnauthorizedException(message: String = "Usuario no autorizado") : Exception(message)
class ForbiddenException(message: String = "Usuario no autorizado") : Exception(message)
class UnexpectedException(message: String? = "Excepción no esperada") : Exception(message)
class HttpsException(message: String = "Error en la petición https") : Exception(message)
class NotFoundException(message: String = "Not Found") : Exception(message)
class BadRequestException(message: String = "Not Found") : Exception(message)