package ar.edu.unlam.mobile.scaffolding.domain

object Authentication {
    private const val WRONG_USER_PASSWORD = "Email y/o contraseña incorrectas"
    private const val DUPLICATE_EMAIL = "Este email ya se encuentra registrado"
    private const val BAD_REQUEST = "Ha ocurrido un error inesperado intentelo mas tarde"
    private const val SUCCESS_SING_UP = "Se ha registrado su cuenta correctamente"
    private const val SUCCESS_LOGIN = "Se ha iniciado sesion correctamente"
    private const val SUCCESS_USER_EDIT = "Se han guardados los cambios correctamente"

    fun messageApi(
        message: String?,
        isLoginOrigin: Boolean = false,
        isSingUpOrigin: Boolean = false,
        isEditUserOrigin: Boolean = false,
    ): String? {
        when {
            message.isNullOrBlank() -> {
                return null
            }

            message.contains("500") -> {
                return DUPLICATE_EMAIL
            }

            message.contains("401") -> {
                return WRONG_USER_PASSWORD
            }

            message.contains("200") && isLoginOrigin -> {
                return SUCCESS_LOGIN
            }

            message.contains("200") && isSingUpOrigin -> {
                return SUCCESS_SING_UP
            }

            message.contains("200") && isEditUserOrigin -> {
                return SUCCESS_USER_EDIT
            }

            else -> {
                return BAD_REQUEST
            }
        }
    }
}
