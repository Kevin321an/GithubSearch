package com.kevin.githubsearch.di

import com.kevin.githubsearch.data.GitHubInfoRepo
import com.kevin.githubsearch.data.GitHubInfoRepository
import com.kevin.githubsearch.data.datasource.remote.GitHubRemoteDataSource
import com.kevin.githubsearch.data.datasource.remote.provideGitHubInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteGitHubRemoteDataSource


@Module
@InstallIn(SingletonComponent::class)
object GitHubRemoteDataSourceDataModule {
    @Singleton
    @RemoteGitHubRemoteDataSource
    @Provides
    fun provideGitHubRemoteDataSource(): GitHubRemoteDataSource {
        return GitHubRemoteDataSource(provideGitHubInfoService())
    }
}
@Module
@InstallIn(SingletonComponent::class)
object GitHubInfoRepoModule{
    @Singleton

    @Provides
    fun provideGitHubRepository(
            @RemoteGitHubRemoteDataSource remoteDataSource: GitHubRemoteDataSource
    ): GitHubInfoRepo {
        return GitHubInfoRepository(remoteDataSource = remoteDataSource)
    }
}
