package youkidkk.kotlin.maze.enums

import youkidkk.kotlin.maze.Point

/**
 * 方向列挙型。
 *
 * @param pointInc 地点増分
 */
enum class Direction(val pointInc: Point) {
    // 北
    NORTH(Point(0,-1)),
    // 東
    EAST (Point(1,0)),
    // 南
    SOUTH(Point(0,1)),
    // 西
    WEST (Point(-1,0))
}