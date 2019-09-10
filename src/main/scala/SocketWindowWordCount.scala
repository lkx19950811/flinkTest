import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.scala._
object SocketWindowWordCount {

  /**
    * 官方demo
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