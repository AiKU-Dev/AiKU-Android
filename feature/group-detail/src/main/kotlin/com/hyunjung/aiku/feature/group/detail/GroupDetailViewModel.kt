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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    groupRepository: GroupRepository,
    scheduleRepository: ScheduleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = savedStateHandle.toRoute<AikuRoute.GroupDetailRoute>().groupId

    private val _selectedTab = MutableStateFlow(GroupDetailTab.MEMBER)
    val selectedTab: StateFlow<GroupDetailTab> = _selectedTab.asStateFlow()

    val groupDetailUiState: StateFlow<GroupDetailUiState> =
        groupRepository.getGroupDetail(groupId)
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> GroupDetailUiState.Error(
                        result.exception.message ?: "알 수 없는 오류가 발생 했어요"
                    )

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

    fun updateSelectedTab(selectedTab: GroupDetailTab) {
        _selectedTab.update { selectedTab }
    }
}

sealed interface GroupDetailUiState {
    data object Loading : GroupDetailUiState
    data class Success(val groupDetail: GroupDetail) : GroupDetailUiState
    data class Error(val message: String) : GroupDetailUiState
}

enum class GroupDetailTab(val label: String) {
    MEMBER("멤버"),
    SCHEDULE("약속"),
}