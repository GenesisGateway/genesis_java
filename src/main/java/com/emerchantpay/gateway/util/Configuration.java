package com.emerchantpay.gateway.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.emerchantpay.gateway.api.constants.*;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import lombok.Getter;
import lombok.Setter;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

/**
 * Configuration class for handling global settings for Genesis API requests.
 * This class encapsulates essential configuration options such as environment, endpoint,
 * API versions, authentication details, and debug settings. It offers various constructors
 * to support specific configurations for consumer, FX, and SCA Checker APIs.
 *
 * <p>
 * Usage:
 * Configure different endpoints, versions, and credentials for Genesis services
 * through a single instance of this class. Enables debugging and parameterized URLs
 * for enhanced logging and testing capabilities.
 * </p>
 *
 * @see Environments
 * @see Endpoints
 * @see GenesisException
 * @see Logger
 */
public class Configuration implements Serializable, Cloneable {

    /**
     * Specifies the environment in which the Genesis API will operate (e.g., production or staging sandbox).
     */
    @Getter
    private final Environments environment;

    /**
     * The endpoint to which requests will be directed, customized for the specific API in use.
     */
    @Getter
    private final Endpoints endpoint;

    /**
     * Username for API authentication.
     */
    @Getter
    @Setter
    private String username;

    /**
     * Password for API authentication.
     */
    @Getter
    @Setter
    private String password;

    /**
     * Token or identifier for virtual terminal defined at Genesis.
     */
    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    //TODO: Convert it to enum
    private String action;
    /**
     * Indicates if we need to pass Virtual Terminal token to Genesis.
     * <p>Default: {@code true}
     */
    @Getter
    @Setter
    private Boolean tokenEnabled = true;
    /**
     * Indicates if smart routing is enabled.
     * <p>Default: {@code false}
     */
    @Getter
    @Setter
    private boolean forceSmartRouting = false;
    @Getter
    @Setter
    private Boolean wpfEnabled = false;
    private Boolean enabledDebugMode = false;
    @Getter
    @Setter
    private Locale language;
    private String consumerAPIVersion;
    private String fxAPIVersion;
    @Getter
    private String scaCheckerAPIVersion;
    private QueryString queryParams;

    public Configuration clone() {
        try {
            return (Configuration) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Logger logger;
    private static final String[] availableFXAPIVersions = new String[]{"v1"};
    private static final String[] availableConsumerAPIVersions = new String[]{"v1"};
    private static final String[] availableScaAPIVersions = new String[]{"v1"};
    private static final String pathSeparator = "/";


    static {
        logger = Logger.getLogger("Genesis");
        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
    }

    public Configuration(Environments environment, Endpoints endpoint) {
        this.environment = environment;
        this.endpoint = endpoint;
    }

    // Consumer
    public Configuration(Environments environment, Endpoints endpoint, ConsumerEndpoints consumerEndpoint, String version) {
        List<String> availableVersionsList = Arrays.asList(availableConsumerAPIVersions);
        if (availableVersionsList.contains(version)) {
            this.consumerAPIVersion = version;
            this.environment = environment;
            this.endpoint = new Endpoints(endpoint.getEndpointName() + pathSeparator + consumerAPIVersion + consumerEndpoint.getEndpointName());
        } else {
            throw new GenesisException(ErrorMessages.INVALID_CONSUMER_API_VERSION + availableVersionsList);
        }
    }

    // FX
    public Configuration(Environments environment, Endpoints endpoint, FXEndpoints fxEndpoints, String version) {
        List<String> availableVersionsList = Arrays.asList(availableFXAPIVersions);
        if (availableVersionsList.contains(version)) {
            this.fxAPIVersion = version;
            this.environment = environment;
            this.endpoint = new Endpoints(endpoint.getEndpointName() + pathSeparator + fxAPIVersion + fxEndpoints.getEndpointName());
        } else {
            throw new GenesisException(ErrorMessages.INVALID_FX_API_VERSION + availableVersionsList);
        }
    }

    // SCA Checker
    public Configuration(Environments environment, Endpoints endpoint, String version) {
        this.scaCheckerAPIVersion = version;
        this.environment = environment;
        this.endpoint = new Endpoints(endpoint.getEndpointName() + pathSeparator + version);
    }

    public void setLogger(Logger log) {
        logger = log;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setDebugMode(Boolean enabled) {
        this.enabledDebugMode = enabled;
    }

    public Boolean isDebugModeEnabled() {
        return this.enabledDebugMode;
    }

    public Configuration addQueryParameter(String paramName, Object paramValue){
        if(queryParams == null){
            queryParams = new QueryString();
        }
        queryParams.append(paramName, paramValue);
        return this;
    }

    public Configuration clearQueryParameters(){
        queryParams = null;
        return this;
    }

    public String getBaseUrl() {
        String queryParamStr = queryParams != null ? "?" + queryParams : "";
        EndpointApiTypes service = getEndpointApiType();
        String fullSubdomain = getFullSubDomain(service);

        return buildUrl(fullSubdomain, queryParamStr);
    }

    private EndpointApiTypes getEndpointApiType() {
        if (getTokenEnabled()) {
            return EndpointApiTypes.GATE;
        } else {
            if (getWpfEnabled()) {
                return EndpointApiTypes.WPF;
            } else if (EndpointActions.TRANSACTIONS.equals(getAction()) || EndpointActions.RECONCILE.equals(getAction())) {
                return EndpointApiTypes.API;
            } else {
                // This can occur when we have ThreedsV2ContinueRequest, it is a non-token request
                return EndpointApiTypes.GATE;
            }
        }
    }

    private String getFullSubDomain(EndpointApiTypes service) {
        switch (environment.getEnvironmentName()) {
            case "prod":
                if (Environments.SERVICES_WITH_PROD_SUBDOMAIN.contains(service)) {
                    return environment.getEnvironmentName() + "." + service.getUrlPart();
                } else {
                    return service.getUrlPart();
                }

            case "staging":
                return environment.getEnvironmentName() + "." + service.getUrlPart();

            default:
                // Allow users flexibility with defining subdomains
                return environment.getEnvironmentName();
        }
    }

    private String buildUrl(String fullSubDomain, String queryParamStr) {
        return String.format("https://%s.%s%s%s%s",
                fullSubDomain,
                endpoint.getEndpointName(),
                pathSeparator,
                getAction(),
                getTokenEnabled() ? pathSeparator + token : ""
        ) + queryParamStr;
    }

    public void setConsumerAPIVersion(String version) {
        List<String> availableVersionsList = Arrays.asList(availableConsumerAPIVersions);
        if (availableVersionsList.contains(version)) {
            this.consumerAPIVersion = version;
        } else {
            throw new GenesisException(ErrorMessages.INVALID_CONSUMER_API_VERSION + availableVersionsList);
        }
    }

    public String getConsumerVersion() {
        return consumerAPIVersion;
    }

    public void setFXAPIVersion(String version) {
        List<String> availableVersionsList = Arrays.asList(availableFXAPIVersions);
        if (availableVersionsList.contains(version)) {
            this.fxAPIVersion = version;
        } else {
            throw new GenesisException(ErrorMessages.INVALID_FX_API_VERSION + availableVersionsList);
        }
    }

    public String getFXAPIVersion() {
        return fxAPIVersion;
    }

    public void setScaCheckerAPIVersion(String version) {
        List<String> availableVersionsList = Arrays.asList(availableScaAPIVersions);
        if (availableVersionsList.contains(version)) {
            this.scaCheckerAPIVersion = version;
        } else {
            throw new GenesisException(ErrorMessages.INVALID_SCA_API_VERSION + availableVersionsList);
        }
    }

}
