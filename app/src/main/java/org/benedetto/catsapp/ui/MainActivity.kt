package org.benedetto.catsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.benedetto.data.extension.isNetworkAvailable
import org.benedetto.data.extension.toast

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //savedInstanceState.getBoolean("key") throws error
        //val locked: Boolean? = savedInstanceState?.getBoolean("locked")//safe call ?. operator is savedInstance is null, null will be returned else statements to right of ?. will be executed
         val locked: Boolean = savedInstanceState?.getBoolean("locked") ?: false
        //savedInstanceState?.let { println(it.getBoolean("isLocked")) }

        setContent {
            Navigation()
        }
        if(isNetworkAvailable()){
            toast("Fetching cats from network")
        }else{
            toast("Network NOT connected")
        }
    }
}