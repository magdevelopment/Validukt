package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
interface Validator<in T> {
    val tag: String?
    fun validate(value: T): ValidationResult
}