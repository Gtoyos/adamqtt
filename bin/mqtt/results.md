# Experiment results. ADA Lab

## Test 4.1:
clean_session = true (for both publisher and subscriber)
qos = 0 (for both publisher and subscriber)

In this setting, no msgs are reieved. (Expected).

In test 2, nothing neither.

Important: If the suscriber asks for QoS 1 but the publsher sends message with QoS 0, the subscriber will not recieve messages of QoS 1 but 0.

### Retain FLAG

The broker retains the messages. That is, publisher sends message. Broker saves it. Subscriber wildly appears, the broker send the sent messages to the subscriber.

En el experimento lo que sucede es que el cliente cada vez que se reconecta recive el mensaje!