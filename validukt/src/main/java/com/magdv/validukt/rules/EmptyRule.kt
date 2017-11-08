package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

class EmptyRule(override val message: String) : ValidationRule<String?> {

    override fun check(input: String?): ValidationResult {
        if (input.isNullOrBlank()) return ValidationResult.Failure(message)
        return ValidationResult.Success
    }
}