
package plu.yellow.reversi.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Paul on 5/1/2017.
 */
public class ServerTest {

    public static void main(String[] args) {

        HttpServer h = LocalServer.startServer();
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target("http://localhost:8080/reversi/test/get");
        String responseMsg = target.request(MediaType.TEXT_PLAIN).get(String.class);
        System.out.println(responseMsg);

        target = c.target("http://localhost:8080/reversi/test/set");
        Response response = target.request().put(Entity.text("Test Message"));
        responseMsg = response.readEntity(String.class);
        System.out.println(responseMsg);

        target = c.target("http://localhost:8080/reversi/test/get");
        responseMsg = target.request(MediaType.TEXT_PLAIN).get(String.class);
        System.out.println(responseMsg);

        h.shutdownNow();


        MessageObject message = new MessageObject("TEST");

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new FileOutputStream(new File("server/src/main/resources/Message.json")), message);
            System.out.println( "Message set to: " + message.getmsg());
        } catch (IOException e) {
            System.out.println("File Write failed: " + e);
        }

        try {
            Scanner s = new Scanner(new File("server/src/main/resources/Message.json"));
            System.out.println(s.nextLine());
        } catch (IOException e) {}

    }
}

