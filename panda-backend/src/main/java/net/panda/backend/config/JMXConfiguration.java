package net.panda.backend.config;

import net.panda.core.support.MapBuilder;
import net.sf.jstring.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import java.io.IOException;

@Configuration
public class JMXConfiguration {

    @Autowired
    private Strings strings;

    @Bean
    public Object exporter() throws IOException {
        MBeanExporter exporter = new MBeanExporter();
        exporter.setBeans(MapBuilder.<String, Object>create()
                .with("configuration:name=strings", strings)
                .get());
        return exporter;
    }

}
