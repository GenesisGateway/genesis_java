package com.emerchantpay.gateway.api;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @license     http://opensource.org/licenses/MIT The MIT License
 */

import java.util.HashMap;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

public abstract class RequestRoot {

    private GenesisValidator validator;

    //Base Attributes
    private RequestBuilder baseAttrRequestBuilder;
    private HashMap<String, String> baseAttrParamsMap;

    //Billing Address Attributes
    private RequestBuilder billAddrAttrRequestBuilder;
    private HashMap<String, String> billAddrAttrParamsMap;

    //Shipping Address Attributes
    private RequestBuilder shipAddrAttrRequestBuilder;
    private HashMap<String, String> shipAddrAttrParamsMap;

    //Customer Info Attributes
    private RequestBuilder customerInfoAttrRequestBuilder;
    private HashMap<String, String> customerInfoAttrParamsMap;

    //Payment Attributes
    private RequestBuilder paymentAttrRequestBuilder;

    //Descriptor Attributes
    private RequestBuilder descriptorAttrRequestBuilder;

    //Notification Attributes
    private RequestBuilder notificationAttrRequestBuilder;
    private HashMap<String, String> notificationAttrParamsMap;

    //Async Attributes
    private RequestBuilder asyncAttrRequestBuilder;
    private HashMap<String, String> asyncAttrParamsMap;

    //Risk Params Attributes
    private RequestBuilder riskParamsAttrRequestBuilder;

    //Mpi Attributes
    private RequestBuilder mpiAttrRequest3DSv1Builder;
    private RequestBuilder mpiAttrRequest3DSv2Builder;

    //FX Attributes
    private RequestBuilder fXAttrRequestBuilder;

    //PBV Attributes
    private RequestBuilder pbvAttrRequestBuilder;
    private HashMap<String, String> pbvAttrParamsMap;

    //Sca Attributes
    private RequestBuilder scaAttrRequestBuilder;
    private HashMap<String, String> scaAttrParamsMap;

    //SDD Attributes
    private RequestBuilder sddAttrRequestBuilder;
    private HashMap<String, String> sddAttrParamsMap;

    //Credit Card Attributes
    private RequestBuilder creditCardAttrRequestBuilder;
    private HashMap<String, String> creditCardAttrParamsMap;

    //Reminder Attributes
    private RequestBuilder reminderAttrRequestBuilder;
    private HashMap<String, String> reminderAttrParamsMap;


    public GenesisValidator getValidator(){
        if( validator == null){
            validator = new GenesisValidator();
        }
        return validator;
    }

    public RequestBuilder getBaseAttrRequestBuilder(){
        if(baseAttrRequestBuilder == null){
            baseAttrRequestBuilder = new RequestBuilder("");
        }
        return baseAttrRequestBuilder;
    }

    public HashMap<String, String> getBaseAttrParamsMap(){
        if(baseAttrParamsMap == null){
            baseAttrParamsMap = new HashMap<String, String>();
        }
        return baseAttrParamsMap;
    }

    public RequestBuilder getBillAddrAttrRequestBuilder(){
        if(billAddrAttrRequestBuilder == null){
            billAddrAttrRequestBuilder = new RequestBuilder("");
        }
        return billAddrAttrRequestBuilder;
    }

    public HashMap<String, String> getBillAddrAttrParamsMap(){
        if(billAddrAttrParamsMap == null){
            billAddrAttrParamsMap = new HashMap<String, String>();
        }
        return billAddrAttrParamsMap;
    }

    public RequestBuilder getShipAddrAttrRequestBuilder(){
        if(shipAddrAttrRequestBuilder == null){
            shipAddrAttrRequestBuilder = new RequestBuilder("");
        }
        return shipAddrAttrRequestBuilder;
    }

    public HashMap<String, String> getShipAddrAttrParamsMap(){
        if(shipAddrAttrParamsMap == null){
            shipAddrAttrParamsMap = new HashMap<String, String>();
        }
        return shipAddrAttrParamsMap;
    }

    public RequestBuilder getCustomerInfoAttrRequestBuilder(){
        if(customerInfoAttrRequestBuilder == null){
            customerInfoAttrRequestBuilder = new RequestBuilder("");
        }
        return customerInfoAttrRequestBuilder;
    }

    public HashMap<String, String> getCustomerInfoAttrParamsMap(){
        if(customerInfoAttrParamsMap == null){
            customerInfoAttrParamsMap = new HashMap<String, String>();
        }
        return customerInfoAttrParamsMap;
    }

    public RequestBuilder getPaymentAttrRequestBuilder(){
        if(paymentAttrRequestBuilder == null){
            paymentAttrRequestBuilder = new RequestBuilder("");
        }
        return paymentAttrRequestBuilder;
    }

    public RequestBuilder getDescriptorAttrRequestBuilder(){
        if(descriptorAttrRequestBuilder == null){
            descriptorAttrRequestBuilder = new RequestBuilder("");
        }
        return descriptorAttrRequestBuilder;
    }

    public RequestBuilder getNotificationAttrRequestBuilder(){
        if(notificationAttrRequestBuilder == null){
            notificationAttrRequestBuilder = new RequestBuilder("");
        }
        return notificationAttrRequestBuilder;
    }

    public HashMap<String, String> getNotificationAttrParamsMap(){
        if(notificationAttrParamsMap == null){
            notificationAttrParamsMap = new HashMap<String, String>();
        }
        return notificationAttrParamsMap;
    }

    public RequestBuilder getAsyncAttrRequestBuilder(){
        if(asyncAttrRequestBuilder == null){
            asyncAttrRequestBuilder = new RequestBuilder("");
        }
        return asyncAttrRequestBuilder;
    }

    public HashMap<String, String> getAsyncAttrParamsMap(){
        if(asyncAttrParamsMap == null){
            asyncAttrParamsMap = new HashMap<String, String>();
        }
        return asyncAttrParamsMap;
    }

    public RequestBuilder getRiskParamsAttrRequestBuilder(){
        if(riskParamsAttrRequestBuilder == null){
            riskParamsAttrRequestBuilder = new RequestBuilder("");
        }
        return riskParamsAttrRequestBuilder;
    }

    public RequestBuilder getMpiAttrRequest3DSv1Builder(){
        if(mpiAttrRequest3DSv1Builder == null){
            mpiAttrRequest3DSv1Builder = new RequestBuilder("");
        }
        return mpiAttrRequest3DSv1Builder;
    }

    public RequestBuilder getMpiAttrRequest3DSv2Builder(){
        if(mpiAttrRequest3DSv2Builder == null){
            mpiAttrRequest3DSv2Builder = new RequestBuilder("");
        }
        return mpiAttrRequest3DSv2Builder;
    }

    public RequestBuilder getFXAttrRequestBuilder(){
        if(fXAttrRequestBuilder == null){
            fXAttrRequestBuilder = new RequestBuilder("");
        }
        return fXAttrRequestBuilder;
    }

    public RequestBuilder getPBVAttrRequestBuilder(){
        if(pbvAttrRequestBuilder == null){
            pbvAttrRequestBuilder = new RequestBuilder("");
        }
        return pbvAttrRequestBuilder;
    }

    public HashMap<String, String> getPBVAttrParamsMap(){
        if(pbvAttrParamsMap == null){
            pbvAttrParamsMap = new HashMap<String, String>();
        }
        return pbvAttrParamsMap;
    }

    public RequestBuilder getScaAttrRequestBuilder(){
        if(scaAttrRequestBuilder == null){
            scaAttrRequestBuilder = new RequestBuilder("");
        }
        return scaAttrRequestBuilder;
    }

    public HashMap<String, String> getScaAttrParamsMap(){
        if(scaAttrParamsMap == null){
            scaAttrParamsMap = new HashMap<String, String>();
        }
        return scaAttrParamsMap;
    }

    public RequestBuilder getSDDAttrRequestBuilder(){
        if(sddAttrRequestBuilder == null){
            sddAttrRequestBuilder = new RequestBuilder("");
        }
        return sddAttrRequestBuilder;
    }

    public HashMap<String, String> getSDDAttrParamsMap(){
        if(sddAttrParamsMap == null){
            sddAttrParamsMap = new HashMap<String, String>();
        }
        return sddAttrParamsMap;
    }

    public RequestBuilder getCreditCardAttrRequestBuilder(){
        if(creditCardAttrRequestBuilder == null){
            creditCardAttrRequestBuilder = new RequestBuilder("");
        }
        return creditCardAttrRequestBuilder;
    }

    public HashMap<String, String> getCreditCardAttrParamsMap(){
        if(creditCardAttrParamsMap == null){
            creditCardAttrParamsMap = new HashMap<String, String>();
        }
        return creditCardAttrParamsMap;
    }

    public RequestBuilder getReminderAttrRequestBuilder(){
        if(reminderAttrRequestBuilder == null){
            reminderAttrRequestBuilder = new RequestBuilder("");
        }
        return reminderAttrRequestBuilder;
    }

    public HashMap<String, String> getReminderAttrParamsMap(){
        if(reminderAttrParamsMap == null){
            reminderAttrParamsMap = new HashMap<String, String>();
        }
        return reminderAttrParamsMap;
    }
}

