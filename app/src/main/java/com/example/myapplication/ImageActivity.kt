package com.example.myapplication


import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import java.util.ArrayList


class ImageActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var adapter: Adapter? = null
    var models: ArrayList<Model>? = null
    var colors: Array<Int>? = null
    var argbEvaluator = ArgbEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        models = ArrayList<Model>()
        models!!.add(Model(R.drawable.sun, "2019-11-20", "#Busan #Haeundae #Friendship"))
        models!!.add(Model(R.drawable.test, "2019-11-20", "#Aespa #Karina #Winter"))
        models!!.add(Model(R.drawable.test2, "2019-11-20", "#Brogi #Face Park #Jino Ryu"))
        models!!.add(Model(R.drawable.test3, "2019-11-20", "#Moapp #Sibal"))

        adapter = Adapter(models!!, this)

        viewPager = findViewById(R.id.viewPager)
        viewPager!!.adapter = adapter
        viewPager!!.setPadding(130, 0, 130, 0)

        val colors_temp = arrayOf<Int>(
            getResources().getColor(R.color.color1),
            getResources().getColor(R.color.color2),
            getResources().getColor(R.color.color3),
            getResources().getColor(R.color.color4)
        )

        colors = colors_temp

        viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position < adapter!!.getCount() - 1 && position < colors!!.size - 1) {
                    viewPager!!.setBackgroundColor(
                        argbEvaluator.evaluate(
                            positionOffset,
                            colors!![position],
                            colors!![position + 1]
                        ) as Int
                    )
                } else {
                    viewPager!!.setBackgroundColor(colors!![colors!!.size - 1])
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}
