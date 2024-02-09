package com.example.realmdbapproach

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdbapproach.schema.Address
import com.example.realmdbapproach.schema.Course
import com.example.realmdbapproach.schema.Student
import com.example.realmdbapproach.schema.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MainActivity : AppCompatActivity() {

//    private val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val config = RealmConfiguration.create(schema = setOf(Address::class, Teacher::class, Course::class, Student::class))
        realm = Realm.open(config)

        lifecycleScope.launchWhenStarted {
            Log.d("TAG", "onCreate: ${viewModel.listCourse.value.size}")
            viewModel.listCourse.collect {
                setRCV(it)
            }
        }

    }
    companion object {
        lateinit var realm: Realm
    }
    private fun setRCV(a: List<Course>){
        val rcv = findViewById<RecyclerView>(R.id.rcv)
        rcv.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter().apply {
                setListChange(a)
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}