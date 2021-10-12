package com.braze.advancedsamples

open class NoParameterSingleton<out T: Any> (creator: () -> T){

    private var creator: (() -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(): T {
        val checkInstance = instance
        if (checkInstance != null){
            return checkInstance
        }
        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null){
                checkInstanceAgain
            }else{
                val created = creator!!()
                instance = created
                creator = null
                created
            }
        }

    }
}