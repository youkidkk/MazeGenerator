package youkidkk.kotlin.maze.viewer

import youkidkk.kotlin.maze.Maze
import youkidkk.kotlin.maze.Point
import youkidkk.kotlin.maze.enums.Direction
import youkidkk.kotlin.maze.enums.PointStatus
import youkidkk.kotlin.maze.generator.MazeGenerator
import youkidkk.kotlin.maze.route.RouteSearcher
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JPanel

class MazeViewer(private var maze: Maze, private var route: List<Point>? = null) {

    private val squareSize: Int

    init {
        squareSize = SQUARE_BASE_SIZE / maze.height * 2
    }

    fun show() {
        val frm = JFrame()
        frm.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frm.contentPane.preferredSize = Dimension(squareSize * (maze.width / 2), squareSize * (maze.height / 2))

        val panel = object : JPanel() {
            override fun paintComponent(g: Graphics?) {
                val g2: Graphics2D? = g as Graphics2D

                g2?.color = Color.WHITE
                g2?.fillRect(0, 0, squareSize * (maze.width / 2), squareSize * (maze.height / 2))

                // ルートの描画
                route?.forEach {
                    g2?.stroke = BasicStroke(1.0f)
                    g2?.color = Color.RED
                    g2?.fillRect(it.x / 2 * squareSize, it.y / 2 * squareSize, squareSize, squareSize)
                }

                // 迷路の描画
                g2?.color = Color.BLACK
                g2?.stroke = BasicStroke(4.0f)
                for (x in 1..maze.width step 2) {
                    for (y in 1..maze.height step 2) {
                        val pointNW = Point(x / 2 * squareSize, y / 2 * squareSize)
                        val pointNE = Point(x / 2 * squareSize + squareSize, y / 2 * squareSize)
                        val pointSW = Point(x / 2 * squareSize, y / 2 * squareSize + squareSize)
                        val pointSE = Point(x / 2 * squareSize + squareSize, y / 2 * squareSize + squareSize)
                        if (maze.get(Point(x, y) + Direction.NORTH.pointInc) == PointStatus.WALL) {
                            g2?.drawLine(pointNW, pointNE)
                        }
                        if (maze.get(Point(x, y) + Direction.WEST.pointInc) == PointStatus.WALL) {
                            g2?.drawLine(pointNW, pointSW)
                        }
                        if (maze.get(Point(x, y) + Direction.SOUTH.pointInc) == PointStatus.WALL) {
                            g2?.drawLine(pointSW, pointSE)
                        }
                        if (maze.get(Point(x, y) + Direction.EAST.pointInc) == PointStatus.WALL) {
                            g2?.drawLine(pointNE, pointSE)
                        }
                    }
                }
            }
        }
        panel.isFocusable = true
        panel.addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {
                val width = maze.width
                val height = maze.height

                maze = MazeGenerator(maze.width, maze.height, startPoint = Point(1, 1), endPoint = Point(width - 1, height - 1)).generate()
                route = RouteSearcher(maze, Point(1, 1), Point(width - 1, height - 1)).search()
                panel.repaint()
            }

            override fun keyPressed(e: KeyEvent?) { }

            override fun keyReleased(e: KeyEvent?) { }
        })

        frm.contentPane.add(panel)
        frm.pack()
        frm.setLocationRelativeTo(null)
        frm.isVisible = true
    }

    companion object {
        val SQUARE_BASE_SIZE = 1024
    }

}

fun Graphics.drawLine(pointStart: Point, pointEnd: Point) : Unit {
    this.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y)
}
