package ir.mahyco.p2p.config;


import com.tosan.novin.tools.encryption.ConsoleEncryptionUtility;
import com.tosan.novin.tools.propertiesmanager.cryptor.TosanCryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Sha.rezaei
 * @since 1/2/2021
 */
@Slf4j
public class EncryptedPropertySourcePlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    private static final String ENCRYPTED_SUFFIX = "@";
    private final TosanCryptor tosanCryptor;

    public EncryptedPropertySourcePlaceholderConfigurer(TosanCryptor tosanCryptor) {
        this.tosanCryptor = tosanCryptor;
    }

    public static String encryptByTosanCryptor(String text) {
        TosanCryptor tosanCryptor = new TosanCryptor(ConsoleEncryptionUtility.getEncryptionKey());
        return tosanCryptor.encryptText(text);
    }

    public static String decryptByTosanCryptor(String encText) {
        TosanCryptor tosanCryptor = new TosanCryptor(ConsoleEncryptionUtility.getEncryptionKey());
        return tosanCryptor.decryptText(encText);
    }

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties properties = super.mergeProperties();
        return decryptProperties(properties);
    }

    private Properties decryptProperties(Properties props) {

        Enumeration<?> propertyNames = props.propertyNames();

        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            String propertyValue = props.getProperty(propertyName);
            if (propertyName.endsWith(ENCRYPTED_SUFFIX)) {
                props.remove(propertyName);
                log.debug("decrypting property {} ...", propertyNames);
                propertyName = propertyName.substring(0, propertyName.length() - 1);
                try {
                    String convertedValue = "";
                    if (StringUtils.hasLength(propertyValue)) {
                        convertedValue = tosanCryptor.decryptText(propertyValue);
                    }
                    props.setProperty(propertyName, convertedValue);
                } catch (Exception e) {
                    log.warn("Could not decrypt property: {}, Exception: {} ", propertyName, e);
                    throw new RuntimeException("Could not decrypt property : " + propertyName + e.getMessage());
                }
            }
        }
        return props;
    }
}
