package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.room.Room
import com.example.myapplication.model.Map

class MapActivity : AppCompatActivity() {
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private lateinit var gestureDetector: GestureDetector
    private lateinit var db: AppDatabase
    private var scaleFactor = 1.0f
    private val constraintLayout: ConstraintLayout by lazy {
        findViewById(R.id.constraintLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        gestureDetector = GestureDetector(this, GestureListener())
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        val mapId = arrayOf(
            // 수도권
            R.id.s_ansan, R.id.s_anseong, R.id.s_anyang, R.id.s_bucheon, R.id.s_daebudo,
            R.id.s_dongducheon, R.id.s_ganghwa, R.id.s_gapyeong, R.id.s_gimpo, R.id.s_goyang,
            R.id.s_gunpo, R.id.s_guri, R.id.s_gwacheon, R.id.s_gwangju, R.id.s_gwangmyeong,
            R.id.s_gyodongdo, R.id.s_hanam, R.id.s_hwaseong, R.id.s_icheon, R.id.s_incheon,
            R.id.s_namyangju, R.id.s_osan, R.id.s_paju, R.id.s_pocheon, R.id.s_pyeongtaek,
            R.id.s_seongmodo, R.id.s_seongnam, R.id.s_seoul, R.id.s_siheung, R.id.s_suwon,
            R.id.s_uijeongbu, R.id.s_uiwang, R.id.s_yangju, R.id.s_yangpyeong, R.id.s_yeoju,
            R.id.s_yeoncheon, R.id.s_yeongheungdo, R.id.s_yeongjongdo, R.id.s_yongin,
            // 전북
            R.id.jb_buan, R.id.jb_gimje, R.id.jb_gochang, R.id.jb_gunsan, R.id.jb_iksan,
            R.id.jb_imsil, R.id.jb_jangsu, R.id.jb_jeongeup, R.id.jb_jeonju, R.id.jb_jinan,
            R.id.jb_muju, R.id.jb_namwon, R.id.jb_sunchang, R.id.jb_wanju,
            // 전남 및 제주
            R.id.jn_bosung, R.id.jn_changheung, R.id.jn_damyang, R.id.jn_gangjin, R.id.jn_goheung,
            R.id.jn_goksung, R.id.jn_gurye, R.id.jn_gwangju, R.id.jn_gwangyang, R.id.jn_haenam,
            R.id.jn_hampyeong, R.id.jn_hwasoon, R.id.jn_jangseong, R.id.jn_jindo, R.id.jn_mokpo,
            R.id.jn_muan, R.id.jn_najoo, R.id.jn_suncheon, R.id.jn_yeongam, R.id.jn_yeongkwang,
            R.id.jn_yeosu, R.id.jeju,
            // 충청도
            R.id.c_asan, R.id.c_boeun, R.id.c_boryeong, R.id.c_buyeo, R.id.c_cheonan,
            R.id.c_cheongju, R.id.c_cheongyang, R.id.c_chungju, R.id.c_daejeon, R.id.c_dangjin,
            R.id.c_danyang, R.id.c_eumseong, R.id.c_geumsan, R.id.c_goesan, R.id.c_gongju,
            R.id.c_gyeryong, R.id.c_hongseong, R.id.c_jecheon, R.id.c_jeungpyeong, R.id.c_jincheon,
            R.id.c_nonsan, R.id.c_okcheon, R.id.c_sejong, R.id.c_seocheon, R.id.c_seosan,
            R.id.c_taean, R.id.c_taean_below, R.id.c_yeongdong, R.id.c_yesan,
            // 강원
            R.id.kw_cheolwon, R.id.kw_chuncheon, R.id.kw_donghae, R.id.kw_gangneung, R.id.kw_hoengseong,
            R.id.kw_hongcheon, R.id.kw_hwacheon, R.id.kw_injae, R.id.kw_jeongseon, R.id.kw_koseong,
            R.id.kw_pyeongchang, R.id.kw_samcheok, R.id.kw_sokcho, R.id.kw_taebaek, R.id.kw_wonjoo,
            R.id.kw_yanggu, R.id.kw_yangyang, R.id.kw_yeongwol,
            // 경북
            R.id.kb_andong, R.id.kb_bonghwa, R.id.kb_cheongdo, R.id.kb_cheongsong, R.id.kb_chilgok,
            R.id.kb_daegu, R.id.kb_dokdo, R.id.kb_gimcheon, R.id.kb_goryeong, R.id.kb_gumi,
            R.id.kb_gunwi, R.id.kb_gyeongju, R.id.kb_gyeongsan, R.id.kb_mungyeong, R.id.kb_pohang,
            R.id.kb_sangju, R.id.kb_seongju, R.id.kb_uiseong, R.id.kb_uljin, R.id.kb_ulleungdo,
            R.id.kb_yecheon, R.id.kb_yeongcheon, R.id.kb_yeongdeok, R.id.kb_yeongju, R.id.kb_yeongyang,
            // 경남
            R.id.kn_busan, R.id.kn_changnyeong, R.id.kn_changwon, R.id.kn_geochang, R.id.kn_geoje,
            R.id.kn_gimhae, R.id.kn_goseong, R.id.kn_hadong, R.id.kn_haman, R.id.kn_hamyang,
            R.id.kn_hapcheon, R.id.kn_jinju, R.id.kn_miryang, R.id.kn_namhae, R.id.kn_sacheon,
            R.id.kn_sancheong, R.id.kn_tongyeong, R.id.kn_uiryeong, R.id.kn_ulsan, R.id.kn_yangsan
        )
        val mapName = arrayOf(
            // 수도권
            "안산", "안성", "안양", "부천", "대부도",
            "동두천", "강화", "가평", "김포", "고양",
            "군포", "구리", "과천", "광주", "광명",
            "교동도", "하남", "화성", "이천", "인천",
            "남양주", "오산", "파주", "포천", "평택",
            "성모도", "성남", "서울", "시흥", "수원",
            "의정부", "의왕", "양주", "양평", "여주",
            "연천", "영흥도", "영종도", "용인",
            // 전북
            "부안", "김제", "고창", "군산", "익산",
            "임실", "장수", "정읍", "전주", "진안",
            "무주", "남원", "순창", "완주",
            // 전남 및 제주
            "보성", "장흥", "담양", "강진", "고흥",
            "곡성", "구례", "광주", "광양", "해남",
            "함평", "화순", "장성", "진도", "목포",
            "무안", "나주", "순천", "영암", "영광",
            "여수", "제주",
            // 충청도
            "아산", "보은", "보령", "부여", "천안",
            "청주", "청양", "충주", "대전", "당진",
            "단양", "음성", "금산", "괴산", "공주",
            "계룡", "홍성", "제천", "증평", "진천",
            "논산", "옥천", "세종", "서천", "서산",
            "태안", "태안 아래", "영동", "예산",
            // 강원
            "철원", "춘천", "동해", "강릉", "횡성",
            "홍천", "화천", "인제", "정선", "고성",
            "평창", "삼척", "속초", "태백", "원주",
            "양구", "양양", "영월",
            // 경북
            "안동", "봉화", "청도", "청송", "칠곡",
            "대구", "독도", "김천", "고령", "구미",
            "군위", "경주", "경산", "문경", "포항",
            "상주", "성주", "의성", "울진", "울릉도",
            "예천", "영천", "영덕", "영주", "영양",
            // 경남
            "부산", "창녕", "창원", "거창", "거제",
            "김해", "고성", "하동", "함안", "함양",
            "합천", "진주", "밀양", "남해", "사천",
            "산청", "통영", "의량", "울산", "양산"
        )
        val mapImage = arrayOfNulls<ImageView>(167) // 수도권 39, 전북 14, 전남 22, 충청도 29, 강원 18, 경북 25, 경남 20

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mapDB"
        ).allowMainThreadQueries().build()

        val count = db.mapDao().getAllCount()
        //Toast.makeText(this, count.toString(), Toast.LENGTH_SHORT).show()

        //db.mapDao().deleteAll()

        for (i in mapId.indices) {
            mapImage[i] = findViewById<ImageView>(mapId[i])

            if(count == 0)
                saveMapInformation(mapId[i], mapName[i], "#FFFFFF")
            else {
                val mapColor = db.mapDao().getAllById(mapId[i])[0].color
                mapImage[i]!!.setColorFilter(Color.parseColor(mapColor))
            }
            mapImage[i]!!.setOnClickListener {
                var intent = Intent(applicationContext, StoriesActivity::class.java)
                intent.putExtra("Local", mapId[i])
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveMapInformation(mapId: Int, mapName: String, mapColor: String) {
        Thread {
            db.mapDao().insertMap(Map(mapId, mapName, mapColor))
        }.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector!!.onTouchEvent(event)
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        mScaleGestureDetector!!.onTouchEvent(ev)
        gestureDetector.onTouchEvent(ev)
        return gestureDetector.onTouchEvent(ev)
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= mScaleGestureDetector!!.scaleFactor

            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 4.0f))

            constraintLayout.scaleX = scaleFactor
            constraintLayout.scaleY = scaleFactor
            return true
        }
    }

    inner class GestureListener: GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return true
        }
    }
}