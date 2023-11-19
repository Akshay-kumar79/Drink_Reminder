package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.akshaw.drinkreminder.onboarding_domain.utils.previousPage

/**
 * Provides previous onboarding page to be shown
 */
class GetPreviousPage {
    
    /**
     *  @return
     *  -> [Result.success] with previous page if [OnboardingPage.previousPage] is not null for
     *  [currentPage]
     *
     *  -> [Result.failure] if [OnboardingPage.previousPage] is null for [currentPage],
     *  It indicates that onboarding is finished
     */
    operator fun invoke(currentPage: OnboardingPage): Result<OnboardingPage> {
        return currentPage.previousPage()?.let { previousPage ->
            Result.success(previousPage)
        } ?: kotlin.run {
            Result.failure(Exception("previous page not found"))
        }
    }
    
}