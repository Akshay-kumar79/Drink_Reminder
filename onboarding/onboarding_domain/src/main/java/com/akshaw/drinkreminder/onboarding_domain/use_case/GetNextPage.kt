package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.akshaw.drinkreminder.onboarding_domain.utils.nextPage

/**
 *  Provides Next Onboarding page to be shown
 */
class GetNextPage {
    
    /**
     * @return
     * -> [Result.success] with next page if [OnboardingPage.nextPage] is not null for
     * [currentPage], if next page is [OnboardingPage.PERMISSION] and [hasAllPermission]
     * is true then [OnboardingPage.PERMISSION] page will be skipped
     *
     *  -> [Result.failure] if [OnboardingPage.nextPage] is null for [currentPage],
     *  It indicates that onboarding is finished
     *
     */
    operator fun invoke(currentPage: OnboardingPage, hasAllPermission: Boolean): Result<OnboardingPage> {
        
        return currentPage.nextPage()?.let { nextPage ->
            
            if (nextPage == OnboardingPage.PERMISSION && hasAllPermission) {
                nextPage.nextPage()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception())
            } else {
                Result.success(nextPage)
            }
        } ?: Result.failure(Exception())
    }
    
}