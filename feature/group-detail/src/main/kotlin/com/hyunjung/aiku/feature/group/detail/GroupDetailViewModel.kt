package com.hyunjung.aiku.feature.group.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyunjung.aiku.core.domain.repository.GroupRepository
import com.hyunjung.aiku.core.domain.repository.ScheduleRepository
import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.result.Result
import com.hyunjung.aiku.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    groupRepository: GroupRepository,
    scheduleRepository: ScheduleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = savedStateHandle.toRoute<AikuRoute.GroupDetailRoute>().groupId

    val groupDetailUiState: StateFlow<GroupDetailUiState> =
        groupRepository.getGroupDetail(groupId)
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> GroupDetailUiState.Error(result.exception)
                    is Result.Loading -> GroupDetailUiState.Loading
                    is Result.Success -> GroupDetailUiState.Success(result.data)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = GroupDetailUiState.Loading
            )

    val groupSchedulePagingData: Flow<PagingData<GroupSchedule>> =
        scheduleRepository.getGroupSchedulePagingData(groupId).cachedIn(viewModelScope)
}

sealed interface GroupDetailUiState {
    data object Loading : GroupDetailUiState
    data class Success(val groupDetail: GroupDetail) : GroupDetailUiState
    data class Error(val throwable: Throwable) : GroupDetailUiState
}