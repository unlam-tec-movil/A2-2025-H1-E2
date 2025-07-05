package ar.edu.unlam.mobile.scaffolding.ui.util

import javax.inject.Inject

class MessageFromApi
    @Inject
    constructor() {
        fun errorMessage(message: String): String =
            when {
                message.contains("unauthorized: wrong user or password") -> {
                    "Usuario y/o contraseña incorrectos"
                }

                message.contains("Duplicate entry") -> {
                    "Este email ya esta registrado"
                }

                else -> {
                    "Ha ocurrido un error inesperado"
                }
            }
    }
