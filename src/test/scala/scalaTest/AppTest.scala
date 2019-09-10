package scalaTest

import org.junit._
import Assert._

@Test
class AppTest {

    @Test
    def testOK() = assertTrue(true)
    @Test
    def test123() = println("测试")
//    @Test
//    def testKO() = assertTrue(false)

}


