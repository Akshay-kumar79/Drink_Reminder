package com.akshaw.drinkreminder.core.util

/** ids should not have number gap (for number picker purpose in UI) */
private const val AM_ID = 100
private const val PM_ID = 101

enum class TimeUnit(val id: Int) {
    
    AM(AM_ID),
    PM(PM_ID);
    
    companion object {
        fun fromId(id: Int): TimeUnit? {
            return when (id) {
                AM_ID -> AM
                PM_ID -> PM
                else -> null
            }
        }
        
        fun fromString(name: String): TimeUnit? {
            return when (name) {
                AM.name -> AM
                PM.name -> PM
                else -> null
            }
        }
        
        fun getNameFromId(id: Int): String? {
            return fromId(id)?.name
        }
        
        fun maxID() = PM.id
        fun minID() = AM.id
    }
    
}


