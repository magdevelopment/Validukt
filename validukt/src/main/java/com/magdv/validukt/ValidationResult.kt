package com.magdv.validukt

/**
 * Created by Maxim Pisarenko on 27.10.2017.
 */
sealed class ValidationResult {
    object Success : ValidationResult()
    class Failure(val message: String) : ValidationResult()
}
