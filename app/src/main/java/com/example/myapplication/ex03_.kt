package com.example.myapplication

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class ex03_ : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex03_)
        //버튼 이벤트
        var btn=findViewById<Button>(R.id.rnkback)
        var txt=findViewById<TextView>(R.id.rnkname)


        //인텐트 연결
        var intent=intent
        var rname=intent.getStringExtra("RnkName")
        var rtimer=intent.getStringExtra("Timer")
        var rcount=intent.getIntExtra("Count",0)
        var rnklist=intent.getStringArrayListExtra("RnkList")

        //어뎁터에 넣을 리스트
        var list=ArrayList<String>(rnklist)
        //리스트,어뎁터 연결
        var rnkList=findViewById<ListView>(R.id.rnklist)
        var adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        rnkList.adapter=adapter
        adapter.notifyDataSetChanged()

        txt.text="최근기록    이름: ${rname}  시도 수: ${rcount} 걸린 시간: ${rtimer}"


        btn.setOnClickListener {
            finish()    //엑티비티 종료
        }
    }

}