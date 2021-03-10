package com.dbbrowser.routing.holder;

import com.dbbrowser.routing.token.ConnConfig;
import org.springframework.util.Assert;

public class ConnContextHolder {
    private static final ThreadLocal<ConnConfig> context = new ThreadLocal<>();

    public static void set(ConnConfig connectionId) {
        Assert.notNull(connectionId, "Connection id cannot be null");
        context.set(connectionId);
    }

    public static ConnConfig getClientConnectionId() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
