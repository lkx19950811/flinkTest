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
    case class Movie(id: Long, name: String ,genres: String)
    /**
      * 读取 电影csv文件,转换成 movie对象
      * 并且过滤出动作电影
      */
    @Test
    def testText(): Unit = {
        val env:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
        val movies = env.readCsvFile[Movie]("./file/movies.csv",ignoreFirstLine = true)
        env.setParallelism(1)
        //过滤出动作电影
        val actionMovie = movies.filter(movie => movie.genres.contains("Action"))
        actionMovie.print()
    }


}


