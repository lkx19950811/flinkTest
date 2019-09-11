package scalaTest

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala._


object ReadCsvFile {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 直接将数据，转成Student（相当于Schema）
    val values = env.readCsvFile[Movie]("./file/movies.csv")
    values.print()
  }
  /** 定义一个 movie类 */
  case class Movie(id: Long, name: String ,genres: Set[String])
}
