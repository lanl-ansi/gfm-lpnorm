package gov.lanl.nisac.fragility.config;

/**
 * A class responsible for proxy settings.
 */
public class ProxySettings {

    public static void setProxy() throws Exception {
        String httpProxyHost = System.getenv("HTTP_PROXY");
        String httpsProxyHost = System.getenv("HTTPS_PROXY");
        setProxyContent(httpProxyHost, "http");
        setProxyContent(httpsProxyHost, "https");
    }

    private static void setProxyContent(String env, String proxyhead) {
        if (env != null) {
            String hostString;
            int startHost = env.indexOf("@");
            if (startHost > 0) {
                hostString = env.substring(startHost + 1);
            } else {
                hostString = env;
            }
            int startPort = hostString.indexOf(":");
            String host = hostString.substring(0, startPort);
            String port = hostString.substring(startPort + 1);
            System.setProperty(proxyhead + ".proxyHost", host);
            System.setProperty(proxyhead + ".proxyPort", port);
        }
    }

}
