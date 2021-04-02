[![](https://jitpack.io/v/li2/android-network.svg)](https://jitpack.io/#li2/android-network)


## Usage

Create an implementation of API endpoints:

```
interface TmdbApi {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): TmdbMovieListAPI
}

bind<TmdbApi>() with singleton {
    NetworkBuilder.buildRetrofitAdapter<TmdbApi>(
            context = instance(),
            baseUrl = Constants.TMDB_URL,
            interceptors = listOf(
                    TmdbRequestInterceptor(),
                    TmdbResponseInterceptor(instance())),
            timeout = TmdbApi.TIMEOUT,
            debug = AppBuildConfig.DEBUG)
}
```

## Download

```
    repositories {
        maven { url "https://jitpack.io" }
    }

    implementation "com.github.li2:android-network:$release-version"
    or
    implementation "com.github.li2:android-network:master-SNAPSHOT"
```

