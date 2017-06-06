package com.emerchantpay.gateway.util;

import java.math.BigDecimal;
import java.lang.reflect.Field;
import java.math.MathContext;

public class Currency {

	private String currency;
	private Integer exponent;
	private BigDecimal convertedAmount;

	public Currency() {

		super();
	}

	public Currency(String currency, String name, String code, String country, Integer exponent) {

		this.currency = currency;
		this.exponent = exponent;
	}

	public static Currency AED = new Currency("AED", "UAE Dirham", "784", "UNITED ARAB EMIRATES", 2);
	public static Currency AFN = new Currency("AFN", "Afghani", "971", "AFGHANISTAN", 2);
	public static Currency ALL = new Currency("ALL", "Lek", "008", "AL BANIA", 2);
	public static Currency AMD = new Currency("AMD", "Armenian Dram", "051", "ARMENIA", 2);
	public static Currency ANG = new Currency("ANG", "Netherlands Antillean Guilder", "532",
			"SINT MAARTEN (DUTCH PART)", 2);
	public static Currency AOA = new Currency("AOA", "Kwanza", "973", "ANGOLA", 2);
	public static Currency ARS = new Currency("ARS", "Argentine Peso", "032", "ARGENTINA", 2);
	public static Currency AUD = new Currency("AUD", "Australian Dollar ", "036", "TUVALU", 2);
	public static Currency AWG = new Currency("AWG", "Aruban Florin", "533", "ARUBA", 2);
	public static Currency AZN = new Currency("AZN", "Azerbaijanian Manat", "944", "AZERBAIJAN", 2);
	public static Currency BAM = new Currency("BAM", "Convertible Mark", "977", "BOSNIA AND HERZEGOVINA", 2);
	public static Currency BBD = new Currency("BBD", "Barbados Dollar", "052", "BARBADOS", 2);
	public static Currency BDT = new Currency("BDT", "Taka", "050", "BANGLADESH", 2);
	public static Currency BGN = new Currency("BGN", "Bulgarian Lev", "975", "BULGARIA ", 2);
	public static Currency BHD = new Currency("BHD", "Bahraini Dinar", "048", "BAHRAIN", 3);
	public static Currency BIF = new Currency("BIF", "Burundi Franc", "108", "BURUNDI", 0);
	public static Currency BMD = new Currency("BMD", "Bermudian Dollar", "060", "BERMUDA", 2);
	public static Currency BND = new Currency("BND", "Brunei Dollar", "096", "BRU NEI DARUSSALAM", 2);
	public static Currency BOB = new Currency("BOB", "Boliviano", "068", "BOLIVIA, PLURINAT IONAL STATE OF", 2);
	public static Currency BOV = new Currency("BOV", "Mvdol", "984", "BOLIVIA, PLURINATIONA L STATE OF", 2);
	public static Currency BRL = new Currency("BRL", "Brazilian Real", "986", "BRAZIL", 2);
	public static Currency BSD = new Currency("BSD", "Bahamian Dollar", "044", "BAHAMAS", 2);
	public static Currency BTN = new Currency("BTN", "Ngultrum", "064", "BHUTAN", 2);
	public static Currency BWP = new Currency("BWP", "Pula", "072", "BOTSWANA", 2);
	public static Currency BYR = new Currency("BYR", "Belarussian Ruble", "974", "BELARUS", 0);
	public static Currency BZD = new Currency("BZD", "Belize Dollar", "084", "BELIZE", 2);
	public static Currency CAD = new Currency("CAD", "Canadian Dollar", "124", "CANADA", 2);
	public static Currency CDF = new Currency("CDF", "Congolese Franc", "976", "CONGO, DEMOCRATIC REPUBLIC OF THE", 2);
	public static Currency CHE = new Currency("CHE", "WIR Euro", "947", "SWITZERLAND", 2);
	public static Currency CHF = new Currency("CHF", "Swiss Franc", "756", "SWITZERLAND", 2);
	public static Currency CHW = new Currency("CHW", "WIR Franc", "948", "SWITZERLAND", 2);
	public static Currency CLF = new Currency("CLF", "Unidad de Fomento", "990", "CHILE", 4);
	public static Currency CLP = new Currency("CLP", "Chilean Peso", "152", "CHILE", 0);
	public static Currency CNY = new Currency("CNY", "Yuan Renminbi", "156", "CHINA", 2);
	public static Currency COP = new Currency("COP", "Colombian Peso", "170", "COLOMBIA", 2);
	public static Currency COU = new Currency("COU", "Unidad de Valor Real", "970", "COLOMBIA", 2);
	public static Currency CRC = new Currency("CRC", "Costa Rican Colon", "188", "COSTA RICA", 2);
	public static Currency CUC = new Currency("CUC", "Peso Convertible", "931", "CUBA", 2);
	public static Currency CUP = new Currency("CUP", "Cuban Peso", "192", "CUBA", 2);
	public static Currency CVE = new Currency("CVE", "Cabo Verde Escudo", "132", "CABO VERDE", 2);
	public static Currency CZK = new Currency("CZK", "Czec h Koruna", "203", "CZECH REPUBLIC", 2);
	public static Currency DJF = new Currency("DJF", "Djibouti Franc", "262", "DJIBOUTI", 0);
	public static Currency DKK = new Currency("DKK", "Danish Krone", "208", "GREENLAND", 2);
	public static Currency DOP = new Currency("DOP", "Dominican Peso", "214", "DOMINICAN REPUBLIC", 2);
	public static Currency DZD = new Currency("DZD", "Algerian Dinar", "012", "ALGERIA", 2);
	public static Currency EGP = new Currency("EGP", "Egyptian Pound", "818", "EGYPT", 2);
	public static Currency ERN = new Currency("ERN", "Nakfa", "232", "ERITREA ", 2);
	public static Currency ETB = new Currency("ETB", "Ethiopian Birr", "230", "ETHIOPIA", 2);
	public static Currency EUR = new Currency("EUR", "Euro", "978", "SPAIN", 2);
	public static Currency FJD = new Currency("FJD", "Fiji Dollar", "242", "FIJI", 2);
	public static Currency FKP = new Currency("FKP", "Falkland Islands Pound", "238", "FALKLAND ISL ANDS (MALVINAS)",
			2);
	public static Currency GBP = new Currency("GBP", "Pound Sterling", "826", "UNITED KING DOM", 2);
	public static Currency GEL = new Currency("GEL", "Lari", "981", "GEORGIA", 2);
	public static Currency GHS = new Currency("GHS", "Ghana Cedi", "936", "GHANA", 2);
	public static Currency GIP = new Currency("GIP", "Gibraltar Pound", "292", "GIBRALTAR", 2);
	public static Currency GMD = new Currency("GMD", "Dalasi", "270", "GAMBIA", 2);
	public static Currency GNF = new Currency("GNF", "Guinea Franc", "324", "GUINEA", 0);
	public static Currency GTQ = new Currency("GTQ", "Quetzal", "320", "GUATEMALA", 2);
	public static Currency GYD = new Currency("GYD", "Guyana Dollar", "328", "GUYANA", 2);
	public static Currency HKD = new Currency("HKD", "Hong Kong Dollar", "344", "HONG KONG", 2);
	public static Currency HNL = new Currency("HNL", "Lempira", "340", "HONDURAS", 2);
	public static Currency HRK = new Currency("HRK", "Croatian Kuna ", "191", "CROATIA", 2);
	public static Currency HTG = new Currency("HTG", "Gourde", "332", "HAITI", 2);
	public static Currency HUF = new Currency("HUF", "Forint", "348", "HUNGARY", 2);
	public static Currency IDR = new Currency("IDR", "Rupiah", "360", "INDONESIA", 2);
	public static Currency ILS = new Currency("ILS", "New Israeli Sheqel", "376", "ISRAE L", 2);
	public static Currency INR = new Currency("INR", "Indian Rupee", "356", "INDIA", 2);
	public static Currency IQD = new Currency("IQD", "Iraqi Dinar", "368", "IRAQ", 3);
	public static Currency IRR = new Currency("IRR", "Iranian Rial", "364", "IRAN, ISLAMIC REPUBLIC OF", 2);
	public static Currency ISK = new Currency("ISK", "Iceland Krona", "352", "ICELAND", 0);
	public static Currency JMD = new Currency("JMD", "Jamaican Dollar", "388", "JAMAICA", 2);
	public static Currency JOD = new Currency("JOD", "Jordanian Dinar", "400", "JORDAN", 3);
	public static Currency JPY = new Currency("JPY", "Yen", "392", "JAPAN", 0);
	public static Currency KES = new Currency("KES", "Kenyan Shilling", "404", "KENYA", 2);
	public static Currency KGS = new Currency("KGS", "Som", "417", "KYRGYZSTAN", 2);
	public static Currency KHR = new Currency("KHR", "Riel", "116", "CAMBODIA", 2);
	public static Currency KMF = new Currency("KMF", "Comoro Franc", "174", "COMOROS", 0);
	public static Currency KPW = new Currency("KPW", "North Korean Won", "408",
			"KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF", 2);
	public static Currency KRW = new Currency("KRW", "Won", "410", "KOREA, REPUBLIC OF", 0);
	public static Currency KWD = new Currency("KWD", "Kuwaiti Dinar", "414", "KUWAIT", 3);
	public static Currency KYD = new Currency("KYD", "Cayman Islands Dollar", "136", "CAYMAN ISLANDS", 2);
	public static Currency KZT = new Currency("KZT", "Tenge", "398", "KAZAKHSTAN", 2);
	public static Currency LAK = new Currency("LAK", "Kip", "418", "LAO PEOPLE'S DEMOCRATIC REPUBLIC", 2);
	public static Currency LBP = new Currency("LBP", "Lebanese Pound", "422", "LEBANON", 2);
	public static Currency LKR = new Currency("LKR", "Sri Lanka Rupee", "144", "SRI LANKA", 2);
	public static Currency LRD = new Currency("LRD", "Liberian Dollar", "430", "LIBERIA", 2);
	public static Currency LSL = new Currency("LST", "Loti", "426", "LESOTHO", 2);
	public static Currency LTL = new Currency("LTL", "Lithuanian Litas", "440", "LITHUANIA", 2);
	public static Currency LYD = new Currency("LYD", "Libyan Dinar", "434", "LIBYA", 3);
	public static Currency MAD = new Currency("MAD", "Moroccan Dirham", "504", "WESTERN SAHARA", 2);
	public static Currency MDL = new Currency("MDL", "Moldovan Leu", "498", "MOLDOVA, REPUBLIC OF", 2);
	public static Currency MGA = new Currency("MGA", "Malaga sy Ariary", "969", "MADAGASCAR", 2);
	public static Currency MKD = new Currency("MKD", "Denar", "807", "MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF", 2);
	public static Currency MMK = new Currency("MMK", "Kyat", "104", "MYANMAR", 2);
	public static Currency MNT = new Currency("MNT", "Tugrik", "496", "MONGOLIA", 2);
	public static Currency MOP = new Currency("MOP", "Pataca", "446", "MACAO", 2);
	public static Currency MRO = new Currency("MRO", "Ouguiya", "478", "MAURITANIA", 2);
	public static Currency MUR = new Currency("MUR", "Mauritius Rupee", "480", "MAURITIUS", 2);
	public static Currency MVR = new Currency("MVR", "Rufiyaa", "462", "MALDIVES", 2);
	public static Currency MWK = new Currency("MWK", "Kwacha", "454", "MALAWI", 2);
	public static Currency MXN = new Currency("MXK", "Mexican Peso", "484", "MEXICO", 2);
	public static Currency MXV = new Currency("MXV", "Mexican Unidad de Inversion (UDI)", "979", "MEXICO", 2);
	public static Currency MYR = new Currency("MYR", "Malaysian Ringgit", "458", "MALAYSIA", 2);
	public static Currency MZN = new Currency("MZN", "Mozambique Metical", "943", "MOZAMBIQUE", 2);
	public static Currency NAD = new Currency("NAD", "Namibia Dollar", "516", "NAMIBIA", 2);
	public static Currency NGN = new Currency("NGN", "Naira", "566", "NIGERIA", 2);
	public static Currency NIO = new Currency("NIO", "Cordoba Oro", "558", "NICARAGUA", 2);
	public static Currency NOK = new Currency("NOK", "Norwegian Krone", "578", "SVALBARD AND JAN MAYEN", 2);
	public static Currency NPR = new Currency("NPR", "Nepalese Rupee", "524", "NEPAL", 2);
	public static Currency NZD = new Currency("NZD", "New Zealand Dollar", "554", "TOKELAU", 2);
	public static Currency OMR = new Currency("OMR", "Rial Omani", "512", "OMAN", 3);
	public static Currency PAB = new Currency("PAB", "Balboa", "590", "PANAMA", 2);
	public static Currency PEN = new Currency("PEN", "Nuevo Sol", "604", "PERU", 2);
	public static Currency PGK = new Currency("PGK", "Kina", "598", "PAPUA NEW GUINEA", 2);
	public static Currency PHP = new Currency("PHP", "Philippine Peso", "608", "PHILIPPINES", 2);
	public static Currency PKR = new Currency("PKR", "Pakistan Rupee", "586", "PAKISTAN", 2);
	public static Currency PLN = new Currency("PLN", "Zloty", "985", "POLAND", 2);
	public static Currency PYG = new Currency("PYG", "Guarani", "600", "PARAGUAY", 0);
	public static Currency QAR = new Currency("QAR", "Qatari Rial", "634", "QATAR", 2);
	public static Currency RON = new Currency("RON", "New Romanian Leu", "946", "ROMANIA", 2);
	public static Currency RSD = new Currency("RSD", "Serbian Dinar", "941", "SERBIA", 2);
	public static Currency RUB = new Currency("RUB", "Russian Ruble", "643", "RUSSIAN FEDERATION", 2);
	public static Currency RWF = new Currency("RWF", "Rwanda Franc", "646", "RWANDA", 0);
	public static Currency SAR = new Currency("SAR", "Saudi Riyal", "682", "SAUDIARABIA", 2);
	public static Currency SBD = new Currency("SBD", "Sol omon Islands Dollar", "090", "SOLOMON ISLANDS", 2);
	public static Currency SCR = new Currency("SCR", "Seyche lles Rupee", "690", "SEYCHELLES", 2);
	public static Currency SDG = new Currency("SGD", "Sudanese Pound", "938", "SUDAN", 2);
	public static Currency SEK = new Currency("SEK", "Swedish Krona", "752", "SWEDEN", 2);
	public static Currency SGD = new Currency("SGD", "Singapore Dollar", "702", "SINGAPORE", 2);
	public static Currency SHP = new Currency("SHP", "Saint Helena Pound", "654",
			"SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA", 2);
	public static Currency SLL = new Currency("SLL", "Leone", "694", "SIERRA LEONE", 2);
	public static Currency SOS = new Currency("SOS", "Somali Shilling", "706", "SOMALIA", 2);
	public static Currency SRD = new Currency("SRD", "Surinam Dollar", "968", "SURINAME", 2);
	public static Currency SSP = new Currency("SSP", "South Sudanese Pound", "728", "SOUTH SUDAN", 2);
	public static Currency STD = new Currency("STD", "Dobra", "678", "SAO TOME ANDPRINCIPE", 2);
	public static Currency SVC = new Currency("SVC", "El Salvador Colon", "222", "ELSALVADOR", 2);
	public static Currency SYP = new Currency("SYP", "Syrian Pound", "760", "SYRIANARAB REPUBLIC", 2);
	public static Currency SZL = new Currency("SZL", "Lilangeni", "748", "SWAZILAND", 2);
	public static Currency THB = new Currency("THB", "Baht", "764", "THAI LAND", 2);
	public static Currency TJS = new Currency("TJS", "Somoni", "972", "TAJIKISTAN", 2);
	public static Currency TMT = new Currency("TMT", "Turkmenistan New Manat", "934", "TURKMENISTAN", 2);
	public static Currency TND = new Currency("TND", "Tunisian Dinar", "788", "TUNISIA", 3);
	public static Currency TOP = new Currency("TOP", "Pa'anga", "776", "TONGA", 2);
	public static Currency TRY = new Currency("TRY", "Turkish Lira", "949", "TURKEY", 2);
	public static Currency TTD = new Currency("TTD", "Trinidad and Tobago Dollar", "780", "TRINIDAD AND TOBAGO", 2);
	public static Currency TWD = new Currency("TWD", "New Taiwan Dollar", "901", "TAIWAN, PROVINCE OF CHINA", 2);
	public static Currency TZS = new Currency("TZS", "Tanzanian Shilling", "834", "TANZANIA, UNITED REPUBLIC OF", 2);
	public static Currency UAH = new Currency("UAH", "Hryvnia", "980", "UKRAINE", 2);
	public static Currency UGX = new Currency("UGX", "Uganda Shilling", "800", "UGANDA", 0);
	public static Currency USD = new Currency("USD", "US Dollar", "840", "VIRGIN ISLANDS (U.S.)", 2);
	public static Currency USN = new Currency("USN", "US Dollar (Next day)", "997", "UNITED STATES", 2);
	public static Currency UYI = new Currency("UYI", "Uruguay Peso en Unidades Indexadas (URUIURUI)", "940", "URUGUAY",
			0);
	public static Currency UYU = new Currency("UYU", "Peso Uruguayo", "858", "URUGUAY", 2);
	public static Currency UZS = new Currency("UZS", "Uzbekistan Sum", "860", "UZBEKISTAN", 2);
	public static Currency VEF = new Currency("VEF", "Bolivar", "937", "VENEZUELA, BOLIVARIAN REPUBLIC OF", 2);
	public static Currency VND = new Currency("VND", "Dong", "704", "VIETNAM", 0);
	public static Currency VUV = new Currency("VUV", "Vatu", "548", "VANUATU", 0);
	public static Currency WST = new Currency("WST", "Tala", "882", "SAMOA", 2);
	public static Currency XAF = new Currency("XAF", "CFA Franc BEAC", "950", "GABON", 0);
	public static Currency XAG = new Currency("XAG", "Silver", "961", "ZZ11_Silver", null);
	public static Currency XAU = new Currency("XAU", "Gold", "959", "ZZ08_Gold", null);
	public static Currency XBA = new Currency("XBA", "Bond Markets Unit European Composite Unit (EURCO)", "955",
			"ZZ01_Bond Markets Unit European_EURCO", null);
	public static Currency XBB = new Currency("XBB", "Bon d Markets Unit European Monetary Unit (E.M.U.-6)", "956",
			"ZZ02_Bond Markets Unit European_EMU-6", null);
	public static Currency XBC = new Currency("XBC", "Bond Markets Unit European Unit of Account 9 (E.U.A.-9)", "957",
			"ZZ03_Bond Markets Unit European_EUA-9", null);
	public static Currency XBD = new Currency("XBD", "Bond Markets Unit European Unit of Account 17 (E.U.A.-17)", "958",
			"ZZ04_Bond Markets Unit European_EUA-17", null);
	public static Currency XCD = new Currency("XCD", "East Caribbean Dollar", "951", "SAINT VINCENT AND THE GRENADINES",
			2);
	public static Currency XDR = new Currency("XDR", "SDR (Special Drawing Right)", "960",
			"INTERNATIONAL MONETARY FUND (IMF)", null);
	public static Currency XOF = new Currency("XOF", "CFA Franc BCEAO", "952", "TOGO", 0);
	public static Currency XPD = new Currency("XPD", "Palladium", "964", "ZZ09_Palladium", null);
	public static Currency XPF = new Currency("XPF", "CFP Franc", "953", "WALLIS AND FUTUNA", 0);
	public static Currency XPT = new Currency("XPT", "Platinum", "962", "ZZ10_Platinum", null);
	public static Currency XSU = new Currency("XSU", "Sucre", "994",
			"SISTEMA UNI TARIO DE COMPENSACION REGIONAL DE PAGOS \"SUCRE\"", null);
	public static Currency XTS = new Currency("XTS", "Codes specifically reserved for testing purposes", "963",
			"ZZ06_Testing_Code", null);
	public static Currency XUA = new Currency("XUA", "ADB Unit of Account", "965",
			"MEMBER COUNTRIES OF THE AFRICAN DEVELOPMENT BANK GROUP", null);
	public static Currency XXX = new Currency("XXX",
			"The codes assigned for transactions where no currency is involved", "999", "ZZ07_No_Currency", null);
	public static Currency YER = new Currency("YER", "Yemeni Rial", "886", "YEMEN", 2);
	public static Currency ZAR = new Currency("ZAR", "Rand", "710", "SOUTH AFRICA", 2);
	public static Currency ZMW = new Currency("ZMW", "Zambian Kwacha", "967", "ZAMBIA", 2);
	public static Currency ZWL = new Currency("ZWL", "Zimbabwe Dollar", "932", "ZIMBABWE", 2);

	public BigDecimal setAmountToExponent(BigDecimal amount, String currency) {

		Field field = null;
		Currency fieldValue = null;

		Class<? extends Currency> c = this.getClass();

		try {
			field = c.getField(currency);
			fieldValue = (Currency) field.get(this);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Integer exp = fieldValue.getCurrency(fieldValue).exponent;

		if (exp > 0) {
			BigDecimal multiplyExp = new BigDecimal(Math.pow(10, exp), MathContext.DECIMAL64);
		
			convertedAmount = amount.multiply(multiplyExp).setScale(0, BigDecimal.ROUND_DOWN);
		} else {
			convertedAmount = amount.setScale(0, BigDecimal.ROUND_DOWN);
		}

		return convertedAmount;
	}

	public BigDecimal setExponentToAmount(BigDecimal amount, String currency) {

		Field field = null;
		Currency fieldValue = null;

		Class<? extends Currency> c = this.getClass();

		try {
			field = c.getField(currency);
			fieldValue = (Currency) field.get(this);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Integer exp = fieldValue.getCurrency(fieldValue).exponent;

		if (exp > 0) {
			BigDecimal multiplyExp = new BigDecimal(Math.pow(10, exp), MathContext.DECIMAL64);

			convertedAmount = amount.divide(multiplyExp);
		} else {
			convertedAmount = amount;
		}

		return convertedAmount;
	}

	public String getCurrency() {

		return this.currency;
	}

	public Currency getCurrency(Currency currency) {

		return currency;
	}

	public BigDecimal getAmount() {

		return convertedAmount;
	}
}
