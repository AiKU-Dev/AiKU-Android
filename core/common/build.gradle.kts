plugins{
    alias(libs.plugins.aiku.jvm.library)
    alias(libs.plugins.aiku.hilt)
}

dependencies{
    implementation(libs.kotlinx.coroutines.core)
}