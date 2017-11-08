package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

class LambdaValidator<T>(

    override val tag: String? = null,
    private val lambda: (T) -> ValidationResult) : Validator<T> {

    override fun validate(value: T): ValidationResult {
        return lambda.invoke(value)
    }
}