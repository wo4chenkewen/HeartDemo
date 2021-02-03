package com.angelon.heartdemo.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.angelon.heartdemo.view.MyUtil.degrad

@Suppress("ConvertSecondaryConstructorToPrimary")
class Petal {

    //第一个控制点延长线倍数
    private var stretchA = 0.0

    //第二个控制点延长线倍数
    private var stretchB =  0.0

    //起始旋转角，用于确定第一个端点
    private var startAngle =  0.0

    //两条线之间夹角，由起始旋转角和夹角可以确定第二个端点
    private var angle =  0.0

    //花芯的半径
    private var radius = 2

    //增长因子，花瓣是有开放的动画效果，这个参数决定花瓣展开速度
    private var growFactor =  0.0

    //花瓣颜色
    private var color = 0

    //花瓣是否绽放完成
    private var isFinished = false

    private var path: Path = Path() //用于保存三次贝塞尔曲线

    private val paint: Paint = Paint() //画笔

    //构造函数，由花朵类调用
    constructor(
        stretchA: Double,
        stretchB: Double,
        startAngle: Double,
        angle: Double,
        color: Int,
        growFactor: Double
    ) {
        this.stretchA = stretchA
        this.stretchB = stretchB
        this.startAngle = startAngle
        this.angle = angle
        this.color = color
        this.growFactor = growFactor
        paint.setColor(color)
    }

    //用于渲染花瓣，通过不断更改半径使得花瓣越来越大
    fun render(p: Point, radius: Int, canvas: Canvas) {
        if (this.radius <= radius) {
            this.radius += growFactor.toInt() // / 10;
        } else {
            isFinished = true
        }
        draw(p, canvas)
    }

    //绘制花瓣，参数p是花芯的圆心的坐标
    private fun draw(p: Point, canvas: Canvas) {
        if (!isFinished) {
            path = Path()
            //将向量（0，radius）旋转起始角度，第一个控制点根据这个旋转后的向量计算
            val t: Point = Point(0, radius).rotate(
                degrad(
                    startAngle
                )
            )
            //第一个端点，为了保证圆心不会随着radius增大而变大这里固定为3
            val v1: Point = Point(0, 3).rotate(
                degrad(
                    startAngle
                )
            )
            //第二个端点
            val v2: Point = t.clone().rotate(degrad(angle))
            //延长线，分别确定两个控制点
            val v3: Point = t.clone().mult(stretchA)
            val v4: Point = v2.clone().mult(stretchB)
            //由于圆心在p点，因此，每个点要加圆心坐标点
            v1.add(p)
            v2.add(p)
            v3.add(p)
            v4.add(p)
            path.moveTo(v1.x.toFloat(), v1.y.toFloat())
            //参数分别是：第一个控制点，第二个控制点，终点
            path.cubicTo(v3.x.toFloat(), v3.y.toFloat(), v4.x.toFloat(), v4.y.toFloat(),
                v2.x.toFloat(), v2.y.toFloat()
            )
        }
        canvas.drawPath(path, paint)
    }

}
