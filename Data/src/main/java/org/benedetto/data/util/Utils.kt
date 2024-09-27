package org.benedetto.data.util

import android.util.Log

fun log(msg: String) = Log.d("[${Thread.currentThread().name}]", msg)