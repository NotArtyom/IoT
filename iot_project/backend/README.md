# Backend
## Использование API для Web-клиента:
0. Подтянуть либы `sockjs` и `stomp` для взаимодействия с вебсокетами
1. При открытии страницы подключиться к эндпоинту вебсокетов и подписаться на топик "/topic/updates"
    ```java
    var stompClient = null;
    
    function connect() {
        var socket = new SockJS('/ws-endpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/updates', function (update) {
                // TODO: обработка апдейта из update.body
            });
        });
    }
    ```
2. При нажатии на кнопку активации считывания отправить следующий запрос на эндпоинт `/action`:
    ```json
    {
      "action": "CHECK"
    }
    ```
    или 
    ```json
    {
      "action": "REGISTER"
    }
    ```
   В зависимости от того, какое положение принимает верхний свитч
3. Обрабатывать апдейты, имеющие общий вид:
    ```json
    {
      "action": "REGISTER",
      "success": true,
      "ids": ["1", "2", "3"]
    }
    ```
   Если `action` == `"CHECK"` - показать ошибку/успех проверки UUID, находящегося в массиве `ids`, иначе при `action` == `REGISTER`, показать список зареганных id из массива

## Коммуникация на девайсе:
0. Подключиться к брокеру `broker.mqttdashboard.com:1883`
1. Подписаться на топик `team10/serverToClient`
2. При получении сообщения интерпретировать строку как json следующего вида:
    ```json
    {
      "state": "IDLE", // Возможные значения: "IDLE", "SCANNING"
      "illumination": "NONE" // Возможные значения: "RED", "GREEN", "NONE"
    }
    ```
   1. Если `state` == `SCANNING` - ожидать сканирования датчика. Иначе - выйти из режима ожидания
   2. Если `illumination` != `NONE` - моргнуть лампочкой соответствующим цветом. Иначе - ничего не делать
3. При сканировании датчика отправить на топик `team10/clientToServer` следующее сообщение:
    ```json
    {
      "uuid": "<РАСПОЗНАННЫЙ_UUID>"
    }
    ```
