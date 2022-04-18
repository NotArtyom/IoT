import paho.mqtt.client as mqtt

from iot import constants as C


def main() -> int:
    mqttc = mqtt.Client()
    mqttc.on_message = lambda c, d, msg: print(
        f'{msg.topic!r} {msg.qos!r} {msg.payload!r}',
    )
    mqttc.on_publish = lambda c, d, mid: print(f'{mid!r}')
    mqttc.on_subscribe = lambda c, d, mid, granted_qos: print(
        f'Subscribed: {mid!s} {granted_qos!s}',
    )
    mqttc.on_log = lambda c, d, l, buf: print(buf)
    mqttc.on_connect = lambda c, d, f, rc: print(f'rc: {rc!s}')

    mqttc.connect(C.BROKER_URL)
    mqttc.subscribe(C.TOPIC, C.QOS)
    return mqttc.loop_forever()


if __name__ == '__main__':
    raise SystemExit(main())
