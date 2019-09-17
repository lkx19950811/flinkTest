# flinkTest
flilnk scala demo

the test demo in test/scala/scalaTest

my scala version is 2.12.0
this project just for learning

普通的读取csv文件进行处理的例子在 test下<br>

使用 KafkaDemo的时候 需要下载Kafka<br>
http://mirror.bit.edu.cn/apache/kafka/2.2.0/kafka_2.11-2.2.0.tgz

解压后,在kafka根目录下使用以下命令<br>
(shift + 鼠标右键 可以跳出在该目录下打开命令窗口)<br>
1、启动zookeeper<br>
```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```
2、启动kafka<br>
```
bin\windows\kafka-server-start.bat config\server.properties
```
3、创建主题topic，topic = demo  (可以理解为频道)
```
bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic demo
```
4、启动生产者 producer
```
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic demo
```
然后启动我们的 kafkaDemo,在生产者的命令框中输入文字,kafkaDemo即可消费

