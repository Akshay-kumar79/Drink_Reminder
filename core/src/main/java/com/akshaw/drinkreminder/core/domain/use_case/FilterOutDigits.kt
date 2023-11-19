package com.akshaw.drinkreminder.core.domain.use_case

/**
 * Return all digits from string in same order and remove other characters
 */
class FilterOutDigits {
    
    operator fun invoke(amount: String): String {
        return amount.filter { it.isDigit() }
    }
    
}