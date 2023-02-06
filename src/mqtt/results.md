# Some experiment results. MQTT Lab

## Test 4.1:

clean_session = true (for both publisher and subscriber)
qos = 0 (for both publisher and subscriber)

In this setting, no msgs are reieved. (Expected).

In test 2, nothing neither.

Important: If the suscriber asks for QoS 1 but the publsher sends message with QoS 0, the subscriber will not recieve messages of QoS 1 but 0.

### Retain FLAG

The broker retains the messages. That is, publisher sends a message. Broker saves it. Subscriber appears, the broker sends the message to the subscriber.

In the test, each time the client connects it receives the retained message!