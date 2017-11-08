package com.magdv.validuktexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.widget.Toast
import com.magdv.validukt.ValidationResult
import com.magdv.validukt.rules.BooleanRule
import com.magdv.validukt.rules.EmptyRule
import com.magdv.validukt.rules.MinLengthRule
import com.magdv.validukt.rules.RegexRule
import com.magdv.validukt.validators.ComplexValidator
import com.magdv.validukt.validators.LambdaValidator
import com.magdv.validukt.validators.PropertyValidator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val passwordMatchKey = "passwordMatch"
    private val validator by lazy {
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
            if (it.password == it.passworcConfirmation) return@LambdaValidator ValidationResult.Success
            return@LambdaValidator ValidationResult.Failure(getString(R.string.error_password_match))
        }

        ComplexValidator(setOf(
            agreementValidator,
            passwordValidator,
            confirmPasswordValidator,
            emailValidator,
            matchingPasswordValidator)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val isAgreementAccepted = agreementCheckBox.isChecked

            val registrationForm = RegistrationForm(email, password, confirmPassword, isAgreementAccepted)
            val validationResult = validator.validate(registrationForm)
            val errorMap = validationResult.mapValues {
                val result = it.value as? ValidationResult.Failure
                result?.message
            }

            showValidationErrors(errorMap)
            if (validationResult.none { it.value is ValidationResult.Failure }) {
                Toast.makeText(it.context, getString(R.string.success), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showValidationErrors(errorMap: Map<String, String?>) {
        emailInputLayout.error = errorMap[RegistrationForm::email.name]
        passwordInputLayout.error = errorMap[RegistrationForm::password.name]
        confirmPasswordInputLayout.error = errorMap[RegistrationForm::passworcConfirmation.name]

        errorMap[RegistrationForm::isAgreementAccepted.name]?.let {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        }

        errorMap[passwordMatchKey]?.let {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        }
    }
}
