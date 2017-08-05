package youkidkk.kotlin.maze.viewer

import youkidkk.kotlin.maze.Maze
import youkidkk.kotlin.maze.Point
import youkidkk.kotlin.maze.enums.Direction
import youkidkk.kotlin.maze.enums.PointStatus
import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel

class MazeViewer(private val maze: Maze, private val route: List<Point>? = null) {

    private val squareSize: Int

    init {
        squareSize = SQUARE_BASE_SIZE / maze.height * 2
    }

    fun show() {
        val frm = JFrame()
        frm.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frm.contentPane.preferredSize = Dimension(squareSize * (maze.width / 2), squareSize * (maze.height / 2))

        frm.contentPane.add(object : JPanel() {
            override fun paintComponent(g: Graphics?) {
                val g2: Graphics2D? = g as Graphics2D

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
        })
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
