package me.bob.routeUtil;

public class RoutingDataSourceContext implements AutoCloseable {

    static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static String getDataSource() {
        String key = THREAD_LOCAL.get();
        return key == null ? "master" : key;
    }

    public RoutingDataSourceContext(String key) {
        THREAD_LOCAL.set(key);
    }

    @Override
    public void close() {
        THREAD_LOCAL.remove();
    }
}
