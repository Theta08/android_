package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer


//시간 체크
//DB연결?해서 시간 오름차순, 틀린수 내림 차순
// 정답시 다이어로그  이름 입력,걸린 시간 보여주기, 확인 누르시 랭킹에 등록
//랭킹 누르면 랭킹 보여주기
class MainActivity : AppCompatActivity() {

    //타이머 변수
    var timer=0
    var mill=0
    var sec=0
    var t: Timer?=null
    //다이어로그 이름,시간
    var rnkName:String?=null
    var rnktimer:String?=null
    var cnt=0

    private lateinit var counttxt:TextView

    var rnklist=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex03)
        title="자유주제"

        //바인딩 확인,숫자입력,틀린수 체크.랭킹
        var btn_str=findViewById<Button>(R.id.rand_btn0)
        var btn_chick=findViewById<Button>(R.id.rand_btn1)
        var edt=findViewById<EditText>(R.id.rand_num)
        var btngo=findViewById<Button>(R.id.rand_rnkbtn)
        var title=findViewById<TextView>(R.id.ex_title)

        counttxt=findViewById<TextView>(R.id.rand_count)
        //rnad 값
        var test=findViewById<TextView>(R.id.rand_test)

        //어뎁터에 넣을 리스트
        var list=ArrayList<String>()

        //리스트,어뎁터 연결
        var listview=findViewById<ListView>(R.id.rand_List)
        var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        listview.adapter=adapter



        var rand=0  //랜덤 난수 변수
        var count=0//틀린 수 체크

        //난수 생성
       btn_str.setOnClickListener {
           //타이머 초기화
           if(timer!=0) pause()
           rand=(Math.random()*25).toInt()+1//랜덤 난수 생성 1~25
           //test.text=rand.toString()         //난수값 알아봅기위해서
           edt.setText("")
           //list.clear()                     //리스트 초기화
           list.removeAll(list)
           count=0
           counttxt.setText(count.toString())
           title.setTextColor(Color.RED)
           start()  //타이머 실행

        }
        //정답 확인
        btn_chick.setOnClickListener {
            count++
            counttxt.text=count.toString()
            if(edt.text.toString()!=""){
                if(rand==edt.text.toString().toInt()){
                    pause()     //타이머 종료
                    Toast.makeText(this,"정답입니다.",Toast.LENGTH_SHORT).show()
                    title.setTextColor(Color.BLACK)
                    counttxt.setText(count.toString())
                    edt.setText("")
                    cnt=count               //시도 수
                    count=0                 //시도 수 초기화
                    dlg()                   //다이어리 생성
                }
                else{
                    list.add("${edt.text}") //오답 리스트에 넣기
                    adapter.notifyDataSetChanged() //세로 고침
                    //힌트 시도한 숫자가 2이상이면
                    if(count>2)
                        if(edt.text.toString().toInt()>rand){
                            Toast.makeText(this,"입력한 숫자보다 적습니다.",Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(this,"입력한 숫자보다 큽니다.",Toast.LENGTH_SHORT).show()
                    edt.text=null
               }
            }
            else{
                Toast.makeText(this,"값을 입력하세요.",Toast.LENGTH_SHORT).show()
            }
        }

        //랭킹 보기
        btngo.setOnClickListener {
            intent()    //ex03_으로 가기
            pause()
        }
    }
    //다이얼 로그
    private fun dlg(){
        //준비작업
        var toastview: View = View.inflate(this,R.layout.rankinput,null)
        //다이얼로그 생성
        var dlg= AlertDialog.Builder(this)
        dlg.setTitle("축하합니다")
        var name=toastview.findViewById<EditText>(R.id.rank_name)
        var dtxt=toastview.findViewById<TextView>(R.id.rnak_timer)
        dlg.setView(toastview)
        rnktimer="${sec}:${mill}"
        dtxt.text="걸린 시간은:${rnktimer}"
        // rnkName=name.text.toString()

        //확인시 이벤트발생 여기에 intent로 값을 넘겨 줘서 rank.xml 리스트에 넣기
        dlg.setPositiveButton("확인",DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this,"확인버튼.",Toast.LENGTH_SHORT).show()
            //확인 버튼 클릭시 이름,걸린 시간 넣기
            rnkName=name.text.toString()
            counttxt.setText("0")
            //실험
            rnklist.add("이름:${rnkName}  시도 수:${cnt} 시간:${rnktimer}")
        })
        dlg.setNegativeButton("취소",null)
        dlg.show()
    }
    
    //타이머 시작
    private fun start() {
        t=timer(period = 10) {
            timer++ //0.01초마다 time 1식 증가
            mill=timer%100
            sec=timer/100
        }

    }
    //타이머 종료
    private fun pause(){
        t?.cancel()
        timer=0
    }
    //ex03_으로 가기
    private fun intent(){
        var intent=Intent(this,ex03_::class.java)
        intent.putExtra("RnkName",rnkName)  //이름
        intent.putExtra("Count",cnt)        //횟수수
        intent.putExtra("Timer",rnktimer)   //시간
        intent.putExtra("RnkList",rnklist)  //리스트
        startActivity(intent)
    }

}

