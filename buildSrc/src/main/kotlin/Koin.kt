object Koin {
    const val koinVersion = "3.4.0"
    const val core = "io.insert-koin:koin-core:$koinVersion"

    const val koinAndroidVersion = "3.4.0"
    const val android = "io.insert-koin:koin-android:$koinAndroidVersion"
    const val navigation = "io.insert-koin:koin-androidx-navigation:$koinAndroidVersion"

    const val koinAndroidComposeVersion = "3.4.4"
    const val compose = "io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion"

}

object KoinTest {
    const val koinTesting = "io.insert-koin:koin-test:${Koin.koinVersion}"
    const val koinJunit4Testing = "io.insert-koin:koin-test-junit4:${Koin.koinVersion}"
}
