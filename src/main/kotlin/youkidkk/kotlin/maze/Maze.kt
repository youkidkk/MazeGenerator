package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.enums.Direction
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

    /**
     * 地点が範囲内であるか判定する。
     *
     * @return 判定結果
     */
    fun isInside(point: Point) : Boolean {
        return point.x in 1..width && point.y in 1..height
    }

    /**
     * 地点から移動可能な方向のリストを取得する。
     *
     * @param point 地点
     * @return 移動可能な方向のリスト
     */
    fun getMoveableDirections(point: Point) : List<Direction> {
        val result = mutableListOf<Direction>()
        Direction.values().forEach {
            if (isMoveable(point, it)) {
                result.add(it)
            }
        }
        return result
    }

    /**
     * 地点から対象の方向へ移動可能であるか判定する。
     *
     * @param point 地点
     * @param direction 方向
     * @return 判定結果
     */
    fun isMoveable(point: Point, direction: Direction) : Boolean {
        val dist = point + (direction.pointInc * 2)
        return isInside(dist) && get(point + direction.pointInc).passible
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