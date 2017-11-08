package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult
import com.magdv.validukt.rules.ValidationRule
import kotlin.reflect.KProperty1

/**
 * Created by Maxim Pisarenko on 30.10.2017.
 */
class PropertyValidator<T, R>(

    private val property: KProperty1<T, R>,
    private val validationRules: Set<ValidationRule<R>>,
    override val tag: String? = property.name) : Validator<T> {

    override fun validate(value: T): ValidationResult {
        val error = validationRules
            .map { it.check(property.get(value)) }
            .firstOrNull { it is ValidationResult.Failure }

        return error ?: ValidationResult.Success
    }
}