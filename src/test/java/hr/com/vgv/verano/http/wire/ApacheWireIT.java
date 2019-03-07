package hr.com.vgv.verano.http.wire;

import hr.com.vgv.verano.http.request.Body;
import hr.com.vgv.verano.http.request.methods.Post;
import hr.com.vgv.verano.http.response.ExpectedStatus;
import hr.com.vgv.verano.http.response.FailWith;
import hr.com.vgv.verano.http.response.JsonResponse;
import hr.com.vgv.verano.http.response.Response;
import org.junit.Ignore;
import org.junit.Test;

public class ApacheWireIT
{
    @Test
    @Ignore
    public void postReq()
    {
        new JsonResponse(
            new ApacheWire("http://example.com"),
            new Post(new Body("Hello World"))
        ).json();
    }

    @Test
    public void googlePage()
    {
        new Response(
            new ApacheWire("http://google.com"),
            new ExpectedStatus(301, new FailWith("Cannot fetch from Google"))
        ).touch();
    }
}
