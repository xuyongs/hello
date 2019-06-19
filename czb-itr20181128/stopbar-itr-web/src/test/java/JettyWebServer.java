import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyWebServer {
    public static void main(String[] args) throws Exception{
        // 工程名称
        String projectName = "stopbar-itr-web";
//        System.out.println("Context:" + projectName);
        // web资源路径
        String WebRoot = "src/main/webapp";
        // 端口号
        int port = 8081;
        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();
//        webapp.setDefaultsDescriptor("src/main/resources/webdefault.xml");
        // 设置URL访问名
        webapp.setContextPath("/" + projectName);
        webapp.setWar(WebRoot);
        server.setHandler(webapp);
        server.start();
        server.join();
    }
}
