package com.example.room_and_paging_exmaple

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataTableDao {

    // 모든 데이터를 불러오는 쿼리문
    @Query("SELECT * FROM data_table")
    fun getAllData(): List<DataTable>

    // 데이터를 페이징하여 5개씩 불러오기
    @Query("SELECT * FROM data_table ORDER BY num ASC LIMIT 5 OFFSET (:page-1)*5")
    fun getList(page:Int): List<DataTable>

    // 데이터베이스에 데이터 삽입
    @Insert
    suspend fun insertData(dataTable: DataTable)
}



