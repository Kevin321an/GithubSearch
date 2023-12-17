package com.kevin.githubsearch

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp
const val IMAGE_CACHE_PATH = "kevin_image_cache"
const val IMAGE_CACHE_MAX_SIZE = 0.25

@HiltAndroidApp
class GitHubApplication: Application(),ImageLoaderFactory{
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
                .memoryCache {
                    MemoryCache.Builder(applicationContext)
                            .maxSizePercent(IMAGE_CACHE_MAX_SIZE)
                            .build()
                }
                .diskCache {
                    DiskCache.Builder()
                            .directory(applicationContext.cacheDir.resolve(IMAGE_CACHE_PATH))
                            .maxSizePercent(IMAGE_CACHE_MAX_SIZE)
                            .build()
                }
                .build()
    }
}