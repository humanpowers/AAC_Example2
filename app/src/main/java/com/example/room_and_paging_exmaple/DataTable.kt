package com.example.room_and_paging_exmaple

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "data_table" // 데이터베이스 table 이름
)
data class DataTable(
    var text: String? = "" // 테이블의 컬럼 추가
) {
    @PrimaryKey(autoGenerate = true) // primaryKey 추가 autoGenerate: 추가할 때마다 값 1씩 증가해주는 코드
    var num: Int? = null
}