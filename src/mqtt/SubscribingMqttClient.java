package mqtt;

//added external jar: c:\ada\work\lectures\slr203\mqtt\paho\paho-java-maven\org.eclipse.paho.client.mqttv3-1.2.5.jar 

import org.eclipse.paho.client.mqttv3.*;

public class SubscribingMqttClient{//synchronous client
    public static void main(String[] args) {
		
		String topic        = "labs/paho-example-topic";
	    String brokerURI       = "tcp://localhost:1883";
	    String clientId     = "myClientID_Sub";
	    //MemoryPersistence persistence = new MemoryPersistence();
	    
	    
	    try(
	    	////instantiate a synchronous MQTT Client to connect to the targeted Mqtt Broker
	    	MqttClient mqttClient = new MqttClient(brokerURI, clientId);) {
	    	
	    	
	    	////specify the Mqtt Client's connection options
	    	MqttConnectOptions connectOptions = new MqttConnectOptions();
	    	//clean session 
	    	connectOptions.setCleanSession(true);
	    	//customise other connection options here...
	    	//...
	    	
	    	////connect the mqtt client to the broker
	    	System.out.println("Mqtt Client: Connecting to Mqtt Broker running at: " + brokerURI);
	    	mqttClient.connect(connectOptions);
            System.out.println("Mqtt Client: sucessfully Connected.");
            
            ////wait for a message
            int qos =1;
            mqttClient.subscribe(topic,qos);
            MyCallback mc = new MyCallback();
            mqttClient.setCallback(mc);
            mqttClient.disconnect();
            //System.out.println("zzz");
            Thread.sleep(1,1);
            //System.out.println("*wakes up*");
            mqttClient.reconnect();
            while(true){
                //System.out.println(MyCallback.flag);
                if(mc.flag){
                    break;
                }
            }
            System.out.println("Mqtt Client: Waiting for msg");
            
            ////disconnect the Mqtt Client
            mqttClient.disconnect();
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

class MyCallback implements MqttCallback{
    volatile Boolean flag = false;
    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub
        System.out.println("Connection lost");   
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("Rcv the following msg:"+topic+" :: "+message.toString());
        //flag = true;
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
    }

}