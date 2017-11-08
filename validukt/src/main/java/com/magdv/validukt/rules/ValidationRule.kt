package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
interface ValidationRule<in T> {
    val message: String
    fun check(input: T): ValidationResult
}