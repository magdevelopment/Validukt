package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

class CompositeValidator<T>(

    private val validators: Set<Validator<T>>,
    override val tag: String? = null) : Validator<T> {

    override fun validate(value: T): ValidationResult {
        if (validators.any { it.validate(value) == ValidationResult.Success }) return ValidationResult.Success

        return validators
            .map { it.validate(value) }
            .first { it is ValidationResult.Failure }
    }
}