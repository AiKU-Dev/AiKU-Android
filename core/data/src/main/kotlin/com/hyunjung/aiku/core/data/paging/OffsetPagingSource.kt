package com.hyunjung.aiku.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyunjung.aiku.core.data.paging.PagingDefaults.INITIAL_PAGE

internal class OffsetPagingSource<T : Any>(
    private val initialPage: Int = INITIAL_PAGE,
    private val fetch: suspend (page: Int) -> List<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: initialPage

        return try {
            val items = fetch(page)

            LoadResult.Page(
                data = items,
                prevKey = if (page == initialPage) null else page - 1,
                nextKey = if (items.size < params.loadSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}