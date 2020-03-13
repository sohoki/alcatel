package com.sohoki.backoffice.alcatel.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;

public class UserConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserConfig.class);

    /**
     * Login of the user.<br>
     * Unique in OpenTouch server.
     */
    private final String login;

    /**
     * Name of the user (optional).
     */
    private final String name;

    /**
     * Password of the user.<br>
     * It is the GUI password in OpenTouch user configuration.
     */
    private final String password;

    /**
     * Phone number of the user.<br>
     * It is the company phone in OpenTouch user configuration.
     */
    private final String number;

    /**
     * Device number of the user.<br>
     * It is the number of one device of the user in OpenTouch user configuration.<br>
     * It is used to place a call initiated from the user (ie MakeCall request).
     */
    private final String deskPhone;

    private UserConfig(String login, String name, String password, String number, String deskPhone) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.number = number;
        this.deskPhone = deskPhone;
    }

    public static UserConfig create(String login) throws OTRestClientException {
        Properties userProperties = ConfigReader.load(login + ".properties");
        UserConfig userConfig = new UserConfig(userProperties.getProperty("login"), userProperties.getProperty("name"), userProperties.getProperty("password"),
                userProperties.getProperty("number"), userProperties.getProperty("deskPhone"));
        LOGGER.debug("Create user: {}", userConfig);
        return userConfig;
    }
    
    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public String getDeskPhone() {
        return deskPhone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserConfig [login=");
        builder.append(login);
        if (name != null) {
            builder.append(", name=");
            builder.append(name);
        }
        builder.append(", password=");
        builder.append(password);
        builder.append(", number=");
        builder.append(number);
        builder.append(", deskPhone=");
        builder.append(deskPhone);
        builder.append("]");
        return builder.toString();
    }
}
