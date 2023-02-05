package mqtt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class BinaryClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 1883;
        Socket socket = new Socket(host, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        //byte[] buf = {0x10, 0x1A, 0x00, 0x04,0x4D,0x51,0x54,0x54,0x04,0x02,0x00, 0x3C, 0x00, 0x0E, 0x6D, 0x79, 0x43, 0x6C, 0x69, 0x65, 0x6E, 0x74, 0x49, 0x44, 0x5F, 0x50, 0x75, 0x62 };
        
        //out.write(buf);
        //printArray(buf);
        System.out.println("CONNECT Message:");
        printArray(createCONNECTpayload("PYTHON1"));
        out.write(createCONNECTpayload("PYTHON1"));
        out.flush();
        //System.out.println(createMQTTConnectMessage("yo"));
        //out.writeInt(0);
        byte[] buf = new byte[10000];
        int length = in.read(buf);
        byte[] connack = new byte[length];
        for(int i=0;i<length;i++)
            connack[i] = buf[i];
        System.out.println("CONNACK Message:");
        printArray(connack);
        if(connack[2] == 0x00 && connack[3]== 0x00){ //Yay! We now try to publish sth
            //Send a PUBLISH
            System.out.println("PUBLISH Message:");
            printArray(createPUBLISHpayload("AAA","BBB"));
            out.write(createPUBLISHpayload("AAA","BBB"));
            out.flush();
            //byte[] buf2 = new byte[10000];
            //int length2 = in.read(buf2);
            //byte[] connack2 = new byte[length2];
            //for(int i=0;i<length2;i++)
            //    connack2[i] = buf2[i];
            //System.out.println("PUBACK Message:"); No messa
            //printArray(connack2);
        }
        socket.close();
        
    }
    

    public static void printArray(byte[] array) {
        System.out.print("Array: ");
        for (byte b : array) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }
    
    public static byte[] createPUBLISHpayload(String topic, String msg){
        //30 REMAINING_LENGTH 00 TOPIC_LENGTH TOPIC PAYLOAD
        byte fixed_header = 0x30;
        byte[] topic_len = {
            (byte)(topic.getBytes().length >>> 24),
            (byte)(topic.getBytes().length >>> 16),
            (byte)(topic.getBytes().length >>> 8),
            (byte)topic.getBytes().length};
        topic_len = trimLeft(topic_len);
        int remlen = 1+topic_len.length+topic.getBytes().length+msg.getBytes().length;
        byte[] remaining_length = {
            (byte)(remlen >>> 24),
            (byte)(remlen >>> 16),
            (byte)(remlen >>> 8),
            (byte)remlen};
        remaining_length = trimLeft(remaining_length);
        byte[] payload = new byte[1+remlen+remaining_length.length];
        int i=0;
        payload[i++] = fixed_header;
        for(byte b : remaining_length)
            payload[i++] = b;
        payload[i++] = 0x00;
        for(byte b : topic_len)
            payload[i++] = b;
        for(byte b : topic.getBytes())
            payload[i++] = b;
        for(byte b : msg.getBytes())
            payload[i++] = b;
        return payload;
    }

    public static byte[] createCONNECTpayload(String clientId) {
        
        byte remaining_length = (byte) (12 + clientId.length());
        byte[] length_protocol_name = {0x00,0x04};
        byte[] name = {0x4d,0x51,0x54,0x54,0x04}; //MQTT4
        
        byte flag = 0x2; //clean_session=1
        byte[] keep_alive = {0x00,0x3c}; //60 sec.
        byte[] user_length = {0x0,(byte) clientId.length()};

        byte[] payload = new byte[2+12+clientId.length()];
        int index=0;
        payload[index++] = 0x10;
        payload[index++] = remaining_length;
        payload[index++] =length_protocol_name[0];
        payload[index++] =length_protocol_name[1];
        payload[index++] =name[0];
        payload[index++] =name[1];
        payload[index++] =name[2];
        payload[index++] =name[3];
        payload[index++] =name[4];
        payload[index++] =flag;
        payload[index++] =keep_alive[0];
        payload[index++] =keep_alive[1];
        payload[index++] =user_length[0];
        payload[index++] =user_length[1];
        for(int i=0;i<clientId.length();i++){
            payload[index+i] = (byte) clientId.charAt(i);
        }
        return payload;
    }

    public static byte[] trimLeft(byte[] array) {
        int nonZeroIndex = 0;
        for (; nonZeroIndex < array.length; nonZeroIndex++) {
            if (array[nonZeroIndex] != 0) {
                break;
            }
        }
        int trimmedLength = array.length - nonZeroIndex;
        byte[] trimmedArray = new byte[trimmedLength];
        System.arraycopy(array, nonZeroIndex, trimmedArray, 0, trimmedLength);
        return trimmedArray;
    }
}
