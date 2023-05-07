package ir.mahyco.p2p.config;

public class InvalidConfigurationException extends Exception {

    public InvalidConfigurationException(){super();}

    public InvalidConfigurationException(String message){super(message);}

    public InvalidConfigurationException(Throwable cause){super(cause);}

    public InvalidConfigurationException(Throwable cause , String message){super(message , cause);}
}
