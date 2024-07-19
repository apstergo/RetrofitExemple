package ru.apstegor.retrofitexemple.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ExempleInterceptor():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        return chain.proceed(request = chain.request())
    }
}