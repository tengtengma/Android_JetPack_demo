package com.person.android_demo.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class HomeDetailViewModel (savedStateHandle: SavedStateHandle): ViewModel() {

    val id: String = savedStateHandle["id"] ?: ""

    private val rawTitle: String = savedStateHandle["title"] ?: ""
    private val rawUrl: String = savedStateHandle["url"] ?: ""

    val title: String = try {
        URLDecoder.decode(rawTitle, StandardCharsets.UTF_8.name())
    } catch (_: Exception) {
        rawTitle
    }

    val url: String = try {
        URLDecoder.decode(rawUrl, StandardCharsets.UTF_8.name())
    } catch (_: Exception) {
        rawUrl
    }
}