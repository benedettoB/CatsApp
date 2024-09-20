package org.benedetto.catsapp.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
@HiltAndroidApp kicks off the code generation of the Hilt components and also generates a base class for your application that uses
those generated components.

Just like other Hilt Android entry points, Applications are members injected as well. This means you can use injected fields in the Application
after super.onCreate() has been called.

Note: Since all injected fields are created at the same time in onCreate, if an object is only needed later or conditionally, remember that
you can use a Provider to defer injection. Especially in the Application class which is on the critical startup path, avoiding unnecessary
injections can be important to performance.

 */
@HiltAndroidApp
class App: Application(){
}