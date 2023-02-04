package mqtt;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

//added external jar: c:\ada\work\lectures\slr203\mqtt\paho\paho-java-maven\org.eclipse.paho.client.mqttv3-1.2.5.jar 

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class TempClient2 {//synchronous client
	
	public static void main(String[] args) throws InterruptedException {
		
		String topic        = "/home/Lyon/sido/sht30/value";
	    String messageContent = LocalDateTime.now().toString()+" Message from my Lab's Paho Mqtt Client";
	    int qos             = 1;
	    String brokerURI       = "tcp://localhost:1883";
	    String clientId     = "sht30";
	    MemoryPersistence persistence = new MemoryPersistence();
	    
	    
	    try(
	    	////instantiate a synchronous MQTT Client to connect to the targeted Mqtt Broker
	    	MqttClient mqttClient = new MqttClient(brokerURI, clientId,persistence);) {
	    	
	    	
	    	////specify the Mqtt Client's connection options
	    	MqttConnectOptions connectOptions = new MqttConnectOptions();
	    	//clean session 
	    	connectOptions.setCleanSession(false);
	    	//customise other connection options here...
	    	//...
	    	
	    	////connect the mqtt client to the broker
	    	System.out.println("Mqtt Client: Connecting to Mqtt Broker running at: " + brokerURI);
	    	mqttClient.connect(connectOptions);
            System.out.println("Mqtt Client: sucessfully Connected.");
            
            ////publish a message
            while(true){
                messageContent = Integer.toString(ThreadLocalRandom.current().nextInt(15, 25));
                String messageContent2 = Integer.toString(ThreadLocalRandom.current().nextInt(50, 100));
                MqttMessage message = new MqttMessage(messageContent.getBytes());
                message.setQos(1);
                mqttClient.publish(topic, message);//publish the message to a given topic
                mqttClient.publish("/home/Lyon/sido/sht30/value2", new MqttMessage(messageContent2.getBytes()));
                Thread.sleep(1000);
            }
	    }
	    catch(MqttException e) {
	    	System.out.println("Mqtt Exception reason: " + e.getReasonCode());
            System.out.println("Mqtt Exception message: " + e.getMessage());
            System.out.println("Mqtt Exception location: " + e.getLocalizedMessage());
            System.out.println("Mqtt Exception cause: " + e.getCause());
            System.out.println("Mqtt Exception reason: " + e);
            e.printStackTrace();
	    }
    
	}
    
    

}
