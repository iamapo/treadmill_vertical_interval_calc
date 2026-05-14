package com.redred.treadmillvertiintervallcalc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform