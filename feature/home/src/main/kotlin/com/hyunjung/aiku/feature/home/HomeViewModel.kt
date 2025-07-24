package com.hyunjung.aiku.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyunjung.aiku.core.domain.repository.GroupRepository
import com.hyunjung.aiku.core.domain.repository.ScheduleRepository
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.model.schedule.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    scheduleRepository: ScheduleRepository,
    private val groupRepository: GroupRepository,
) : ViewModel() {

    val groupSummaryPagingData: Flow<PagingData<GroupSummary>> =
        groupRepository.getGroupSummaryPagingData()
            .cachedIn(viewModelScope)

    val schedulePagingData: Flow<PagingData<Schedule>> =
        scheduleRepository.getSchedulePagingData()
            .cachedIn(viewModelScope)

    // todo : replace with actual user nickname fetching logic
    val userNickName: StateFlow<String> = MutableStateFlow("Nickname")

    fun createGroup(name: String) {
        viewModelScope.launch { groupRepository.createGroup(name) }
    }

}