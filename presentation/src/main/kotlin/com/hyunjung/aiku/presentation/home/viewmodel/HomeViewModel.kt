package com.hyunjung.aiku.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.aiku.core.data.model.GroupOverview
import com.hyunjung.aiku.core.data.model.Schedule
import com.hyunjung.aiku.core.data.repository.GroupRepository
import com.hyunjung.aiku.core.data.repository.ScheduleRepository
import com.hyunjung.aiku.core.network.result.Result
import com.hyunjung.aiku.core.network.result.asResult
import com.hyunjung.aiku.presentation.home.model.HomeGroupUiState
import com.hyunjung.aiku.presentation.home.model.HomeScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private var isLastSchedulePage: Boolean = false
    private var isLastGroupPage: Boolean = false
    private val currentSchedulePage: MutableStateFlow<Int> = MutableStateFlow(1)
    private val currentGroupPage: MutableStateFlow<Int> = MutableStateFlow(1)
    private val _todaySchedules: MutableStateFlow<List<Schedule>> = MutableStateFlow(emptyList())
    val todaySchedules: StateFlow<List<Schedule>> = _todaySchedules

    private val _groups: MutableStateFlow<List<GroupOverview>> = MutableStateFlow(emptyList())
    val groups: StateFlow<List<GroupOverview>> = _groups

    // todo : replace with actual user nickname fetching logic
    val userNickName: StateFlow<String> = MutableStateFlow("Nickname")

    private val _isCreateGroupDialogVisible = MutableStateFlow(false)
    val isCreateGroupDialogVisible: StateFlow<Boolean> = _isCreateGroupDialogVisible

    val scheduleUiState: StateFlow<HomeScheduleUiState> =
        currentSchedulePage
            .flatMapConcat { page ->
                scheduleRepository.getSchedules(page)
            }
            .asResult()
            .onEach { result ->
                if (result is Result.Success) {
                    if (result.data.isEmpty()) {
                        isLastSchedulePage = true
                    }
                    _todaySchedules.update { prev -> (prev + result.data) }
                }
            }
            .map { result ->
                when (result) {
                    is Result.Success -> HomeScheduleUiState.Idle
                    is Result.Loading -> HomeScheduleUiState.Loading
                    is Result.Error -> HomeScheduleUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeScheduleUiState.Loading
            )

    val groupUiState: StateFlow<HomeGroupUiState> =
        currentGroupPage.flatMapConcat { page ->
            groupRepository.getGroups(page)
        }
            .asResult()
            .onEach { result ->
                if (result is Result.Success) {
                    if (result.data.isEmpty()) {
                        isLastGroupPage = true
                    }
                    _groups.update { prev -> prev + result.data }
                }
            }
            .map { result ->
                when (result) {
                    is Result.Success -> HomeGroupUiState.Idle
                    is Result.Loading -> HomeGroupUiState.Loading
                    is Result.Error -> HomeGroupUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeGroupUiState.Loading
            )

    fun loadNextSchedulePage() {
        if (!isLastSchedulePage) {
            currentSchedulePage.update { it + 1 }
        }
    }

    fun loadNextGroupPage() {
        if (!isLastGroupPage) {
            currentGroupPage.update { it + 1 }
        }
    }

    fun openCreateGroupDialog() {
        _isCreateGroupDialogVisible.value = true
    }

    fun dismissCreateGroupDialog() {
        _isCreateGroupDialogVisible.value = false
    }

    fun createGroup(name: String) {
        viewModelScope.launch {
            val result = groupRepository.setGroup(name)
            if (result.isSuccess) {
                _groups.update { emptyList() }
                currentGroupPage.value = 1
                isLastGroupPage = false
                dismissCreateGroupDialog()
            } else {
                // TODO: 에러 처리 로직
            }
        }
    }

}