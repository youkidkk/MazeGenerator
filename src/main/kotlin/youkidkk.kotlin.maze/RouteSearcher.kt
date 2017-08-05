package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.enums.Direction

class RouteSearcher(val maze: Maze, val start: Point, val end: Point) {

    lateinit var visited: MutableMap<Point, List<Point>>

    fun search() : List<Point> {
        visited = mutableMapOf()
        val route = mutableListOf<Point>()
        route.add(start)

        visited.put(start, route)

        todo(start, route)

        return visited.get(end)!!
    }

    fun todo(point: Point, route: List<Point>) {
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

                todo(distPoint, next)
            }
        }
    }

}

fun main(args: Array<String>) {
    val maze = MazeGenerator(64, 64).generate()
    val route = RouteSearcher(maze, Point(1,1), Point(127, 127)).search()
    MazeViewer(maze, route).show()
}