package hr.com.vgv.verano.http.response;

import hr.com.vgv.verano.http.request.Body;
import hr.com.vgv.verano.http.request.RequestUri;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.cactoos.Text;
import org.cactoos.collection.CollectionOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

import hr.com.vgv.verano.http.Assertion;
import hr.com.vgv.verano.http.Dict;

public class ExpectedStatus implements Assertion
{
    private final Iterable<Integer> statuses;

    private final UncheckedText message;

    public ExpectedStatus(int status, Text message) {
        this(new IterableOf<>(status), message);
    }

    public ExpectedStatus(Integer status, Integer... additional) {
        this(new Joined<>(status, new IterableOf<>(additional)), new TextOf(""));
    }

    public ExpectedStatus(Iterable<Integer> statuses, Text message)
    {
        this.statuses = statuses;
        this.message = new UncheckedText(message);
    }

    @Override
    public final void test(Dict response)
    {
        final int status = new Status.Of(response).intValue();
        if (!new CollectionOf<>(this.statuses).contains(status)) {
            final String msg = String.format(
                "%s\n%s",
                this.message.asString(),
                String.format(
                    "Received response with status %d, instead of %d.\nReason: %s\nUrl: %s\nBody: %s",
                    status,
                    this.statuses.iterator().next(),
                    new ReasonPhrase.Of(response).asString(),
                    new RequestUri.Of(response).asString(),
                    new Body.Of(response).asString()
                )
            );
            throw new UncheckedIOException(new IOException(msg));
        }
    }
}
