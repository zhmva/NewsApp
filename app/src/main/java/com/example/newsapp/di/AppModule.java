package com.example.newsapp.di;


import com.example.newsapp.data.remote.NewsApi;
import com.example.newsapp.data.repositories.MainRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {
    @Provides
    public static OkHttpClient provideClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
                ).build();
    }

    @Provides
    public static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    public static NewsApi provideApi(Retrofit retrofit) {
        return  retrofit.create(NewsApi.class);
    }

    @Provides
    public static MainRepository provideMainRepository(NewsApi api) {
        return new MainRepository(api);
    }
}
