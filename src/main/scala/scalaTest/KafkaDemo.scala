package scalaTest
import java.util.Properties

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09
import org.apache.flink.streaming.util.serialization.SimpleStringSchema


object KafkaDemo {
  private val ZOOKEEPER_HOST = "localhost:2181"
  private val KAFKA_BROKER = "localhost:9092"
  /** 消费组 */
  private val TRANSACTION_GROUP = "com.lee.flink"
  /** topic */
  private val TOPIC_NAME = "demo"
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //默认情况下，检查点被禁用。要启用检查点，请在StreamExecutionEnvironment上调用enableCheckpointing(n)方法，
    // 其中n是以毫秒为单位的检查点间隔。每隔5000 ms进行启动一个检查点,则下一个检查点将在上一个检查点完成后5秒钟内启动
    env.enableCheckpointing(5000).setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val kafkaProps = new Properties()
    kafkaProps.setProperty("zookeeper.connect", ZOOKEEPER_HOST)
    kafkaProps.setProperty("bootstrap.servers", KAFKA_BROKER)
    kafkaProps.setProperty("group.id", TRANSACTION_GROUP)

    val transaction = env.addSource(new FlinkKafkaConsumer09[String](TOPIC_NAME,new SimpleStringSchema(),kafkaProps))
    //打印消费的信息
    transaction.print()
    env.execute()
  }
}
