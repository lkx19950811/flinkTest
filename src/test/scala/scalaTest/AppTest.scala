package scalaTest

import org.apache.flink.api.scala._
import org.junit._



@Test
//如果是 class 需要继承序列化 不然报错
class AppTest extends Serializable {
    @Test
    def test123() = println("测试")
    /**
      * 本地计算测试  将数组平方后过滤奇数
      */
    @Test
    def testInteger(): Unit = {
        val env : ExecutionEnvironment = ExecutionEnvironment.createCollectionsEnvironment
        val numbers = env.fromElements(1,2,3,4,5,6,7)
        val result = numbers.map( n => n * n).filter( n => n % 2==0)
        result.print()
    }
    /** 定义一个 movie类 */
    case class Movie(id: Long, name: String ,genres: Array[String]){
        //重写下toString,不然 数组没法正确的打印
        override def toString: String = "Movie: " + "id:" + id + "," + "name:" + name + "," + "[" + genres.reduce(_ + "," + _) + "]"
    }
    /**
      * 读取 电影csv文件,转换成 movie对象
      * 并且过滤出动作电影
      */
    @Test
    def testText(): Unit = {
        val env:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
        val lines = env.readCsvFile[(Long, String, String)]("./file/movies.csv",ignoreFirstLine = true)
        val movies = lines.map(t=>Movie(t._1,t._2,t._3.split("\\|")))
        env.setParallelism(1)
        //过滤出动作电影
        val actionMovie = movies.filter(movie => movie.genres.contains("Action"))
        //输出流 需要最后 env.excute()
        actionMovie.writeAsText("./output/action.txt").setParallelism(1)
        //print 已经包含 env.excute()
        actionMovie.print()
    }


}


