package com.example.realmdbapproach.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.bson.types.ObjectId

class Teacher : RealmObject {
    @PrimaryKey
    @Ignore
    var id: ObjectId = ObjectId()
    var name: String = ""
    var address: Address? = null
    var course: RealmList<Course> = realmListOf()
}