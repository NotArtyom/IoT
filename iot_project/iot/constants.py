import os

BROKER_URL = os.environ.get('MQTT_URL', 'broker.mqttdashboard.com')
TOPIC = os.environ.get('MQTT_TOPIC', 'team-10-push')
QOS = int(os.environ.get('MQTT_QOS', 2))
