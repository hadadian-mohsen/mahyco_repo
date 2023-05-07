package ir.mahyco.p2p.config;

import com.tosan.novin.tools.propertiesmanager.validator.InvalidPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class PropertiesLoader {

    private static String PROPERTY_P2P_HOME = "P2P_HOME";

    public static void setP2PHomeVariableName(String p2pHomeVariableName) {
        PROPERTY_P2P_HOME = p2pHomeVariableName;
    }


    private static void handleException(Throwable throwable, String propKey) {
        if (throwable instanceof InvalidConfigurationException) {
            log.warn("Failed to get P2P_HOME environment variable,\n so unable to get value for key='{}' from p2p properties file.", propKey);
        } else if (throwable instanceof IOException) {
            log.warn("ManaConfigs.properties file does not exist or could not load it,\n so unable to get value for key='{}' from p2p properties file.", propKey);
        } else if (throwable instanceof NumberFormatException) {
            log.warn("Couldn't convert the value to Integer \n so unable to get value for key='{}' from p2p properties file.", propKey);
        } else if (throwable instanceof InvalidPropertyException) {
            log.warn("p2p config validation failed, check p2pConfig.properties or p2pDbConfig.properties: {}", throwable.getMessage());
        } else {
            log.error("Unexpected error is occurred so unable to get value for key='{}' from p2p properties file.", propKey, throwable);
        }
    }

    public static String getP2PHome() throws InvalidConfigurationException, IOException {
        String env = System.getenv(PROPERTY_P2P_HOME);
        if (env == null) {
            throw new InvalidConfigurationException("NoHomeError");
        }

        StringBuilder builder = new StringBuilder(env);
        if (!env.endsWith("/") && !env.endsWith("\\")) {
            builder.append("/");
        }

        return builder.toString();
    }
}
