package com.example.realmdbapproach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realmdbapproach.schema.Address
import com.example.realmdbapproach.schema.Course
import com.example.realmdbapproach.schema.Student
import com.example.realmdbapproach.schema.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private val realm = MainActivity.realm
    val listCourse =
        realm.query<Course>().find()
            .asFlow()
            .map {
                it.list.toList()
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )
    init {
        createSampleEntries()
    }

    private fun createSampleEntries(){
        viewModelScope.launch{
            realm.write {
                val address1 = Address().apply {
                    country = "USA"
                    province = "CA"
                    street = "123 Main St"
                }
                val address2 = Address().apply {
                    country = "USA"
                    province = "NY"
                    street = "456 Elm St" 
                }
                val teacher1 = Teacher().apply {
                    name = "John"
                    address = address1
                }
                val teacher2 = Teacher().apply {
                    name = "Jane"
                    address = address2
                }
                val course1 = Course().apply {
                    name = "Math"
                    teacher = teacher1
                }
                val course2 = Course().apply {
                    name = "Science"
                    teacher = teacher2
                }
                teacher1.course = realmListOf(course1)
                teacher2.course = realmListOf(course2)

                val student1: Student = Student().apply{
                    name = "John"
                }
                val student2: Student = Student().apply{
                    name = "Jane"
                }
                course1.enrolledStudent.add(student1)
                course2.enrolledStudent.addAll(listOf(student2,student1))
                address1.teacher = teacher1
                address2.teacher = teacher2
                // Copy to the realm object
                // UpdatePolicy.ALL: copy all fields and update even is already exists
                // UpdatePolicy.REPLACE: copy all fields and replace if already exists

                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }
}