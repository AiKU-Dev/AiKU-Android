package com.hyunjung.aiku.core.data.datasource

import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupOverview

interface GroupDataSource {
    suspend fun getGroups(page:Int):List<GroupOverview>
    suspend fun getGroup(id:Long): GroupDetail
    suspend fun setGroup(name:String)
}