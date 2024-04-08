package com.example.room_and_paging_exmaple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var dataTableDatabase: DataTableDatabases
    private lateinit var editText: EditText
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 생성한 DataTableDatabases.kt와 연결
        dataTableDatabase = DataTableDatabases.getDatabase(this)
        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)

        val saveButton: Button = findViewById(R.id.saveButton)
        val showButton: Button = findViewById(R.id.showButton)

        // saveButton클릭 시 이벤트
        saveButton.setOnClickListener {
            val inputText = editText.text.toString()
            val data = DataTable(inputText) // DataTable 객체 만들어주기
            GlobalScope.launch(Dispatchers.IO) { // Coroutine 사용
                dataTableDatabase.dataTableDao().insertData(data) // 데이터베이스에 데이터 추가
            }
        }

        // (paging X) 데이터 불러오기(모든 데이터를 불러온다)
        showButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) { // Coroutine 사용
                val allData = dataTableDatabase.dataTableDao().getAllData() // data를 불러와 allData 초기화
                val dataTextList = StringBuilder() // StringBuilder 객체 생성
                for (data in allData) {
                    dataTextList.append(data.text).append("\n") // 생성한 StringBuilder객체에 data + 줄 바꿈 추가
                }
                val textToShow = dataTextList.toString() // textView에 값을 넣어주기 위한 String 변수에 data 초기화
                withContext(Dispatchers.Main) {
                    textView.text = textToShow // textView에 보여주기
                }
            }
        }

        var page = 0 //처음 페이지 번호 지정
        // (paging O) 데이터 불러오기(내가 지정한 크기만큼 불러온다)
        showButton.setOnClickListener {
            page++ // 클릭할 때마다 다음 page불러오기
            lifecycleScope.launch(Dispatchers.IO) { // Coroutine 사용
                val allData = dataTableDatabase.dataTableDao().getList(page) // data를 불러와 allData 초기화
                val dataTextList = StringBuilder() // StringBuilder 객체 생성
                for (data in allData) {
                    dataTextList.append(data.text).append("\n") // 생성한 StringBuilder객체에 data + 줄 바꿈 추가
                }
                val textToShow = dataTextList.toString() // textView에 값을 넣어주기 위한 String 변수에 data 초기화
                withContext(Dispatchers.Main) {
                    textView.text = textToShow // textView에 보여주기
                }
            }
        }


    }
}
