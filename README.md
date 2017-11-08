# Validukt
Simple form validation written in Kotlin

## Example usage
Get latest develop snapshot from Jitpack
```gradle
implementation 'com.github.magdevelopment:Validukt:develop-SNAPSHOT'
```

## Example usage
```kotlin
val emptyRule = EmptyRule(getString(R.string.error_empty_field))
val minLengthRule = MinLengthRule(5, getString(R.string.error_short_password))

val emailValidator = PropertyValidator(RegistrationForm::email,
        setOf(emptyRule, RegexRule(Patterns.EMAIL_ADDRESS.toString(), getString(R.string.error_invalid_email))))

val passwordValidator = PropertyValidator(RegistrationForm::password,
        setOf(emptyRule, minLengthRule))

val confirmPasswordValidator = PropertyValidator(RegistrationForm::passworcConfirmation,
        setOf(emptyRule, minLengthRule))

val agreementValidator = PropertyValidator(RegistrationForm::isAgreementAccepted,
        setOf(BooleanRule(getString(R.string.error_agreement))))

val matchingPasswordValidator = LambdaValidator<RegistrationForm>(passwordMatchKey) {
        if (it.password == it.passworcConfirmation) return ValidationResult.Success
        return ValidationResult.Failure(getString(R.string.error_password_match))
    }

val validator = ComplexValidator(setOf(
        agreementValidator,
        passwordValidator,
        confirmPasswordValidator,
        emailValidator,
        matchingPasswordValidator)
    )

validator.validate(form)
```

## Custom validation
It's possible to provide custom validation by implementing Validator/ValidationRule interfaces.
