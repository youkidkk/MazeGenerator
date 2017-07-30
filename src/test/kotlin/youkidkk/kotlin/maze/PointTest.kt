package youkidkk.kotlin.maze

import org.hamcrest.core.Is.*
import org.junit.Assert
import org.junit.Test

class PointTest {

    @Test
    fun testPlus() {
        Assert.assertThat(Point(3,5) + Point(2, 6), `is`(Point(5, 11)))
    }

    @Test
    fun testTimes() {
        Assert.assertThat(Point(3,5) * 3, `is`(Point(9, 15)))
    }

}