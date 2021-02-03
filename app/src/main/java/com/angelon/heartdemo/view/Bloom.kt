package com.angelon.heartdemo.view

import android.graphics.Canvas
import java.util.*

/**
 * created by kevin
 */
class Bloom {
    var point: Point
    private val radius: Int
    private val color: Int
    private val petalCount: Int
    private var petals: ArrayList<Petal>

    constructor(point: Point, radius: Int, color: Int, petalCount: Int) {
        this.point = point
        this.radius = radius
        this.color = color
        this.petalCount = petalCount
        petals = ArrayList<Petal>(petalCount)

        val angle = 360.0 / petalCount
        val startAngle = MyUtil.randomInt(0, 90)

        //注意在kotlin中,传统的for语句应该这么写
        for (index in 0..petalCount) {
            //随机产生第一个控制点的拉伸倍数
            val stretchA =
                MyUtil.random(Garden.Options.minPetalStrech, Garden.Options.maxPetalStrech)
            //随机产生第二个控制点的拉伸倍数
            val stretchB =
                MyUtil.random(Garden.Options.minPetalStrech, Garden.Options.maxPetalStrech)
            //计算每个花瓣的起始角度
            val beginAngle = startAngle + index * angle
            //随机产生每个花瓣的增长因子(即绽放速度)
            val growFactor = MyUtil.random(Garden.Options.minGrowFactor, Garden.Options.maxGrowFactor)
            petals.add(Petal(stretchA, stretchB, beginAngle, angle, color, growFactor))
        }
    }

    fun draw(canvas: Canvas) {
        for (petal in petals) {
            petal.render(point, radius, canvas)
        }
    }


}
