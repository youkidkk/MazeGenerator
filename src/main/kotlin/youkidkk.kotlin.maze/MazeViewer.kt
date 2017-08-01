package youkidkk.kotlin.maze

import youkidkk.kotlin.maze.enums.PointStatus
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class MazeViewer(private val maze: Maze) {

    fun show() {
        val frm = JFrame()
        frm.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frm.contentPane.preferredSize = Dimension(SQUARE_SIZE * (maze.width + 1), SQUARE_SIZE * (maze.height + 1))

        frm.contentPane.add(object : JPanel() {
            override fun paintComponent(g: Graphics?) {
                g?.color = Color.BLACK
                for (x in 0..maze.width) {
                    for (y in 0..maze.height) {
                        if (maze.get(Point(x, y)) == PointStatus.WALL) {
                            g?.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE)
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
        val SQUARE_SIZE = 8
    }

}

fun main(args: Array<String>) {
    val size = 64
    val maze = MazeGenerator(size, size).generate()
    MazeViewer(maze).show()
}