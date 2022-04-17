from __future__ import annotations

import enum
import time
from types import TracebackType
from typing import NoReturn
from typing import Optional

import RPi.GPIO as GPIO



class Pin(enum.IntEnum):
    RED = 11
    GREEN = 13
    BLUE = 15
    BUTTON = 37


class State:
    def __init__(self) -> None:
        self._index = 0
        self._states = [
            (0, 0, 0),
            (1, 0, 0),
            (1, 1, 0),
            (0, 1, 1),
            (0, 0, 1),
        ]

    def __iter__(self) -> State:
        return self

    def __next__(self) -> tuple[int, int, int]:
        curr_state = self._states[self._index]
        self._index = (self._index + 1) % len(self._states)
        return curr_state


class GPIOManager:
    def __init__(self) -> None:
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(Pin.RED, GPIO.OUT)
        GPIO.setup(Pin.GREEN, GPIO.OUT)
        GPIO.setup(Pin.BLUE, GPIO.OUT)
        GPIO.setup(Pin.BUTTON, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
        self._state = State()

    def __enter__(self) -> GPIOManager:
        return self

    def __exit__(
        self,
        tp: Optional[type[BaseException]],
        inst: Optional[BaseException],
        tb: Optional[TracebackType],
    ) -> Optional[bool]:
        GPIO.cleanup()
        if isinstance(inst, KeyboardInterrupt):
            return True
        else:
            return None

    def send(self) -> None:
        r, g, b = next(self._state)
        GPIO.output(Pin.RED, GPIO.LOW if not r else GPIO.HIGH)
        GPIO.output(Pin.GREEN, GPIO.LOW if not g else GPIO.HIGH)
        GPIO.output(Pin.BLUE, GPIO.LOW if not b else GPIO.HIGH)


def main() -> NoReturn:
    with GPIOManager() as gm:
        gm.send()
        while True:
            print(GPIO.input(Pin.BUTTON))
            if GPIO.input(Pin.BUTTON) == GPIO.HIGH:
                gm.send()
                time.sleep(0.4)
            time.sleep(0.05)


if __name__ == "__main__":
    raise SystemExit(main())