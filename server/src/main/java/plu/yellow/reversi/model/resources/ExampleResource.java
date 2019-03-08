package plu.yellow.reversi.model.resources;

import javax.ws.rs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import plu.yellow.reversi.model.MessageObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Paul on 3/7/2017.
 */
@Path("test")
public class ExampleResource {
    private MessageObject message;

    public ExampleResource() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = this.getClass().getResourceAsStream("/Message.json");
        message = mapper.readValue(stream, MessageObject.class);
        stream.close();

    }

    @Path("get")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return message.getmsg();
    }

    @Path("set")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String setMessage(String m) {
        message.setmsg(m);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new FileOutputStream(new File("server/src/main/resources/Message.json")), message);
            return "Message set to: " + message.getmsg();
        } catch (IOException e) {
            return "File Write failed: " + e;
        }
    }

}
