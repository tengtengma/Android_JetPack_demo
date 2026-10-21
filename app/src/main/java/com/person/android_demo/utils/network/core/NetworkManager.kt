package com.person.android_demo.utils.network.core

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class NetworkManager(private val configuration: NetworkConfiguration) : NetworkServiceProtocol {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(configuration.requestTimeout, TimeUnit.SECONDS)
        .readTimeout(configuration.responseTimeout, TimeUnit.SECONDS)
        .build()

    override suspend fun <T> request(request: NetworkRequest<T>): Result<T> {
        return suspendCancellableCoroutine { continuation ->
            try {
                // 1. 构建完整的 URL 和 Query 参数
                val urlBuilder = (configuration.baseUrl.trimEnd('/') + "/" + request.path.trimStart('/'))
                    .toHttpUrlOrNull()?.newBuilder()

                if (urlBuilder == null) {
                    continuation.resume(Result.failure(NetworkError.Unknown(IllegalArgumentException("Invalid URL"))))
                    return@suspendCancellableCoroutine
                }

                // 合并 Query 参数
                request.queryParameters?.forEach { (key, value) ->
                    urlBuilder.addQueryParameter(key, value)
                }

                val finalUrl = urlBuilder.build()
                val okHttpRequestBuilder = Request.Builder().url(finalUrl)

                // 2. 设置请求头
                request.headers?.forEach { (key, value) ->
                    okHttpRequestBuilder.addHeader(key, value)
                }

                // 3. 处理 Method 和 Body 编码
                setupRequestMethodAndBody(okHttpRequestBuilder, request)

                val httpRequest = okHttpRequestBuilder.build()
                val call = client.newCall(httpRequest)

                continuation.invokeOnCancellation {
                    call.cancel()
                }

                // 4. 执行请求
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resume(Result.failure(NetworkError.NetworkConnectionError()))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        response.use {
                            val responseBodyString = response.body?.string() ?: ""
                            if (response.isSuccessful) {
                                try {
                                    @Suppress("UNCHECKED_CAST")
                                    val decodedObject = Gson().fromJson<T>(responseBodyString, request.responseType)
                                    continuation.resume(Result.success(decodedObject))
                                } catch (e: Exception) {
                                    Log.e("NetworkManager", "Decoding error: ${e.localizedMessage}")
                                    Log.e("NetworkManager", "Response JSON: $responseBodyString")
                                    continuation.resume(Result.failure(NetworkError.DecodingError(e)))
                                }
                            } else {
                                val error = when (response.code) {
                                    401 -> NetworkError.Unauthorized()
                                    404 -> NetworkError.NotFound()
                                    else -> NetworkError.HttpError(response.code, response.message)
                                }
                                continuation.resume(Result.failure(error))
                            }
                        }
                    }
                })

            } catch (e: Exception) {
                continuation.resume(Result.failure(NetworkError.Unknown(e)))
            }
        }
    }

    // 辅助方法：配置 HTTP 方法与 Body 编码
    private fun setupRequestMethodAndBody(builder: Request.Builder, request: NetworkRequest<*>) {
        when (request.method) {
            HttpMethod.GET -> builder.get()
            HttpMethod.POST -> {
                builder.post(createBody(request))
            }
            HttpMethod.PUT -> {
                builder.put(createBody(request))
            }
            HttpMethod.DELETE -> {
                if (request.body != null) {
                    builder.delete(createBody(request))
                } else {
                    builder.delete()
                }
            }
        }
    }

    private fun createBody(request: NetworkRequest<*>): RequestBody {
        val emptyBody = "".toRequestBody(null)
        val bodyObj = request.body ?: return emptyBody

        return when (request.bodyEncoding) {
            BodyEncoding.JSON -> {
                val jsonString = Gson().toJson(bodyObj)
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaType())
            }
            BodyEncoding.URL_ENCODED -> {
                // Form 表单编码
                val formBodyBuilder = FormBody.Builder()
                if (bodyObj is Map<*, *>) {
                    bodyObj.forEach { (key, value) ->
                        if (key != null && value != null) {
                            formBodyBuilder.add(key.toString(), value.toString())
                        }
                    }
                }
                formBodyBuilder.build()
            }
            BodyEncoding.NONE -> emptyBody
        }
    }
}