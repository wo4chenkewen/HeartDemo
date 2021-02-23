package com.angelon.heartdemo.view


/**
 * created by kevin
 */
class Garden {
    class Options {
        companion object {

            //用于控制产生随机花瓣个数的范围
            const val minPetalCount = 8
            const val maxPetalCount = 15

            //用于控制产生延长线倍数范围
            const val minPetalStretch = 2.0
            const val maxPetalStretch = 3.5

            //用于控制产生随机增长因子的范围,增长因子决定花瓣绽放速度
            const val minGrowFactor = 1.0
            const val maxGrowFactor = 1.1

            //用于控制花瓣产生花朵半径随机数的范围
            const val minBloomRadius = 8
            const val maxBloomRadius = 10

            //用于产生随机颜色
            const val minRedColor = 128
            const val maxRedColor = 255
            const val minGreenColor = 0
            const val maxGreenColor = 128
            const val minBlueColor = 0
            const val maxBlueColor = 128

            //花瓣的透明度
            const val opacity = 50

        }
    }

    //创建一个随机的花朵
    fun createRandomBloom(x: Int, y: Int): Bloom {
        //创建一个随机花朵的半径
        val radius = MyUtil.randomInt(Options.minBloomRadius, Options.maxBloomRadius)
        //创建一个随机花朵的颜色
        val color = MyUtil.randomRgba(
            Options.minRedColor, Options.minGreenColor, Options.minBlueColor,
            Options.maxRedColor, Options.maxGreenColor, Options.maxBlueColor, Options.opacity
        )
        //创建随机的花朵中的花瓣个数
        val petalCount = MyUtil.randomInt(Options.minPetalCount, Options.maxPetalCount)

        return createBloom(x, y, radius, color, petalCount)
    }

    private fun createBloom(x: Int, y: Int, radius: Int, color: Int, petalCount: Int): Bloom {
        return Bloom(Point(x, y), radius, color, petalCount)
    }


}
