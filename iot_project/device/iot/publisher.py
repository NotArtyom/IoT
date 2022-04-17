import contextlib
import time
from typing import Generator

import paho.mqtt.client as mqtt
from pirc522 import RFID

# from . import constants as C


@contextlib.contextmanager
def run_rdr() -> Generator[None, None, None]:
    rdr = RFID()
    try:
        yield rdr
    finally:
        rdr.cleanup()


def main():
    mqttc = mqtt.Client()
    mqttc.connect("broker.mqttdashboard.com")
    mqttc.loop_start()

    with run_rdr() as rdr:
        while True:
            rdr.wait_for_tag()
            error, data = rdr.request()
            if not error:
                print('Detected')
                error, uid = rdr.anticoll()
                if not error:
                    # Print UID
                    message = f"UID: {','.join(map(str, uid))}"
                    print(message)
                    print(f'{time.time()}: Tag detected')
                    mqttc.publish('team-10-push', message, 2).wait_for_publish()
                    time.sleep(0.5)


if __name__ == '__main__':
    raise SystemExit(main())
