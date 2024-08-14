package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.constants.DeprecatedTransactionTypes;
import com.emerchantpay.gateway.api.exceptions.LimitsException;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

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

public class GenesisClient extends Request {

    private Configuration configuration;
    //Transaction id to Configuration if specific configuration is needed for any request
    private HashMap<String, Configuration> configurationMap = new HashMap<String, Configuration>();
    private List<Request> requestList = new ArrayList<Request>();

    //Maximum number of requests to be chained sync or async
    private static final int maxRequestNumber = 100;
    //Execute requests asynchronously in chunks of 10.
    private static final int asyncRequestsChunkSize = 10;

    private int connectTimeout = 60000;
    private int readTimeout = 60000;

    // Execute
    private Http http;
    //Transaction Id to response
    private ConcurrentHashMap<String, NodeWrapper> responseMap = new ConcurrentHashMap<String, NodeWrapper>();
    private Boolean asyncExecute = false;

    public GenesisClient(Configuration configuration, Request request) {

        super();
        this.configuration = configuration;
        this.requestList.add(request);
    }

    public GenesisClient(Configuration configuration, List<Request> requestList) {

        super();
        checkRequestsCount(requestList.size());
        this.configuration = configuration;
        this.requestList.addAll(requestList);
    }

    public GenesisClient(Map<Request, Configuration> requestWithConfigurationMap) {

        super();
        checkRequestsCount(requestWithConfigurationMap.size());
        for (Request request : requestWithConfigurationMap.keySet()) {
            this.configurationMap.put(request.getTransactionId(), requestWithConfigurationMap.get(request));
            this.requestList.add(request);
        }
    }

    public Boolean getAsyncExecute() {
        return asyncExecute;
    }

    public GenesisClient setAsyncExecute(Boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
        return this;
    }

    public static int getMaxRequestNumber() {
        return maxRequestNumber;
    }

    public GenesisClient debugMode(Boolean enabled) {
        if (configuration != null) {
            configuration.setDebugMode(enabled);
        }

        for (String transactionId : configurationMap.keySet()) {
            configurationMap.get(transactionId).setDebugMode(enabled);
        }
        return this;
    }

    public GenesisClient changeRequest(Request request) {
        this.requestList.clear();
        this.requestList.add(request);
        return this;
    }

    public GenesisClient addRequest(Request request) {
        checkRequestsCount(requestList.size() + 1);
        this.requestList.add(request);
        return this;
    }

    public GenesisClient addRequest(Request request, Configuration configuration) {
        checkRequestsCount(requestList.size() + 1);
        this.requestList.add(request);
        this.configurationMap.put(request.getTransactionId(), configuration);
        return this;
    }

    public TransactionGateway getTransaction() {
        return new TransactionGateway(getConfiguration(), getResponse());
    }

    public ConsumerGateway getConsumer() {
        return new ConsumerGateway(getConfiguration(), getResponse());
    }

    public TransactionGateway getTransaction(String transactionId) {
        return new TransactionGateway(getConfiguration(transactionId), getResponse(transactionId));
    }

    public ConsumerGateway getConsumer(String transactionId) {
        return new ConsumerGateway(getConfiguration(transactionId), getResponse(transactionId));
    }

    public Request execute() {
        if (asyncExecute) {
            executeAsync();
        } else {
            for (Request request : requestList) {
                execute(request, getConfiguration(request.getTransactionId()));
            }
        }
        return this;
    }

    private void executeAsync() {

        ArrayList<CompletableFuture> requestFutures = new ArrayList<CompletableFuture>();
        for (int requestIndex = 0; requestIndex < requestList.size(); requestIndex++) {
            Request request = requestList.get(requestIndex);
            //Clone configuration for each request so asynchronous execution doesn't throw errors
            Configuration requestConfig = getConfiguration(request.getTransactionId()).clone();
            // Using Lambda Expression
            CompletableFuture<Void> futureRequest = CompletableFuture.runAsync(() -> {
                execute(request, requestConfig);
            });
            requestFutures.add(futureRequest);
            //Execute CompletableFutures at the end of each chunk or end of the array
            if (((requestIndex + 1) % asyncRequestsChunkSize == 0) ||
                    requestIndex == requestList.size() - 1) {
                // Create a combined Future and wait until all are completed
                CompletableFuture.allOf(requestFutures.toArray(new CompletableFuture[requestFutures.size()]))
                        .join();
                //Clear list for next chunk
                requestFutures.clear();
            }
        }
    }

    private void execute(Request request, Configuration configuration) {

        switch ((request.getTransactionType() != null) ? request.getTransactionType() : "") {
            case "wpf_payment":
                configuration.setWpfEnabled(true);
                configuration.setTokenEnabled(false);

                if (configuration.getLanguage() != null) {
                    configuration.setAction(configuration.getLanguage() + "/wpf");
                } else {
                    configuration.setAction("wpf");
                }
                break;
            case "wpf_reconcile":
                configuration.setWpfEnabled(true);
                configuration.setTokenEnabled(false);
                configuration.setAction("wpf/reconcile");
                break;
            case "reconcile":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(true);
                configuration.setAction("reconcile");
                break;
            case "reconcile_by_date":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(true);
                configuration.setAction("reconcile/by_date");
                break;
            case "blacklist":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("blacklists");
                break;
            case "chargeback":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("chargebacks");
                break;
            case "chargeback_by_date":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("chargebacks/by_date");
                break;
            case "reports_fraud":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("fraud_reports");
                break;
            case "reports_fraud_by_date":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("fraud_reports/by_date");
                break;
            case "retrieval_requests":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("retrieval_requests");
                break;
            case "retrieval_requests_by_date":
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(false);
                configuration.setAction("retrieval_requests/by_date");
                break;
            default:
                configuration.setWpfEnabled(false);
                configuration.setTokenEnabled(true);
                configuration.setAction("process");
                break;
        }

        DeprecatedTransactionTypes.validate(request.getTransactionType());

        if (request.isConsumer()) {
            configuration.setWpfEnabled(false);
            configuration.setTokenEnabled(false);
            configuration.setAction("");
        }

        http = new Http(configuration);
        http.setConnectTimeout(connectTimeout);
        http.setReadTimeout(readTimeout);
        NodeWrapper response = http.postXML(configuration.getBaseUrl(), request);

        String requestIdentifier = getRequestIdentifier(request);
        responseMap.put(requestIdentifier, response);
    }

    public NodeWrapper getResponse() {
        //Return first element. Map isn't ordered but it's for compatibility when working with single request.
        Iterator<NodeWrapper> iterator = responseMap.values().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public NodeWrapper getResponse(String transactionId) {
        return responseMap.get(transactionId);
    }

    public List<NodeWrapper> getAllResponses() {
        return new ArrayList<NodeWrapper>(responseMap.values());
    }

    /**
     * Sets a specified timeout value, in milliseconds, to be used when opening a communications link to the resource
     * referenced by this GenesisClient. If the timeout expires before the connection can be established,
     * a java. net. SocketTimeoutException is raised. A timeout of zero is interpreted as an infinite timeout.
     *
     * @param timeout an int that specifies the connect timeout value in milliseconds.
     * @throws IllegalArgumentException if the timeout parameter is negative
     */
    public void setConnectTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        connectTimeout = timeout;
    }

    /**
     * Sets the read timeout to a specified timeout, in milliseconds. A non-zero value specifies the timeout when
     * reading from Input stream when a connection is established to a resource. If the timeout expires before there
     * is data available for read, a java. net. SocketTimeoutException is raised. A timeout of zero is interpreted as
     * an infinite timeout.
     *
     * @param timeout an int that specifies the timeout value to be used in milliseconds.
     * @throws IllegalArgumentException if the timeout parameter is negative
     */
    public void setReadTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        readTimeout = timeout;
    }

    private Configuration getConfiguration() {
        Iterator<Configuration> iterator = configurationMap.values().iterator();

        if (configuration != null) {
            return configuration;
        } else if (iterator.hasNext()) {
            //Return first element. Map isn't ordered but it's for compatibility when working with single request.
            return iterator.next();
        } else {
            return null;
        }
    }

    private Configuration getConfiguration(String transactionId) {
        if (configurationMap.containsKey(transactionId)) {
            return configurationMap.get(transactionId);
        } else {
            return configuration;
        }
    }

    private void checkRequestsCount(int requestsCount) {
        if (requestsCount > maxRequestNumber) {
            throw new LimitsException("Maximum requests number reached. Limit is: "
                    + maxRequestNumber);
        }
    }

    private String getRequestIdentifier(Request request) {
        //Match request by its transaction id/unique id, if present, otherwise use internally generated id so execution is not blocked.
        String requestIdentifier;
        if (request.getTransactionId() != null) {
            requestIdentifier = request.getTransactionId();
        } else if (request.getUniqueId() != null) {
            requestIdentifier = request.getUniqueId();
        } else {
            requestIdentifier = UUID.randomUUID().toString();
        }
        return requestIdentifier;
    }
}