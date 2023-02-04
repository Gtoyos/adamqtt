package mqtt;

import java.time.LocalDateTime;

//added external jar: c:\ada\work\lectures\slr203\mqtt\paho\paho-java-maven\org.eclipse.paho.client.mqttv3-1.2.5.jar 

import org.eclipse.paho.client.mqttv3.*;

public class PublishingMqttClient {//synchronous client
	
	public static void main(String[] args) {
		
		String topic        = "labs/paho-example-topic";
	    String messageContent = LocalDateTime.now().toString()+"Message from my Lab's Paho Mqtt Client";
	    int qos             = 1;
	    String brokerURI       = "tcp://localhost:1883";
	    String clientId     = "myClientID_Pub";
	    //MemoryPersistence persistence = new MemoryPersistence();
	    
	    
	    try(
	    	////instantiate a synchronous MQTT Client to connect to the targeted Mqtt Broker
	    	MqttClient mqttClient = new MqttClient(brokerURI, clientId);) {
	    	
	    	
	    	////specify the Mqtt Client's connection options
	    	MqttConnectOptions connectOptions = new MqttConnectOptions();
	    	//clean session 
	    	connectOptions.setCleanSession(true);
			connectOptions.setWill("labs/paho-example-topic", "RIP".getBytes(), 1, false);
	    	//customise other connection options here...
	    	//...
	    	
	    	////connect the mqtt client to the broker
	    	System.out.println("Mqtt Client: Connecting to Mqtt Broker running at: " + brokerURI);
	    	mqttClient.connect(connectOptions);
            System.out.println("Mqtt Client: sucessfully Connected.");
            
            ////publish a message
            System.out.println("Mqtt Client: Publishing message: " + messageContent);
            MqttMessage message = new MqttMessage(messageContent.getBytes());//instantiate the message including its content (payload)
            message.setQos(qos);//set the message's QoS
			//message.setRetained(false);
            //mqttClient.publish(topic, message);//publish the message to a given topic
            System.out.println("Mqtt Client: successfully published the message.");
        
            ////disconnect the Mqtt Client
			mqttClient.disconnectForcibly();
            //mqttClient.disconnect();
            System.out.println("Mqtt Client: Disconnected.");
            
            
	    }
	    catch(MqttException e) {
	    	System.out.println("Mqtt Exception reason: " + e.getReasonCode());
            System.out.println("Mqtt Exception message: " + e.getMessage());
            System.out.println("Mqtt Exception location: " + e.getLocalizedMessage());
            System.out.println("Mqtt Exception cause: " + e.getCause());
            System.out.println("Mqtt Exception reason: " + e);
            e.printStackTrace();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
    
    

}
