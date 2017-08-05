package youkidkk.kotlin.maze

class RouteSearcher(val maze: Maze, val start: Point, val end: Point) {

    lateinit var visited: MutableMap<Point, List<Point>>

    fun search() : List<Point> {
        visited = mutableMapOf()
        val route = mutableListOf<Point>()
        route.add(start)

        visited.put(start, route)

        go(start, route)

        return visited.get(end)!!
    }

    private fun go(point: Point, route: List<Point>) {
        if (point == end) {
            return
        }
        val directions = maze.getMoveableDirections(point)
        for (direction in directions) {
            val distPoint = point + (direction.pointInc * 2)
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
