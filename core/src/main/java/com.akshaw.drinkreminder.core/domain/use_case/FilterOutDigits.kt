package com.akshaw.drinkreminder.core.domain.use_case

class FilterOutDigits {

    operator fun invoke(amount: String): String{
        return amount.filter { it.isDigit() }
    }

}