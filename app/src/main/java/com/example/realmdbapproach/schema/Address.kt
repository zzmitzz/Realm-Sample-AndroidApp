package com.example.realmdbapproach.schema

import io.realm.kotlin.types.EmbeddedRealmObject

// Relation between Object:
// Teacher 1:1 Address
// Teacher 1:N Courses
// Student N:N Courses


// since Address has followed by Teacher, it's not necessary for address have an id field
// And the address will be turned to EmbeddedRealmObject
class Address: EmbeddedRealmObject{
    var country: String = ""
    var province: String = ""
    var street: String = ""
    var teacher: Teacher? = null
}

