package com.emerchantpay.gateway.api.constants;

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

import java.util.ArrayList;
import java.util.Arrays;

public class P24BankCodes {

    //BLIK - PSP
    public static final Integer BLIK_PSP = 154;

    //EuroBank
    public static final Integer EUROBANK = 94;

    //Przekaz tradycyjny
    public static final Integer PRZEKAZ_TRADYCYJNY = 178;

    //Przekaz/Przelew tradycyjny
    public static final Integer PRZELEW_TRADYCYJNY = 1000;

    //Place z IKO
    public static final Integer PLACE_Z_IKO = 135;

    //Place z Orange
    public static final Integer PLACE_Z_ORANGE = 146;

    //Raiffeisen Bank PBL
    public static final Integer REIFFEISEN_BANK_PBL = 102;

    //U zyj przedplaty
    public static final Integer U_ZYJ_PRZEDPLATY = 177;

    //mBank-mTransfer
    public static final Integer MBANK_MTRANSFER = 25;

    //Return bank codes as array of strings so it's easier to handle in validation and error message
    public static ArrayList<String> getAllowedP24BankCodes(){
        return new ArrayList<String>(Arrays.asList(
                BLIK_PSP.toString(),
                EUROBANK.toString(),
                PRZEKAZ_TRADYCYJNY.toString(),
                PRZELEW_TRADYCYJNY.toString(),
                PLACE_Z_IKO.toString(),
                PLACE_Z_ORANGE.toString(),
                REIFFEISEN_BANK_PBL.toString(),
                U_ZYJ_PRZEDPLATY.toString(),
                MBANK_MTRANSFER.toString()
        ));
    }
}
