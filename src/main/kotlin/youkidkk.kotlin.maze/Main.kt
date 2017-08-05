package youkidkk.kotlin.maze

fun main(args: Array<String>) {
    val maze = MazeGenerator(64, 64).generate()
    val route = RouteSearcher(maze, Point(1,1), Point(127, 127)).search()
    MazeViewer(maze, route).show()
}