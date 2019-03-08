package plu.yellow.reversi.model;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class LocalServer {
    //base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8880/reversi/";

    public static HttpServer startServer(){
        //Scan package for resources.
        final ResourceConfig rc =
                new ResourceConfig().packages("plu.yellow.reversi.model.resources");

        //create and start a new instance of grizzly http server
        //exposing the Jersey application at BASE_URI

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),  rc);
    }

    public static void main(String[] args){

        final HttpServer server = startServer();

    }

}
