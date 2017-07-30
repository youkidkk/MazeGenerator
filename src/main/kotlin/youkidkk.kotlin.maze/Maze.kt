package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.enums.PointStatus

/**
 * 迷路クラス。
 *
 * @param width 幅
 * @param height 高さ
 */
class Maze(val width: Int, val height: Int) {

    /**
     * 地図。
     */
    private val map: MutableList<MutableList<PointStatus>> = mutableListOf()

    /**
     * イニシャライザ。
     */
    init {
        // マップを壁で初期化
        for (x in 0..width * 2) {
            val points = mutableListOf<PointStatus>()
            for (y in 0..height * 2) {
                points.add(PointStatus.WALL)
            }
            this.map.add(points)
        }
    }

    /**
     * 地点状態を取得する。
     *
     * @param x X座標
     * @param y Y座標
     */
    fun get(x: Int, y: Int) : PointStatus = map[x][y]

    /**
     * 地点状態を取得する。
     *
     * @param point 地点
     */
    fun get(point: Point) : PointStatus = this.get(point.x, point.y)

    /**
     * 地点状態を設定する。
     *
     * @param x X座標
     * @param y Y座標
     * @param state 地点状態
     */
    fun set(x: Int, y: Int, state: PointStatus) : Unit {
        map[x][y] = state
    }

    /**
     * 地点状態を設定する。
     *
     * @param point 地点
     * @param state 地点状態
     */
    fun set(point: Point, state: PointStatus) : Unit {
        this.set(point.x, point.y, state)
    }

    override fun toString(): String {
        val result = StringBuilder()
        for (y in 0..height) {
            for (x in 0..width) {
                result.append(if (get(x, y) == PointStatus.WALL) "■" else "　")
            }
            result.append(System.lineSeparator())
        }
        return result.toString()
    }

}