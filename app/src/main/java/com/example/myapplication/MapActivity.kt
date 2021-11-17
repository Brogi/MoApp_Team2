package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val entireMap = findViewById<ImageView>(R.id.entire_map)
        val mapId = arrayOf(
            // 수도권
            R.id.s_ansan, R.id.s_anseong, R.id.s_anyang, R.id.s_bucheon, R.id.s_daebudo,
            R.id.s_dongducheon, R.id.s_ganghwa, R.id.s_gapyeong, R.id.s_gimpo, R.id.s_goyang,
            R.id.s_gunpo, R.id.s_guri, R.id.s_gwacheon, R.id.s_gwangju, R.id.s_gwangmyeong,
            R.id.s_gyodongdo, R.id.s_hanam, R.id.s_hwaseong, R.id.s_icheon, R.id.s_incheon,
            R.id.s_namyangju, R.id.s_osan, R.id.s_paju, R.id.s_pocheon, R.id.s_pyeongtaek,
            R.id.s_seongmodo, R.id.s_seongnam, R.id.s_seoul, R.id.s_siheung, R.id.s_suwon,
            R.id.s_uijeongbu, R.id.s_uiwang, R.id.s_yangju, R.id.s_yangpyeong, R.id.s_yeoju,
            R.id.s_yeoncheon, R.id.s_yeongjongdo, R.id.s_yongin,
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
        val mapImage = arrayOfNulls<ImageView>(167) // 수도권 39, 전북 14, 전남 22, 충청도 29, 강원 18, 경북 25, 경남 20

        for (i in mapId.indices) {
            mapImage[i] = findViewById<ImageView>(mapId[i])
            mapImage[i]!!.setOnClickListener {
                mapImage[i]!!.setColorFilter(Color.parseColor("#9CD7FF"))
                var intent = Intent(applicationContext,StoriesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}