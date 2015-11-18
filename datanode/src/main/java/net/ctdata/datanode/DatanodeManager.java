package net.ctdata.datanode;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.Listeners.AddNodeListener;
import net.ctdata.common.Queue.Listeners.ObservationListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.dbconnectors.ObservationsConnector;
import net.ctdata.datanode.dbconnectors.UserSensorsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by aditi on 14/11/15.
 * See for Log4j Initialisation: http://howtodoinjava.com/2013/04/08/how-to-configure-log4j-using-properties-file/,
 * http://www.petrikainulainen.net/programming/gradle/getting-started-with-gradle-dependency-management/
 */
public class DatanodeManager {

        public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
            AddNode addNode = new AddNode();
            addNode.setNodeURL(UUID.randomUUID() + "./raspberry.net");

            final RabbitMqConnection conn = new RabbitMqConnection("amqp://localhost");
            conn.RegisterListener(new AddNodeListener() {
                @Override
                public void HandleMessage(AddNode message) {
                    // insert AddNode data into User_Sensors table
                    System.out.println("Received AddNode messgae..");
                    UserSensorsConnector userSensorsConnector = new UserSensorsConnector();
                    UserSensors userSensors = new UserSensors("admin", message.getNodeURL());
                    int i = userSensorsConnector.insertInto(userSensors);

                    if(i!= DatanodeConstants.FAILURE)
                        System.out.println("Successfully added sensor node with url "+ message.getNodeURL() +" for user admin");
                }
            });

            Observation obs = new Observation();
            obs.setRaspberryNode(UUID.randomUUID());
            obs.setSensor(1);
            obs.setObservation(35.35);
            obs.setTime(DateTime.now());
            obs.setLatitude(12.678);
            obs.setLongitude(-125.45);

            conn.RegisterListener(new ObservationListener() {
                @Override
                public void HandleMessage(Observation message) {
                    //insert Observation into the Observations table
                    System.out.println("Received Observation data message..");
                    ObservationsConnector obsConn = new ObservationsConnector();
                    Observations obsData = new Observations();
                    obsData.setRaspberryNode(message.getRaspberryNode());
                    obsData.setSensorId(message.getSensor());
                    obsData.setObservationData(message.getObservation());
                    obsData.setObservationTime(DateTimeConversions.convertDateTimeToString(message.getTime()));
                    int flag = obsConn.insertInto(obsData);

                    if(flag != DatanodeConstants.FAILURE)
                        System.out.println("Successfully added observavation for " +
                                "raspberry node " + obsData.getRaspberryNode() + " , sensor id "+ obsData.getSensorId());
                }
            });
            conn.SendMessage(addNode);
            conn.SendMessage(obs);

        }

}
