package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.generator.MazeGenerator
import youkidkk.kotlin.maze.route.RouteSearcher
import youkidkk.kotlin.maze.viewer.MazeViewer

fun main(args: Array<String>) {
    val maze = MazeGenerator(128, 128, 30).generate()
    val route = RouteSearcher(maze, Point(1, 1), Point(127, 127)).search()
    MazeViewer(maze, route).show()
}