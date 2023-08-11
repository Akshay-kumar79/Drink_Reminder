package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.akshaw.drinkreminder.onboarding_domain.utils.previousPage

/**
 * Returns next page on success and failure indicates current page is starting one
 */
class GetPreviousPage {

    operator fun invoke(currentPage: OnboardingPage): Result<OnboardingPage>{
        return if (currentPage.previousPage() != null){
            Result.success(currentPage.previousPage()!!)
        }else{
            Result.failure(Exception())
        }
    }

}