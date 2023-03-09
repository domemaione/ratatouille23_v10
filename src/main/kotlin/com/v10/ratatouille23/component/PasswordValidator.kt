package com.v10.ratatouille23.component


import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class PasswordValidator(

) {

    fun supports(clazz: Class<*>): Boolean {
        return String::class.java == clazz
    }

    fun validate(target: Any): Errors {
        val password = target as String
        val errors = BeanPropertyBindingResult(target, "password")

        if (password.length < 8) {
            errors.reject("password.too.short", "La password deve avere almeno 8 caratteri")
        }

        if (!password.matches(Regex(".*[A-Z].*"))) {
            errors.reject("password.no.uppercase", "La password deve contenere almeno una lettera maiuscola")
        }

        if (!password.matches(Regex(".*[a-z].*"))) {
            errors.reject("password.no.lowercase", "La password deve contenere almeno una lettera minuscola")
        }

        if (!password.matches(Regex(".*\\d.*"))) {
            errors.reject("password.no.digits", "La password deve contenere almeno un numero")
        }

        if (!password.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))) {
            errors.reject("password.no.special.chars", "La password deve contenere almeno un carattere speciale")
        }
        return errors
    }
}
