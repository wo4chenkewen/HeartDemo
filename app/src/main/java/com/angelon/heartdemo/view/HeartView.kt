package com.angelon.heartdemo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Thread.sleep
import kotlin.concurrent.thread


/**
 * @author kevin
 */
class HeartView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(
    context,
    attrs,
    defStyleAttr
), SurfaceHolder.Callback {

    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var garden: Garden
    private lateinit var backgroundPaint: Paint
    private lateinit var bm: Bitmap
    private var blooms: ArrayList<Bloom?> = ArrayList()
    private lateinit var canvas: Canvas

    private var w = 0
    private var h = 0
    private var heartRatio = 1
    private var offsetX = 0
    private var offsetY = 0
    private var isDrawing = false


    //可以将init直接放到init代码块中, java转过来的我有点小懵逼
    init {
        init()
    }

    private fun init() {
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
        garden = Garden()
        backgroundPaint = Paint()
        backgroundPaint.color = Color.rgb(0xff, 0xff, 0xe0)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        //do nothing
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        this.w = width
        this.h = height
        //我的手机宽度像素是1080, 发现参数设置为30比较合适,这里根据不同的宽度动态的调整参数

        heartRatio = w * 10 /MyUtil.getScreenWidth(context)
        offsetX = width / 2
        offsetY = height / 2 - 55
        bm = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
        canvas = Canvas(bm)
        drawOnNewThread()

    }

    private fun drawOnNewThread() {
        thread {
            if (isDrawing) {
                return@thread
            }

            isDrawing = true
            var angle = 10.0

            while (true) {
                val bloom = getBloom(angle)
                if (bloom != null) {
                    blooms.add(bloom)
                }

                if (angle > 30) {
                    break
                } else {
                    angle += 0.2
                }
                drawHeart()
                sleep(20)

            }
            isDrawing = false


        }.start()
    }

    /**
     * 绘制列表中所有的花朵
     * */
    private fun drawHeart() {
        canvas.drawRect(0F, 0F, w.toFloat(), h.toFloat(), backgroundPaint)
        blooms.forEach {
            it?.draw(canvas)
        }
        val lockCanvas = surfaceHolder.lockCanvas()
        lockCanvas.drawBitmap(bm, 0F, 0F, null)
        surfaceHolder.unlockCanvasAndPost(lockCanvas)
    }

    fun reDraw() {
        blooms.clear()
        drawOnNewThread()
    }

    private fun getBloom(angle: Double): Bloom? {
        val p = getHeartPoint(angle)
        var draw = true
        /**
         * 循环比较新的坐标位置是否可以创建花朵,
         * 为了防止花朵太密集
         * */
        //for (e: Type in list) {
        //   ...
        // }
        //在kotlin中, 这种写法注意一下

        for (b: Bloom? in blooms) {
            val bp = b?.point
            val distance = Math.sqrt(
                Math.pow(p.x.toDouble() - bp!!.x, 2.0) + Math.pow(
                    p.y.toDouble() - bp.y,
                    2.0
                )
            )
            if (distance < Garden.Options.maxBloomRadius * 1.5) {
                draw = false
                break
            }
        }

        //如果位置间距满足要求,就在该位置创建花朵并将花朵放入列表

        if (draw) {
            val bloom = garden.createRandomBloom(p.x, p.y)
            return bloom
        }
        return null
    }

    private fun getHeartPoint(angle: Double): Point {
        val t = angle / Math.PI
        val x = heartRatio * (16 * Math.pow(Math.sin(t), 3.0))
        val y = -heartRatio * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t))
        return Point((offsetX + x).toInt(), (offsetY + y).toInt())
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //do nothing
    }


}