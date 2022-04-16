from pirc522 import RFID
import time
import paho.mqtt.client as mqtt
rdr = RFID()
util = rdr.util()
mqttc = mqtt.Client()
mqttc.connect("broker.mqttdashboard.com", 1883, 60)
mqttc.loop_start()

TOPIC = 'team/10'




while True:
    rdr.wait_for_tag()
    (error, data) = rdr.request()
    if not error:
        print("\nDetected")
        (error, uid) = rdr.anticoll()
        if not error:
            # Print UID
            message = "UID: "+str(uid[0])+","+str(uid[1])+","+str(uid[2])+","+str(uid[3])
            print(message)
            print("{}: Tag detected".format(time.time()))
            mqttc.publish(TOPIC, message, qos=2).wait_for_publish()
            time.sleep(0.5)

# Calls GPIO cleanup
rdr.cleanup()