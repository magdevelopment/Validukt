package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult
import com.magdv.validukt.rules.ValidationRule

/**
 * Created by Maxim Pisarenko on 27.10.2017.
 */

class StandartValidator<T>(private val validationRules: Set<ValidationRule<T>>,
                           override val tag: String? = null) : Validator<T> {

    override fun validate(value: T): ValidationResult {
        val error = validationRules
            .map { it.check(value) }
            .firstOrNull { it is ValidationResult.Failure }

        return error ?: ValidationResult.Success
    }
}