package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
class MaxLengthRule(

    private val threshold: Int,
    override val message: String) : ValidationRule<String?> {

    override fun check(input: String?): ValidationResult {
        input ?: return ValidationResult.Failure(message)
        if (input.length > threshold) return ValidationResult.Failure(message)

        return ValidationResult.Success
    }
}