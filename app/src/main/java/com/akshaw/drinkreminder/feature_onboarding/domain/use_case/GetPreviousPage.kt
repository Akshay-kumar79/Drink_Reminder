package com.akshaw.drinkreminder.feature_onboarding.domain.use_case

import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingPage
import com.akshaw.drinkreminder.feature_onboarding.presentation.nextPage
import com.akshaw.drinkreminder.feature_onboarding.presentation.previousPage

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