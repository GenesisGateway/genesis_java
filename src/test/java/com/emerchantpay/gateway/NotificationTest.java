package com.emerchantpay.gateway;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.model.Notification;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.SHA1Hasher;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NotificationTest {

    private NotificationGateway notificationGtw;
    private Notification notification;
    private Configuration configuration;
    private Map<String, String> notificationParams;

    private RequestBuilder expectedResponse;

    @Before
    public void createNotification() throws MalformedURLException, UnsupportedEncodingException,
            NoSuchAlgorithmException {
        notificationGtw = mock(NotificationGateway.class);
        notification = mock(Notification.class);
        expectedResponse = mock(RequestBuilder.class);

        doNothing().when(notificationGtw).parseNotification(isA(Map.class));
        doNothing().when(notificationGtw).initReconciliation();
        doNothing().when(notificationGtw).generateResponse();

        //Create test configuration
        configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
        configuration.setUsername("test");
        configuration.setPassword("test");
        configuration.setToken("test");

        //Create test notification params
        notificationParams = new HashMap<String, String>();
        notificationParams.put("transaction_type", TransactionTypes.SALE_3D);
        notificationParams.put("terminal_token", configuration.getToken());
        notificationParams.put("status", "approved");
        notificationParams.put("amount", "10");
        notificationParams.put("eci", "05");
        notificationParams.put("avs_response_code", "5I");
        notificationParams.put("avs_response_text", "Response+provided+by+issuer+processor%3B+ Address+information+not+verified");
    }

    @Test
    public void testAPINotification() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        when(notificationGtw.getNotification()).thenReturn(notification);
        when(notificationGtw.isAuthentic()).thenReturn(true);
        when(notificationGtw.isApiNotification()).thenReturn(true);
        when(notificationGtw.isWPFNotification()).thenReturn(false);
        when(notificationGtw.getResponse()).thenReturn(expectedResponse);

        assertTrue(notificationGtw.isAuthentic());
        assertTrue(notificationGtw.isApiNotification());
        assertFalse(notificationGtw.isWPFNotification());
        assertEquals(notificationGtw.getResponse(), expectedResponse);

        verify(notificationGtw).isAuthentic();
        verify(notificationGtw).isApiNotification();
        verify(notificationGtw).isWPFNotification();
        verify(notificationGtw).getResponse();
        verifyNoMoreInteractions(notificationGtw);
    }

    @Test
    public void testAPINotificationParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String uniqueId = new StringUtils().generateUID();;
        String transactionId = new StringUtils().generateUID();
        notificationParams.put("unique_id", uniqueId);
        notificationParams.put("signature", SHA1Hasher.SHA1(uniqueId + configuration.getPassword()));

        NotificationGateway notification = new NotificationGateway(configuration, notificationParams);

        assertTrue(notification.isAuthentic());
        assertTrue(notification.isApiNotification());
    }

    @Test
    public void testWPFNotification() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        when(notificationGtw.getNotification()).thenReturn(notification);
        when(notificationGtw.isAuthentic()).thenReturn(true);
        when(notificationGtw.isApiNotification()).thenReturn(false);
        when(notificationGtw.isWPFNotification()).thenReturn(true);
        when(notificationGtw.getResponse()).thenReturn(expectedResponse);

        assertEquals(notificationGtw.getNotification(), notification);
        assertTrue(notificationGtw.isAuthentic());
        assertFalse(notificationGtw.isApiNotification());
        assertTrue(notificationGtw.isWPFNotification());
        assertEquals(notificationGtw.getResponse(), expectedResponse);

        verify(notificationGtw).getNotification();
        verify(notificationGtw).isAuthentic();
        verify(notificationGtw).isApiNotification();
        verify(notificationGtw).isWPFNotification();
        verify(notificationGtw).getResponse();
        verifyNoMoreInteractions(notificationGtw);
    }

    @Test
    public void testWPFNotificationParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String uniqueId = new StringUtils().generateUID();;
        String transactionId = new StringUtils().generateUID();
        notificationParams.put("wpf_unique_id", uniqueId);
        notificationParams.put("signature", SHA1Hasher.SHA1(uniqueId + configuration.getPassword()));

        NotificationGateway notification = new NotificationGateway(configuration, notificationParams);

        assertTrue(notification.isAuthentic());
        assertTrue(notification.isWPFNotification());
    }

    @Test(expected = GenesisException.class)
    public void testNonAuthenticNotification() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        NotificationGateway notification = new NotificationGateway(configuration, notificationParams);
    }

    @Test(expected = GenesisException.class)
    public void testInvalidNotification() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        GenesisException exception = new GenesisException(errorCode, "Invalid Genesis Notification!", new Throwable());

        when(notificationGtw.getNotification()).thenReturn(notification);
        when(notificationGtw.isAuthentic() == false).thenThrow(exception);
        when(notificationGtw.isApiNotification()).thenReturn(true);
        when(notificationGtw.isWPFNotification()).thenReturn(false);
        when(notificationGtw.getResponse()).thenReturn(expectedResponse);

        assertEquals(notificationGtw.getNotification(), notification);
        assertFalse(notificationGtw.isAuthentic());
        assertTrue(notificationGtw.isApiNotification());
        assertFalse(notificationGtw.isWPFNotification());
        assertEquals(notificationGtw.getResponse(), expectedResponse.toXML());

        verify(notificationGtw).getNotification();
        verify(notificationGtw).isAuthentic();
        verify(notificationGtw).isApiNotification();
        verify(notificationGtw).isWPFNotification();
        verify(notificationGtw).getResponse();
        verifyNoMoreInteractions(notificationGtw);
    }

    @Test(expected = GenesisException.class)
    public void testNotificationWithMissingParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Integer errorCode = ErrorCodes.INPUT_DATA_MISSING_ERROR.getCode();
        GenesisException exception = new GenesisException(errorCode, ErrorCodes.getErrorDescription(errorCode), new Throwable());

        when(notificationGtw.getNotification()).thenReturn(notification);
        when(notificationGtw.getNotification().getUniqueId()).thenReturn(null);
        when(notificationGtw.getNotification().getSignature()).thenReturn(null);
        when(notificationGtw.getNotification().getUniqueId() == null ||
                notificationGtw.getNotification().getSignature() == null).thenThrow(exception);
        when(notificationGtw.isApiNotification()).thenReturn(true);
        when(notificationGtw.isWPFNotification()).thenReturn(false);
        when(notificationGtw.getResponse()).thenReturn(expectedResponse);

        assertEquals(notificationGtw.getNotification(), notification);
        assertNull(notificationGtw.getNotification().getUniqueId());
        assertNull(notificationGtw.getNotification().getSignature());
        assertFalse(notificationGtw.isAuthentic());
        assertTrue(notificationGtw.isApiNotification());
        assertFalse(notificationGtw.isWPFNotification());
        assertEquals(notificationGtw.getResponse(), expectedResponse);

        verify(notificationGtw).getNotification();
        verify(notificationGtw).getNotification().getUniqueId();
        verify(notificationGtw).getNotification().getSignature();
        verify(notificationGtw).isAuthentic();
        verify(notificationGtw).isApiNotification();
        verify(notificationGtw).isWPFNotification();
        verify(notificationGtw).getResponse();
        verifyNoMoreInteractions(notificationGtw);
    }
}