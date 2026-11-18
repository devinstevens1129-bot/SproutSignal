plugins { id("com.android.application") }

android {
    namespace = "com.sproutsignal.app"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.sproutsignal.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            // Signing is injected at release time (keystore kept out of the repo).
            // Until a signing config is supplied, CI signs the test release with the debug key.
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
