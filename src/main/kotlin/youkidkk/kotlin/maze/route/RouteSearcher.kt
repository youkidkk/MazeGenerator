package youkidkk.kotlin.maze.route

import youkidkk.kotlin.maze.Maze
import youkidkk.kotlin.maze.Point

/**
 * ルート探索を行うためのクラス。
 *
 * @param maze 迷路
 * @param start 開始地点
 * @param end 終了地点
 */
class RouteSearcher(val maze: Maze, val start: Point, val end: Point) {

    /**
     * 到達済み地点マップ。
     */
    lateinit var visited: MutableMap<Point, List<Point>>

    /**
     * ルート探索を行う。
     */
    fun search() : List<Point> {
        visited = mutableMapOf()
        val route = mutableListOf<Point>()
        route.add(start)

        visited.put(start, route)

        go(start, route)

        return visited.get(end)!!
    }

    /**
     * 対象地点から再帰的にルートを探索する。
     *
     * @param point 対象地点
     * @param route これまでのルート
     */
    private fun go(point: Point, route: List<Point>) {
        // 対象地点が終了地点の場合は処理を終了する
        if (point == end) {
            return
        }

        // 移動可能な方向リストで繰り返し
        val directions = maze.getMoveableDirections(point)
        for (direction in directions) {
            // 移動先地点
            val distPoint = point + (direction.pointInc * 2)

            // 移動先地点が既に到達済みでない場合、再帰的にルートを探索する
            if (!visited.containsKey(distPoint)) {
                val next = mutableListOf<Point>()
                next.addAll(route)
                next.add(distPoint)
                visited.put(distPoint, next)

                go(distPoint, next)
            }
        }
    }

}
