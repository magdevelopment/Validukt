package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 30.10.2017.
 */
class ComplexValidator<in T>(validators: Set<Validator<T>>) {

    val validators = validators.associateBy {
        val tag = it.tag ?: throw IllegalAccessException("Tag should be not null")
        tag
    }

    fun validate(value: T): Map<String, ValidationResult> {
        val errorMap = mutableMapOf<String, ValidationResult>()
        validators.forEach { entry ->
            val result = entry.value.validate(value)
            errorMap[entry.key] = result
        }

        return errorMap
    }
}