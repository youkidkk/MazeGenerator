package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.enums.Direction
import youkidkk.kotlin.maze.enums.PointStatus
import java.util.*

/**
 * 迷路ジェネレータ。
 *
 * @param paramWidth 迷路の幅
 * @param paramHeight 迷路の高さ
 * @param paramWayLength 道の長さ
 */
class MazeGenerator(paramWidth: Int = 0,
                    paramHeight: Int = 0,
                    paramWayLength: Int = 0) {

    /**
     * 迷路オブジェクト。
     */
    private lateinit var maze: Maze

    // 迷路の幅。
    private var width  = MIN_WIDTH * 2

    // 迷路の高さ。
    private var height = MIN_HEIGHT * 2

    // 道の長さ。
    private var maxWayLength = DEFAULT_WAY_LENGTH

    /**
     * イニシャライザ。
     */
    init {
        if (paramWidth  > MIN_WIDTH) { width  = paramWidth  * 2}
        if (paramHeight > MIN_HEIGHT) { height = paramHeight * 2}
        if (paramWayLength > MIN_WAY_LENGTH) { maxWayLength = paramWayLength }
    }

    /**
     * 迷路を生成。
     *
     * @return 迷路オブジェクト
     */
    fun generate() : Maze {
        // 迷路の初期化
        maze = Maze(width, height)

        // 開始地点を取得し、床にする
        var startPoint = getFirstPoint()
        maze.set(startPoint, PointStatus.FLOOR)

        while (true) {
            // 現在地点から掘り進む
            dig(startPoint)

            // 開始地点候補のリストを取得する
            val diggablePoints = getDiggableStartPoints()
            if (diggablePoints.isEmpty()) {
                // 候補の開始地点がない場合、繰り返しを終了する
                break
            } else {
                // 開始地点候補のリストのうち、いずれかをランダムに開始地点とする
                startPoint = diggablePoints[getRandomInt(diggablePoints.size)]
            }
        }

        return maze
    }

    /**
     * 最初の開始地点を取得。
     */
    fun getFirstPoint() : Point = Point(
            getRandomInt(width  / 2) * 2 + 1,
            getRandomInt(height / 2) * 2 + 1
    )

    /**
     * 開始地点から道を掘り進める。
     * 道の長さに達したとき、または掘れる方向がなくなるまで行う。
     *
     * @param startPoint 開始地点
     */
    fun dig(startPoint: Point) : Unit {
        var currentPoint = startPoint.copy()

        // 道の長さの分、繰り返す
        for (i in 0..maxWayLength) {
            // 掘れる方向のリストを取得する
            val diggableDirections = getDiggableDirections(currentPoint)

            if (diggableDirections.isEmpty()) {
                // 掘れる方向がない場合、繰り返しを終了する
                break
            } else {
                // ランダムに掘れる方向を掘り、地点を移動する
                val direction = diggableDirections[getRandomInt(diggableDirections.size)]
                currentPoint = digWall(currentPoint, direction)
            }
        }
    }

    /**
     * 地点から掘れる方向のリストを取得する。
     *
     * @param point 地点
     * @return 掘れる方向のリスト
     */
    fun getDiggableDirections(point: Point) : List<Direction> {
        // 戻り値
        val result = mutableListOf<Direction>()

        // 方向ごとに繰り返し
        Direction.values().forEach {
            // 対象の地点を取得
            val directionPoint = point + (it.pointInc * 2)

            if (maze.isNotOutOfRange(directionPoint) && !maze.get(directionPoint).passible) {
                // 対象の地点が範囲内、かつ掘られていない場合、戻り値に追加する
                result.add(it)
            }
        }

        return result
    }

    /**
     * 開始地点候補のリストを取得する。
     *
     * @return 開始地点候補のリスト
     */
    fun getDiggableStartPoints() : List<Point> {
        // 戻り値
        val result = mutableListOf<Point>()

        // x,yがいずれも奇数の地点で繰り返し
        for (x in 1..width step 2) {
            for (y in 1..height step 2) {
                val point = Point(x, y)
                if (maze.get(point).passible && !getDiggableDirections(point).isEmpty()) {
                    // 対象の地点が掘られている、かつ掘れる方向がある場合、戻り値に追加する
                    result.add(point)
                }
            }
        }

        return result
    }

    /**
     * 対象地点から方向へ掘る。
     *
     * @param point 地点
     * @param direction 方向
     */
    fun digWall(point: Point, direction: Direction) : Point {
        maze.set(point + direction.pointInc, PointStatus.FLOOR)
        val result = point + ( direction.pointInc * 2 )
        maze.set(result, PointStatus.FLOOR)
        return result
    }

    /**
     * 0から最大値未満でランダムな数値を取得する。
     *
     * @param max 最大値
     * @return 0から最大値未満のランダムな数値
     */
    fun getRandomInt(max: Int) = Random().nextInt(max)

    /**
     * 定数。
     */
    companion object {
        // 幅の最小値。
        val MIN_WIDTH = 16

        // 高さの最小値。
        val MIN_HEIGHT = 16

        // 道の長さのデフォルト値。
        val DEFAULT_WAY_LENGTH = 10

        // 道の長さの最小値。
        val MIN_WAY_LENGTH = 3
    }

}

fun main(args: Array<String>) {
    println(MazeGenerator().generate())
}