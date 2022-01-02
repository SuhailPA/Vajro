package com.suhail.vajro.utils

import kotlinx.coroutines.flow.*

inline fun <DataBaseData, BackEndData> networkBoundResource(

    crossinline query: () -> Flow<DataBaseData>,
    crossinline fetch: suspend () -> BackEndData,
    crossinline saveFetchResult: suspend (BackEndData) -> Unit,
    crossinline shouldFetch: (DataBaseData) -> Boolean = { true }
) = flow {

    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map {
                Resource.Error(it, throwable)
            }
        }
    } else {
        query().map {
            Resource.Success(it)
        }
    }
    emitAll(flow)
}