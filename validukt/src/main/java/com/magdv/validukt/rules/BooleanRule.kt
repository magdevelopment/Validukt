package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

class BooleanRule(override val message: String) : ValidationRule<Boolean> {

    override fun check(input: Boolean): ValidationResult {
        if (input) return ValidationResult.Success
        return ValidationResult.Failure(message)
    }
}