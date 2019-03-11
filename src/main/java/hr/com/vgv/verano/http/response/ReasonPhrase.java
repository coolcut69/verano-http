/**
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
package hr.com.vgv.verano.http.response;

import org.cactoos.Text;

import hr.com.vgv.verano.http.Dict;
import hr.com.vgv.verano.http.DictInput;
import hr.com.vgv.verano.http.KvpOf;

/**
 * Http reason phrase.
 * @since 1.0
 */
public class ReasonPhrase extends DictInput.Simple {

    /**
     * Reason phrase key in dictionary.
     */
    private static final String KEY = "reason";

    /**
     * Ctor.
     * @param reason Reason phrase
     */
    public ReasonPhrase(final String reason)
    {
        super(new KvpOf(ReasonPhrase.KEY, reason));
    }

    /**
     * Reason phrase from response.
     */
    public static class Of implements Text
    {
        /**
         * Response.
         */
        private final Dict response;

        /**
         * Ctor.
         * @param response Response
         */
        public Of(final Dict response)
        {
            this.response = response;
        }

        @Override
        public final String asString()
        {
            return this.response.get(ReasonPhrase.KEY, "");
        }
    }
}
