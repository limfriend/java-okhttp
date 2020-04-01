package io.opentracing.contrib.okhttp3;

import java.io.IOException;

import org.junit.Before;

import okhttp3.OkHttpClient;

/**
 * @author Pavol Loffay
 */
public class TracingCallFactoryTest extends AbstractOkHttpTest {

    static {
        System.setProperty(SkipPatternUtil.SKIP_PATTERN_PROPERTY_KEY, "/health*");
    }

    public TracingCallFactoryTest() {
        super(new TracingCallFactory(new OkHttpClient(), AbstractOkHttpTest.mockTracer));
    }

    @Before
    public void before() throws IOException {
        super.before();

    }
}
