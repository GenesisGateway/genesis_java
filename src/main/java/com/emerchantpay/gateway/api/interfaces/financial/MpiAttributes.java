package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.MpiProtocolVersions;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;

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

public interface MpiAttributes {

    HashMap<String, String> paramsMap = new HashMap<String, String>();

    // Mpi Params
    default MpiAttributes setMpiCavv(String mpiCavv) {
        paramsMap.put("cavv", mpiCavv);
        getMpiAttrRequest3DSv1Builder().addElement("cavv", mpiCavv);
        getMpiAttrRequest3DSv2Builder().addElement("cavv", mpiCavv);
        return this;
    }

    default MpiAttributes setMpiEci(String mpiEci) {
        paramsMap.put("eci", mpiEci);
        getMpiAttrRequest3DSv1Builder().addElement("eci", mpiEci);
        getMpiAttrRequest3DSv2Builder().addElement("eci", mpiEci);
        return this;
    }

    default MpiAttributes setMpiXid(String mpiXid) {
        paramsMap.put("xid", mpiXid);
        getMpiAttrRequest3DSv1Builder().addElement("xid", mpiXid);
        return this;
    }

    default MpiAttributes setMpiProtocolVersion(String mpiProtocolVersion) {
        paramsMap.put("protocol_version", mpiProtocolVersion);
        getMpiAttrRequest3DSv2Builder().addElement("protocol_version", mpiProtocolVersion);
        return this;
    }

    default MpiAttributes setMpiProtocolSubVersion(String mpiProtocolSubVersion) {
        paramsMap.put("protocol_sub_version", mpiProtocolSubVersion);
        getMpiAttrRequest3DSv2Builder().addElement("protocol_sub_version", mpiProtocolSubVersion);
        return this;
    }

    default MpiAttributes setMpiDirectoryServerId(String mpiDirectoryServerId) {
        paramsMap.put("directory_server_id", mpiDirectoryServerId);
        getMpiAttrRequest3DSv2Builder().addElement("directory_server_id", mpiDirectoryServerId);
        return this;
    }

    default RequestBuilder buildMpi3DSv1ParamsStructure() {
        return getMpiAttrRequest3DSv1Builder();
    }

    default RequestBuilder buildMpi3DSv2ParamsStructure() {
        return getMpiAttrRequest3DSv2Builder();
    }

    default RequestBuilder buildMpiParams() {
        if (is3DSv2()) {
            return buildMpi3DSv2ParamsStructure();
        } else {
            return buildMpi3DSv1ParamsStructure();
        }
    }

    default Boolean is3DSv2() {
        return paramsMap.get("protocol_version") == MpiProtocolVersions.PROTOCOL_VERSION_2;
    }

    RequestBuilder getMpiAttrRequest3DSv1Builder();
    RequestBuilder getMpiAttrRequest3DSv2Builder();
}