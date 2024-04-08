package com.example.room_and_paging_exmaple

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database( //데이터 베이스 정의
    entities = [DataTable::class], //테이블 지정
    version = 1, //데이터 베이스 버전
    exportSchema = false //스키마를 내보낼지 결정 (스키마란 데이터베이스의 구조와 제약조건에 관해 전반적인 명세를 기술한 것)
)
abstract class DataTableDatabases : RoomDatabase() {
    abstract fun dataTableDao(): DataTableDao

    companion object {
        @Volatile //여러 쓰레드에서 접근가능
        private var INSTANCE: DataTableDatabases? = null //데이터베이스 인스턴스를 저장하는 변수

        fun getDatabase(context: Context): DataTableDatabases {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance // 이미 Instance가 있을때 return
            synchronized(this)  { // Instance가 없을때 database 불러와서 return
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataTableDatabases::class.java,
                    "data_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}