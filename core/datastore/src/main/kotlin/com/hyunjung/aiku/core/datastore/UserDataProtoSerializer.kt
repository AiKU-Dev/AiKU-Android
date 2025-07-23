package com.hyunjung.aiku.core.datastore

import javax.inject.Inject

class UserDataProtoSerializer @Inject constructor() : ProtoSerializer<UserDataProto>(
    defaultInstance = UserDataProto.getDefaultInstance(),
    parse = UserDataProto::parseFrom
)