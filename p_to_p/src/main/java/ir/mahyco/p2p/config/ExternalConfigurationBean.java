package ir.mahyco.p2p.config;

import com.tosan.novin.tools.encryption.ConsoleEncryptionUtility;
import com.tosan.novin.tools.propertiesmanager.cryptor.TosanCryptor;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

@Configuration
@Order(1)
public class ExternalConfigurationBean {
    @Bean
    public TosanCryptor tosanCryptor() {
        return new TosanCryptor(ConsoleEncryptionUtility.getEncryptionKey());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurerforBroker() throws InvalidConfigurationException, IOException, InvalidConfigurationException {
        PropertySourcesPlaceholderConfigurer configurer = new EncryptedPropertySourcePlaceholderConfigurer(
                new TosanCryptor(ConsoleEncryptionUtility.getEncryptionKey())
        );

/*        var p2pConfig = new FileSystemResource(new File(PropertiesLoader.getManaHome() + "conf/p2pConfigs.properties"));
        var p2pDbConfig = new FileSystemResource(new File(PropertiesLoader.getManaHome() + "conf/p2pDbConfigs.properties"));*/
        var p2pConfig = new ClassPathResource(PropertiesLoader.getP2PHome() + "/application.properties");
/*        configurer.setLocations(p2pConfig);
        configurer.setIgnoreResourceNotFound(true);
        configurer.setLocalOverride(true);*/
        return configurer;
    }
}
