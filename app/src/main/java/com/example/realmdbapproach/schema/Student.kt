package com.example.realmdbapproach.schema

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.bson.types.ObjectId

class Student: RealmObject {
    @PrimaryKey
    @Ignore
    var id: ObjectId = ObjectId()
    var name: String = ""
    val enrolledCourse: RealmResults<Course> by backlinks(Course::enrolledStudent)
}
