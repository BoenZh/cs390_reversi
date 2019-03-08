package plu.yellow.reversi.test;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import plu.yellow.reversi.model.LocalServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Created by Paul on 3/7/2017.
 */
public class TestExample {

    private static HttpServer server = null;
    private static Client c = null;

    @BeforeClass
    public static void startServer() {
       server = LocalServer.startServer();

        c = ClientBuilder.newClient();
    }

    @AfterClass
    public static void stopServer() {
        c.close();
        server.shutdownNow();
    }

    @Test
    public void testExamplePlainText() {
        WebTarget target = c.target("http://localhost:8880/reversi/test/get");
        String responseMsg = target.request(MediaType.TEXT_PLAIN).get(String.class);
        Assert.assertEquals("Default", responseMsg);

    }


    /*
    @Test
    public void testSendRecieve() {
        WebTarget target = c.target("http://localhost:8880/reversi/test/set");
        target.request().post(Entity.text("Message!"));


        target = c.target("http://localhost:8880/reversi/test/get");
        String responseMsg = target.request().get(String.class);
        Assert.assertEquals("Message!", responseMsg);

    }
    */
}
