import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
//只需要引入这个一个包,再不需要加任何代码,无论有多少该类型的隐式转换,都能够处理.
import org.apache.flink.api.scala._
object SocketWindowWordCount {

  /**
    * 官方demo 统计连接数 需要自行下载 netcat,并监听9000端口
    * 命令 nc -l -p 9000
    * 然后再开启本方法
    */
  def main(args: Array[String]) : Unit = {


    // get the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    // get input data by connecting to the socket
    val text = env.socketTextStream("localhost", 9000, '\n')

    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .flatMap { w => w.split("\\s") }
      .map { w => Tuple2(w, 1) }
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    // print the results with a single thread, rather than in parallel 单开线程打印
    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")
  }

  // Data type for words with count 定义一个WordWithCount类
  case class WordWithCount(word: String, count: Long)
}