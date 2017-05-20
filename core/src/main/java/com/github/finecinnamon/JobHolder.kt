package com.github.finecinnamon

import kotlinx.coroutines.experimental.Job

/**
 * Represents classes that can execute tasks on a coroutine based job tree
 */
interface JobHolder {
    val job: Job?
}
