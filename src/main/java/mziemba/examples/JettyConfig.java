package mziemba.examples;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jetty9.InstrumentedHandler;
import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JettyConfig {

    private static final String METRICS_PREFIX = "jettyServer";

    @Autowired
    private MetricRegistry metricRegistry;

    @Primary
    @Bean
    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory(
            @Value("${server.port}") final int port) {
        final JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory(port);
        factory.addServerCustomizers(this::jettyServer);
        return factory;
    }

    private void jettyServer(Server server) {
        final InstrumentedHandler instrumentedHandler = new InstrumentedHandler(metricRegistry, METRICS_PREFIX);
        instrumentedHandler.setHandler(server.getHandler());
        instrumentedHandler.setServer(server);
        server.setHandler(instrumentedHandler);
    }
}
