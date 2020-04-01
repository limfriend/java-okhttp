package io.opentracing.contrib.okhttp3;

import java.util.regex.Pattern;

public class SkipPatternUtil {
    private static Pattern SKIP_PATTERN = null;

    private static final Pattern EMPTY_SKIP_PATTERN = Pattern.compile("emptyPartter");

    public static final String SKIP_PATTERN_PROPERTY_KEY = "opentracing.okhttp.skipPattern";
    public static boolean isTraced(String url) {
        if (SKIP_PATTERN == null) {
            String okhhtpSkipPattern = System.getProperty(SKIP_PATTERN_PROPERTY_KEY);
            if (okhhtpSkipPattern != null && okhhtpSkipPattern.length() > 0) {
                Pattern skipPatter = Pattern.compile(okhhtpSkipPattern);
                // dubbo check
                if (SKIP_PATTERN == null) {
                    synchronized (SkipPatternUtil.class) {
                        SKIP_PATTERN = skipPatter;
                    }
                }
            } else {
                // dubbo check
                if (SKIP_PATTERN == null) {
                    synchronized (SkipPatternUtil.class) {
                        SKIP_PATTERN = EMPTY_SKIP_PATTERN;
                    }
                }
            }
        }
        // dono't need to use equals
        if (EMPTY_SKIP_PATTERN == SKIP_PATTERN) {
            return true;
        }
        // skip URLs matching skip pattern
        //  e.g. pattern is defined as '/health|/status' then URL 'http://localhost:5000/context/health' won't be traced
        return !SKIP_PATTERN.matcher(url).find();
    }
}
