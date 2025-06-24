package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SchedulesResultResponse(
    val page: Int,
    val runSchedule: Int,
    val waitSchedule: Int,
    val data: List<ScheduleResponse>
)

@Serializable
data class GroupSchedulesResultResponse(
    val page: Int,
    val groupId: Long,
    val runSchedule: Int,
    val waitSchedule: Int,
    val data: List<GroupScheduleResponse>
)