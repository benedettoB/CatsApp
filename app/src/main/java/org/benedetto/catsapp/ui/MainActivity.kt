package org.benedetto.catsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import org.benedetto.data.extension.isNetworkAvailable
import org.benedetto.data.extension.toast

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
        if(isNetworkAvailable()){
            toast("Fetching cats from the network")
        }else{
            toast("Connect to the internet to fetch cats")
        }
    }
}