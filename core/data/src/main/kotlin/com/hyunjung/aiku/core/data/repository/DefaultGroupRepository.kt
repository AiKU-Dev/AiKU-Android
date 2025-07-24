package com.hyunjung.aiku.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hyunjung.aiku.core.coroutine.AikuDispatchers.IO
import com.hyunjung.aiku.core.coroutine.Dispatcher
import com.hyunjung.aiku.core.data.paging.OffsetPagingSource
import com.hyunjung.aiku.core.data.paging.PagingDefaults.PAGE_SIZE
import com.hyunjung.aiku.core.domain.repository.GroupRepository
import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultGroupRepository @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : GroupRepository {

    override fun getGroupSummaryPagingData(): Flow<PagingData<GroupSummary>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            OffsetPagingSource { groupRemoteDataSource.getGroupSummaries(it) }
        }
    ).flow.flowOn(ioDispatcher)

    override fun getGroupDetail(id: Long): Flow<GroupDetail> = flow {
        emit(groupRemoteDataSource.getGroupDetail(id))
    }

    override suspend fun createGroup(name: String) {
        groupRemoteDataSource.createGroup(name)
    }
}