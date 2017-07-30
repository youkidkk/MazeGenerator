package youkidkk.kotlin.maze.enums

/**
 * 地点状態列挙型。
 *
 * @passible 通行可能かどうか
 */
enum class PointStatus(val passible: Boolean) {
    // 床
    FLOOR(true),
    // 壁
    WALL(false)
}