package com.kevin.githubsearch.di

import com.kevin.githubsearch.data.GitHubInfoRepository
import com.kevin.githubsearch.data.datasource.remote.GitHubRemoteDataSource
import com.kevin.githubsearch.data.datasource.remote.GitHubUserInfoResponse
import com.kevin.githubsearch.data.datasource.remote.provideGitHubInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides

    fun provideGitHubRepository(remoteDataSource: GitHubRemoteDataSource): GitHubInfoRepository {
        return GitHubInfoRepository(remoteDataSource = remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideGitHubRemoteDataSource(): GitHubRemoteDataSource {
        return GitHubRemoteDataSource(provideGitHubInfoService())
    }
}