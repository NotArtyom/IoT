set zookeper_start_script=E:\iot_itis\kafka\kafka\bin\windows\zookeeper-server-start.bat
set zookeper_cfg_path=E:\iot_itis\kafka\kafka\config\zookeeper.properties
set kafka_start_script=E:\iot_itis\kafka\kafka\bin\windows\kafka-server-start.bat
set kafka_config_path=E:\iot_itis\kafka\kafka\config\server.properties
E:
start %zookeper_start_script% %zookeper_cfg_path%
timeout 10
start %kafka_start_script% %kafka_config_path%