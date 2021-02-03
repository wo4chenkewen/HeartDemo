/*
 * Copyright (C), kevin 2015-2021, Angelon Tech Ltd
 * FileName: Paint
 * Author: kevin
 * Date: 2021/2/3 13:12
 * Description:
 * History:
 * <author>            <time>           <version>             <desc>
 */
package com.angelon.heartdemo.view


/**
 * @author kevin
 */
class Point(x: Int, y: Int) {
    var x: Int
    var y: Int

    //旋转
    fun rotate(theta: Double): Point {
        val x = x
        val y = y
        this.x = (Math.cos(theta) * x - Math.sin(theta) * y).toInt()
        this.y = (Math.sin(theta) * x + Math.cos(theta) * y).toInt()
        return this
    }

    //乘以一个常数
    fun mult(f: Double): Point {
        x *= f.toInt()
        y *= f.toInt()
        return this
    }

    //复制
    fun clone(): Point {
        return Point(x, y)
    }

    //该点与圆心距离
    fun length(): Double {
        return Math.sqrt((x * x + y * y).toDouble())
    }

    //向量相减
    fun subtract(p: Point): Point {
        x -= p.x
        y -= p.y
        return this
    }

    //向量相加
    fun add(p: Point): Point {
        x += p.x
        y += p.y
        return this
    }

    operator fun set(x: Int, y: Int): Point {
        this.x = x
        this.y = y
        return this
    }

    init {
        this.x = x
        this.y = y
    }
}