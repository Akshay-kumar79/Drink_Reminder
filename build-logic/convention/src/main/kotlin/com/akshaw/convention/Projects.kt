package com.akshaw.convention

object Projects {
    
    object Implementation {
        
        const val CORE = ":core"
        const val CORE_COMPOSE = ":core-compose"
        
        object Feature {
            const val ONBOARDING_DOMAIN = ":onboarding:onboarding_domain"
            const val ONBOARDING_PRESENTATION = ":onboarding:onboarding_presentation"
            
            const val WATER_DOMAIN = ":water:water_domain"
            const val WATER_PRESENTATION = ":water:water_presentation"
    
            const val SETTINGS_DOMAIN = ":settings:settings-domain"
            const val SETTINGS_PRESENTATION = ":settings:settings-presentation"
        }
        
    }
    
    object TestImplementation{
    
        const val CORE_TEST = ":core-test"
    }
    
}