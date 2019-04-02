/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Vedran Grgo Vatavuk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package hr.com.vgv.verano.http.mock;

import hr.com.vgv.verano.http.Dict;
import hr.com.vgv.verano.http.Wire;
import hr.com.vgv.verano.http.matchings.Matching;
import hr.com.vgv.verano.http.matchings.Stub;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.FirstOf;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Mocked wire.
 * @since 1.0
 */
public class MockWire implements Wire {

    /**
     * List of received requests.
     */
    private final List<Dict> received;

    /**
     * List of defined stubs.
     */
    private final Iterable<Stub> stubs;

    /**
     * Ctor.
     * @param stubs Stubs
     */
    public MockWire(final Stub... stubs) {
        this(new IterableOf<>(stubs));
    }

    /**
     * Ctor.
     * @param stubs Stubs
     */
    public MockWire(final Iterable<Stub> stubs) {
        this.stubs = stubs;
        this.received = new ArrayList<>(0);
    }

    @Override
    public final Dict send(final Dict request) {
        this.received.add(request);
        return new UncheckedScalar<>(
            new FirstOf<>(
                stub -> stub.applicable(request),
                this.stubs,
                () -> {
                    throw new AssertionError(
                        String.format("No stub found for request: %s", request)
                    );
                }
            )
        ).value().response();
    }

    /**
     * Assert defined matching condition against the wire.
     * @param matching Matching
     */
    public final void assertThat(final Matching matching) {
        final Collection<String> errors = this.closestMatch(matching);
        if (!errors.isEmpty()) {
            throw new AssertionError(errors.iterator().next());
        }
    }

    /**
     * Find closest match and return list of matching errors.
     * @param matching Matching
     * @return Collection Collection
     */
    private Collection<String> closestMatch(final Matching matching) {
        Collection<String> result = null;
        for (final Dict request : this.received) {
            final Collection<String> errors = matching.match(request);
            if (result == null) {
                result = errors;
            } else {
                if (errors.isEmpty()) {
                    result = errors;
                } else {
                    if (errors.size() < result.size()) {
                        result = errors;
                    }
                }
            }
        }
        return result;
    }
}