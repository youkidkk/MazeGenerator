package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.generator.MazeGenerator
import youkidkk.kotlin.maze.route.RouteSearcher
import youkidkk.kotlin.maze.viewer.MazeViewer

fun main(args: Array<String>) {
    val size = 128
    val maze = MazeGenerator(size, size, 30).generate()
    val route = RouteSearcher(maze, Point(1, 1), Point(size - 1, size - 1)).search()
    MazeViewer(maze, route).show()
}