package youkidkk.kotlin.maze

/**
 * 地点データ。
 *
 * @param x X座標
 * @param y Y座標
 */
data class Point(var x: Int, var y: Int) {

    /**
     * +演算子オーバーロード。
     */
    operator fun plus(point: Point) : Point = Point(x + point.x, y + point.y)

    /**
     * *演算子オーバーロード。
     */
    operator fun times(i: Int) : Point = Point(x * i, y * i)

}
