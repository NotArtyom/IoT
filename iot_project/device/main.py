from __future__ import annotations

import contextlib
import time
import json
from typing import Generator
from typing import NoReturn
from concurrent.futures import ThreadPoolExecutor

import paho.mqtt.client as mqtt
from pirc522 import RFID
import RPi.GPIO as GPIO

import enum

INBOUND_TOPIC = 'team10/serverToClient'
OUTBOUND_TOPIC = 'team10/clientToServer'
BROKER_URL = 'broker.mqttdashboard.com'
BROKER_PORT = 1883
BROKER_KEEPALIVE = 60
QOS = 2


rdr = RFID()


class State(str, enum.Enum):
    IDLE = "IDLE"
    SCANNING = "SCANNING"

illumination = {
    "RED": 3,
    "GREEN": 5,
    "BLUE": 7,
}


@contextlib.contextmanager
def run_gpio() -> Generator[GPIO, None, None]:
    try:
        yield GPIO
    finally:
        pass

def blink(illumination, v: int):
    global GPIO_initialized
    if v != -1:
        GPIO.output(v, GPIO.HIGH)
        time.sleep(0.5)
        GPIO.output(illumination['RED'], GPIO.LOW)
        GPIO.output(illumination['GREEN'], GPIO.LOW)
        GPIO.output(illumination['BLUE'], GPIO.LOW)

# STATE
current_state = State.IDLE


def initialize_GPIO():
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(illumination['RED'], GPIO.OUT)
    GPIO.setup(illumination['GREEN'], GPIO.OUT)
    GPIO.setup(illumination['BLUE'], GPIO.OUT)
    GPIO.output(illumination['RED'], GPIO.LOW)
    GPIO.output(illumination['GREEN'], GPIO.LOW)
    GPIO.output(illumination['BLUE'], GPIO.LOW)


initialize_GPIO()

#MQTT
mqttc = mqtt.Client()
mqttc.connect(BROKER_URL)


def handle_message(client: mqtt.Client, d, message) -> NoReturn:
    global current_state
    json_object = json.loads(message.payload)
    current_state = State[json_object["state"]]
    current_illumination = illumination.get(json_object["illumination"], -1)
    blink(illumination, current_illumination)

    while current_state == State.SCANNING:
        rdr.wait_for_tag()
        error, data = rdr.request()
        if not error:
            error, uid = rdr.anticoll()
            if not error:
                uid_string = f"{'-'.join(map(str, uid))}"
                json_message = '{' + f'"uuid": "{uid_string}"' + '}'
                client.publish(
                    OUTBOUND_TOPIC,
                    json_message,
                    qos=QOS,
                ).wait_for_publish(5)
                break



mqttc.on_message = handle_message

mqttc.on_subscribe = lambda c, d, mid, granted_qos: print(
    f'Subscribed: {mid!s} {granted_qos!s}',
)
mqttc.on_log = lambda c, d, l, buf: print(buf)
mqttc.connect(BROKER_URL)
mqttc.subscribe(INBOUND_TOPIC, QOS)

try:
    mqttc.loop_forever()
except KeyboardInterrupt:
    GPIO.cleanup()
