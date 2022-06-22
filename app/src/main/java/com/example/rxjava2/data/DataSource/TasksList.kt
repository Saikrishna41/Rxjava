package com.example.rxjava2.data.DataSource

import com.example.rxjava2.data.entities.Tasks

class TasksList {
    companion object {
        fun createLists(): List<Tasks> {
            val taskList = mutableListOf<Tasks>()
            taskList.add(Tasks("walk the dog",2,true))
            taskList.add(Tasks("walk the dog",4,false))
            taskList.add(Tasks("do the dishes",1,true))
            taskList.add(Tasks("walk the dog",3,false))
            return taskList
        }
    }
}