package scalaTest

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
  * 不使用实体类
  * 读取CSV文件 文件在 file文件夹下
  *
  * 将读取到的电影数据,过滤出动作电影并打印
  */
object ReadCsvFile {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 直接将数据，转成 Tuple3 含有三个参数的
    val values = env.readCsvFile[(Long,String,String)]("./file/movies.csv",ignoreFirstLine = true)
    val result = values.filter(_._3.contains("Action"))
    result.print()
  }

}
