package nz.co.udenbrothers.clockwork.serverObjects;

public class Response {

    public String content;
    public int statusCode;
    public String url;

    public Response(String c, int sc, String url){
        content = c;
        statusCode = sc;
        this.url = url;
    }
}

