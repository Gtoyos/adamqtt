## SLR 203

All classes are located in the folder src/mqtt and have a static `main` method so they can be executed independently.

The binary client is located in the file `src/mqtt/BinaryClient.java`. It sends a CONNECT message to the broker, waits for the CONNACKS response. Then sends a PUBLISH message. The user id of the client is `ada`. The PUBLISH command sends a message `BBB` to the topic `AAA`.

The connect message is composed of the following array of bytes (in hex format):

`[10 0F 00 04 4D 51 54 54 04 02 00 3C 00 03 41 64 61]`

- 0x10: Control code for CONNECT type message.
- 0x0F: Remaining length of the message.
- 0x00 & 0x04: Length of protocol name (MQTT).
- 0x4D & 0x51 & 0x54 & 0x54: Protocol name (MQTT).
- 0x04: Protocol version (v4).
- 0x02: Connect flag (clean session flag = 1).
- 0x00 & 0x3c: Keep alive interval (set to 6000 milliseconds).
- 0x00 & 0x03: Length of the payload message (username length).
- 0x41 & 0x64 & 0x61: Payload content (username: ADA).

The connack message is the following:

`[20 02 00 00]`

- 0x20: MQTT control packet type. (CONNACK).
- 0x02: Remaining length of the message.
- 0x00: Connect Acknowledge Flags.
- 0x00: Return code (0=successfull connection).

The publish message is the following:

`[30 11 00 06 43 61 6E 74 61 6C 43 61 6D 65 6D 62 65 72 74]`

- 0x30: Control code for PUBLISH message.
- 0x11: Remeining length of the message.
- 0x00 & 0x06: Length of the topic name.
- 0x61 & 0x6E & 0x74 & 0x61 & 0x6C: Topic name - Cantal in hexadecimal.
- 0x43 & 0x61 & 0x6D & 0x65 & 0x6D & 0x62 & 0x65 & 0x72 & 0x74: Message - Cambembert in hexadecimal.