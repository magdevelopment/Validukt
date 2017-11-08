package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

class RegexRule(private val regexString: String, override val message: String) : ValidationRule<String?> {

    override fun check(input: String?): ValidationResult {
        if (input?.matches(regexString.toRegex()) == true) return ValidationResult.Success
        return ValidationResult.Failure(message)
    }
}