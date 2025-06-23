package com.hyunjung.aiku.core.network.demo

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.data.dto.GroupDetailResult
import com.hyunjung.aiku.core.data.dto.GroupOverviewListResult
import com.hyunjung.aiku.core.data.dto.toGroupDetail
import com.hyunjung.aiku.core.data.dto.toGroupOverview
import com.hyunjung.aiku.core.data.model.GroupDetail
import com.hyunjung.aiku.core.data.model.GroupOverview
import com.hyunjung.aiku.core.network.AikuDispatchers.IO
import com.hyunjung.aiku.core.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.BufferedReader
import javax.inject.Inject

class DemoGroupDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: DemoAssetManager = JvmUnitTestDemoAssetManager,
) : GroupDataSource {
    override suspend fun getGroups(page: Int): List<GroupOverview> {
        val result: GroupOverviewListResult = getDataFromJsonFile(GROUPS_ASSET)
        return result.data.map { it.toGroupOverview() }
    }

    override suspend fun getGroup(id: Long): GroupDetail {
        val result: GroupDetailResult = getDataFromJsonFile(GROUP_DETAIL_ASSET)
        return result.toGroupDetail()
    }

    override suspend fun setGroup(name: String) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getDataFromJsonFile(fileName: String): T =
        withContext(ioDispatcher) {
            assets.open(fileName).use { inputStream ->
                if (SDK_INT <= M) {
                    /**
                     * On API 23 (M) and below we must use a workaround to avoid an exception being
                     * thrown during deserialization. See:
                     * https://github.com/Kotlin/kotlinx.serialization/issues/2457#issuecomment-1786923342
                     */
                    inputStream.bufferedReader().use(BufferedReader::readText)
                        .let(networkJson::decodeFromString)
                } else {
                    networkJson.decodeFromStream(inputStream)
                }
            }
        }

    companion object {
        private const val GROUPS_ASSET = "groups.json"
        private const val GROUP_DETAIL_ASSET = "group_detail_1.json"
    }
}