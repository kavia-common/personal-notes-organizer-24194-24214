androidApplication {
    namespace = "org.example.app"

    dependencies {
        // AndroidX Core UI
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.activity:activity-ktx:1.9.2")
        implementation("androidx.fragment:fragment-ktx:1.8.2")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")

        // Room (KTX + runtime)
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
        // For Room annotations without kapt, use reflectionless at runtime only (no compiler plugin here due to DCL constraints)

        // Remove sample libs from template
        // implementation("org.apache.commons:commons-text:1.11.0")
        // implementation(project(":utilities"))
    }
}
