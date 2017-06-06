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

public enum ErrorCodes {

	SUCCESS(000), ERROR(001), 
	SYSTEM_ERROR(100), 
	MAINTENANCE_ERROR(101), 
	AUTHENTICATION_ERROR(110), 
	CONFIGURATION_ERROR(120), 
	COMMUNICATION_ERROR(200), 
	CONNECTION_ERROR(210), 
	ACCOUNT_ERROR(220), 
	TIMEOUT_ERROR(230), 
	RESPONSE_ERROR(240), 
	PARSING_ERROR(250), 
	INPUT_DATA_ERROR(300),
	INVALID_TRANSACTION_TYPE_ERROR(310),	
	INPUT_DATA_MISSING_ERROR(320), 
	INPUT_DATA_FORMAT_ERROR(330), 
	INPUT_DATA_INVALID_ERROR(340), 
	INVALID_XML_ERROR(350), 
	INVALID_CONTENT_TYPE_ERROR(360), 
	WORKFLOW_ERROR(400), 
	REFERENCE_NOT_FOUND_ERROR(410), 
	REFERENCE_WORKFLOW_ERROR(420), 
	REFERENCE_INVALIDATED_ERROR(430), 
	REFERENCE_MISMATCH_ERROR(440), 
	DOUBLE_TRANSACTION_ERROR(450), 
	TRANSACTION_NOT_FOUND_ERROR(460), 
	PROCESSING_ERROR(500), 
	TRANSACTION_PENDING_ERROR(530), 
	CREDIT_EXCEEDED_ERROR(540), 
	RISK_ERROR(600), 
	BIN_COUNTRY_CHECK_ERROR(609), 
	CARD_BLACKLIST_ERROR(610), 
	BIN_BLACKLIST_ERROR(611), 
	COUNTRY_BLACKLIST_ERROR(612), 
	IP_BLACKLIST_ERROR(613), 
	BLACKLIST_ERROR(614), 
	CARD_WHITELIST_ERROR(615),
	CARD_LIMIT_EXCEEDED_ERROR(620),
	TERMINAL_LIMIT_EXCEEDED_ERROR(621),
	CONTRACT_LIMIT_EXCEEDED_ERROR(622),
	CARD_VELOCITY_EXCEEDED_ERROR(623),
	CARD_TICKET_SIZE_EXCEEDED_ERROR(624), 
	USER_LIMIT_EXCEEDED_ERROR(625),  
	MULTIPLE_FAILURE_DETECTION_ERROR(626),
	CS_DETECTION_ERROR(627),
	RECURRING_LIMIT_EXCEEDED_ERROR(628),
	AVS_ERROR(690),
	THREAT_METRIX_RISK_ERROR(692),
	REMOTE_ERROR(900),
	REMOTE_SYSTEM_ERROR(910),
	REMOTE_CONFIGURATION_ERROR(920),
	REMOTE_DATA_ERROR(930),
	REMOTE_WORKFLOW_ERROR(940),
	REMOTE_TIMEOUT_ERROR(950),
	REMOTE_CONNECTION_ERROR(960), 
	UNKNOWN_VALIDATION_ERROR(000);
	
	Integer code;

	ErrorCodes(Integer code) {

		this.code = code;
	}
	
	 public static ErrorCodes findByCode(String code) {
        for (ErrorCodes errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return UNKNOWN_VALIDATION_ERROR;
    }
	 	
	 public static String getErrorDescription(Integer errorCode) {
			switch (errorCode) {
			default:
			case 001:
				return "Undefined error.";
			case 100:
				return "A general system error occurred.";
			case 101:
				return "System is undergoing maintenance, request could not be handled.";
			case 110:
				return "Login failed. Check your API credentials.";
			case 120:
				return "Config error occurred, e.g. terminal not configured properly. Check terminal settings.";
			case 200:
				return "Communication with issuer failed, please contact support.";
			case 210:
				return "Connection to issuer could not be established, please contact support.";
			case 220:
				return "Issuer account data invalid, please contact support.";
			case 230:
				return "Issuer does not respond within given time-frame - please reconcile.";
			case 240:
				return "Issuer returned invalid response - please reconcile and contact support.";
			case 250:
				return "Issuer response could not be parsed - please reconcile and contact support.";
			case 300:
				return "Invalid were data sent to the API.";
			case 310:
				return "Invalid transaction type was passed to API.";
			case 320:
				return "Required argument is missing.";
			case 330:
				return "Argument passed in invalid format.";
			case 340:
				return "Argument passed in valid format but makes no sense (e.g. incorrect country code or currency).";
			case 350:
				return "The input Builder could not be parsed due to invalid code.";
			case 400:
				return "A transaction was triggered that is not possible at this time in the workflow, e.g. a refund on a declined transaction.";
			case 410:
				return "Reference transaction was not found.";
			case 420:
				return "Wrong Workflow specified.";
			case 430:
				return "Reference transaction already invalidated!";
			case 440:
				return "Data mismatch with reference, e.g. amount exceeds reference.";
			case 450:
				return "Transaction doublet was detected, transaction was blocked.";
			case 460:
				return "The referenced transaction could not be found.";
			case 500:
				return "Transaction declined by issuer.";
			case 510:
				return "Transaction declined, Credit card number is invalid.";
			case 520:
				return "Transaction declined, expiration date not in the future or date invalid.";
			case 530:
				return "Transaction pending.";
			case 540:
				return "Amount exceeds credit card limit.";
			case 600:
				return "Transaction declined by risk management.";
			case 609:
				return "Card bin does not match billing country.";
			case 610:
				return "Card is blacklisted.";
			case 611:
				return "BIN blacklisted.";
			case 612:
				return "Country blacklisted.";
			case 613:
				return "IP address blacklisted.";
			case 614:
				return "Value from the Transaction Request or Risk Parameters is blacklisted.";
			case 616:
				return "PAN Whitelist Filter blocked the transaction.";
			case 620:
				return "Card limit exceeded configured limits.";
			case 621:
				return "Terminal limits exceeded.";
			case 622:
				return "MID limits exceeded.";
			case 623:
				return "Velocity by unknown card exceeded!";
			case 624:
				return "Ticket size by unknown card exceeded!";
			case 625:
				return "User limit exceeded configured limits.";
			case 626:
				return "Found user transaction declined by issuer.";
			case 627:
				return "CrossSelling Error!";
			case 628:
				return "Amount/count by recurring subscription exceeded.";
			case 629:
				return "Address Verification failed.";
			case 691:
				return "MaxMind High Risk Error.";
			case 692:
				return "ThreatMetrix High Risk Error.";
			case 900:
				return "Some error occurred on the issuer.";
			case 910:
				return "Some error occurred on the issuer.";
			case 920:
				return "Issuer configuration error.";
			case 930:
				return "Some passed data caused an error on the issuer.";
			case 940:
				return "Remote workflow error.";
			case 950:
				return "Issuer has time-out with clearing network.";
			case 960:
				return "Issuer could not reach clearing network.";
			}
		}
	 
	 public static String getIssuerResponseCode(String issuerResponseCode)
	    {
	        switch (issuerResponseCode) {
	            case "00":
	                return "Approved or completed successfully";
	            case "02":
	                return "Refer to card issue";
	            case "03":
	                return "Invalid merchant";
	            case "04":
	            case "05":
	                return "Do not honour";
	            case "06":
	                return "Invalid Transaction for Terminal";
	            case "07":
	                return "Honour with ID";
	            case "08":
	                return "Time-Out";
	            case "09":
	                return "No Original";
	            case "10":
	                return "Unable to Reverse";
	            case "11":
	                return "Partial Approval";
	            case "12":
	                return "Invalid transaction card / issuer / acquirer";
	            case "13":
	                return "Invalid amount";
	            case "14":
	                return "Invalid card number";
	            case "17":
	                return "Invalid Capture date (terminal business date)";
	            case "19":
	                return "System Error; Re-enter transaction";
	            case "20":
	                return "No From Account";
	            case "21":
	                return "No To Account";
	            case "22":
	                return "No Checking Account";
	            case "23":
	                return "No Saving Account";
	            case "24":
	                return "No Credit Account";
	            case "30":
	                return "Format error";
	            case "34":
	                return "Implausible card data";
	            case "39":
	                return "Transaction Not Allowed";
	            case "41":
	                return "Pick-up card";
	            case "42":
	                return "Special Pickup";
	            case "43":
	                return "Hot Card, Pickup (if possible)";
	            case "44":
	                return "Pickup Card";
	            case "45":
	                return "Transaction Back Off";
	            case "51":
	                return "Not sufficient funds";
	            case "54":
	                return "Expired card";
	            case "55":
	                return "Incorrect PIN; Re-enter";
	            case "57":
	                return "Not permitted on card";
	            case "58":
	                return "Txn Not Permitted On Term";
	            case "61":
	                return "Exceeds amount limit";
	            case "62":
	                return "Restricted card";
	            case "63":
	                return "MAC Key Error";
	            case "65":
	                return "Exceeds frequency limit";
	            case "66":
	                return "Exceeds Acquirer Limit";
	            case "67":
	                return "Retain Card; no reason specified";
	            case "68":
	                return "Response received too late";
	            case "75":
	                return "Exceeds PIN Retry";
	            case "76":
	                return "Invalid Account";
	            case "77":
	                return "Issuer Does Not Participate In The Service";
	            case "78":
	                return "Function Not Available";
	            case "79":
	                return "Key Validation Error";
	            case "80":
	                return "Approval for Purchase Amount Only";
	            case "81":
	                return "Unable to Verify PIN";
	            case "82":
	                return "Invalid Card Verification Value";
	            case "83":
	                return "Not declined (AVS Only)";
	            case "84":
	                return "Invalid Life Cycle of transaction";
	            case "85":
	                return "No Keys To Use";
	            case "86":
	                return "K M E Sync Error";
	            case "87":
	                return "PIN Key Error";
	            case "88":
	                return "MAC sync Error";
	            case "89":
	                return "Security Violation";
	            case "91":
	                return "Issuer not available";
	            case "92":
	                return "Invalid Issuer";
	            case "93":
	                return "Transaction cannot be completed";
	            case "94":
	                return "Invalid originator";
	            case "96":
	                return "System malfunction";
	            case "97":
	                return "No Funds Transfer";
	            case "98":
	                return "Duplicate Reversal";
	            case "99":
	                return "Duplicate Transaction";
	            case "N3":
	                return "Cash Service Not Available";
	            case "N4":
	                return "Cash Back Request Exceeds Issuer Limit";
	            case "N7":
	                return "CVV2 Failure";
	            case "R0":
	                return "Stop Payment Order";
	            case "R1":
	                return "Revocation of Authorisation Order";
	            case "R3":
	                return "Revocation of all Authorisations Order";
	        }

	        return null;
	    }

}
