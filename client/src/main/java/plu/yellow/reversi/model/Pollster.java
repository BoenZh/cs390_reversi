
package plu.yellow.reversi.model;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by Paul on 5/1/2017.
 */
public class Pollster implements Runnable {
    private Client client;

    public Pollster() {
        client = ClientBuilder.newClient();
    }

    public void run() {
        boolean done = false;

        while(!done) {
            //client.
        }
    }
}

