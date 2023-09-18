package com.akshaw.drinkreminder.core.util


enum class TimeUnit(val id: Int) {
    
    /** ids should not have number gap (for number picker purpose in UI) */
    AM(100),
    PM(101);
    
    companion object{
        fun fromId(id: Int): TimeUnit? {
            return when (id) {
                100 -> AM
                101 -> PM
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


