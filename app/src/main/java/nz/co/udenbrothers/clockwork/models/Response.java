package nz.co.udenbrothers.clockwork.models;

public class Response {
    public String content;
    public int statusCode;

    public Response(String c, int sc){
        content = c;
        statusCode = sc;
    }
}

