package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.akshaw.drinkreminder.onboarding_domain.utils.nextPage

/**
 * Returns next page on success and failure indicates onboarding finished
 */
class GetNextPage {

    operator fun invoke(currentPage: OnboardingPage): Result<OnboardingPage>{
        return if (currentPage.nextPage() != null){
            Result.success(currentPage.nextPage()!!)
        }else{
            Result.failure(Exception())
        }
    }

}