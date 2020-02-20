# Smart Technology Solutions

## Desscription
* The program checks the message if it starts with a valid **STX** `(0x02)` and a valid **ETX** `(0x03)`.
* The program calculates an **LRC** of the message (excluding `STX`) and compares with the last byte of the message.
* The program includes a function that can add **DLE**s to a message, however, since it is not required in order to calculate the **LRC**, the function was not used. - Assuming the original message `{ 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 }` from `RunExample.java` file is valid.

If the original message from `RunExample.java` included **DLE**s, the **LRC** would not match with the last byte of the message which would make the message invalid.

### Dependencies
* [Apache Commons Lang v3.9](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 "Apache Commons Lang - MVNrepository")

## Messages
```java
byte[] message = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };

//Message with invalid LRC
byte[] message2 = { 0x02, 0x10, 0x02, 0x0A, 0x09, 0x10, 0x07, 0x08, 0x03, 0x14 };

//Message with invalid ETX
byte[] message3 = { 0x02, 0x04, 0x0A, 0x02, 0x02, 0x10, 0x08, 0x08, 0x01, 0x1F };

//Message with invalid STX
byte[] message4 = { 0x08, 0x10, 0x02, 0x0A, 0x09, 0x10, 0x07, 0x08, 0x03, 0x14 };

//Message with valid STX, ETX and LRC
byte[] message5 = { 0x02, 0x10, 0x02, 0x0A, 0x18, 0x0A, 0x07, 0x08, 0x03, 0x06 };
```

## Output
```java
Full message (with DLEs): 0x02, 0x10, 0x10, 0x10, 0x02, 0x0a, 0x10, 0x10, 0x10, 0x10, 0x07, 0x08, 0x14, 
Calculated LRC: 0x14
Message LRC (Last Byte): 0x14
Message 1 is valid: true
 
Invalid LRC
Message 2 is valid: false
 
Message must start with a valid STX and end with a valid ETX
Message 3 is valid: false
 
Message must start with a valid STX and end with a valid ETX
Message 4 is valid: false
 
Full message (with DLEs): 0x02, 0x10, 0x10, 0x10, 0x02, 0x0a, 0x18, 0x0a, 0x07, 0x08, 0x06, 
Calculated LRC: 0x06
Message LRC (Last Byte): 0x06
Message 5 is valid: true
```