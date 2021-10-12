package com.braze.advancedsamples

open class SingletonHolder<out T: Any, in A> (creator: (A) -> T){

    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    /**
     * Helper method that can be called once you *know* the instance is initialized
     */
    fun getInstance(): T {
        if (instance == null){
            throw IllegalArgumentException("Braze manager is not intilized!")
        }
        return instance!!
    }

    fun getInstance(arg:A): T {
        val checkInstance = instance
        if (checkInstance != null){
            return checkInstance
        }
        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null){
                checkInstanceAgain
            }else{
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }

    }
}