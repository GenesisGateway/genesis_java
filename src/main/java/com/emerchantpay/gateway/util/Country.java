package com.emerchantpay.gateway.util;

import java.lang.reflect.Field;

public class Country {

	private String code;
	private String name;

	public Country() {
		super();
	}

	public Country(String code, String name) {

		this.code = code;
		this.name = name;
	}

	public static Country Afganistan = new Country("AF", "Afghanistan");
	public static Country AlandIsland = new Country("AX", "Aland Islands");
	public static Country Albania = new Country("AL", "Albania");
	public static Country Algeria = new Country("DZ", "Algeria");
	public static Country AmericanSamoa = new Country("AF", "American Samoa");
	public static Country Andorra = new Country("AD", "Andorra");
	public static Country Angola = new Country("AO", "Angola");
	public static Country Anguilla = new Country("AI", "Anguilla");
	public static Country Antarctica = new Country("AQ", "Antarctica");
	public static Country Antiqua = new Country("AG", "Antiqua And Barbuda");
	public static Country Argentina = new Country("AR", "Argentina");
	public static Country Armenia = new Country("AM", "Armenia");
	public static Country Aruba = new Country("AW", "Andorra");
	public static Country Australia = new Country("AU", "Australia");
	public static Country Austria = new Country("AT", "Austria");
	public static Country Azerbaijan = new Country("AZ", "Azerbaijan");
	public static Country Bahamas = new Country("BS", "Bahamas");
	public static Country Bahrain = new Country("BH", "Bahrain");
	public static Country Bangladesh = new Country("BD", "Bangladesh");
	public static Country Barbados = new Country("BB", "Barbados");
	public static Country Belarus = new Country("BY", "Belarus");
	public static Country Belgium = new Country("BE", "Belgium");
	public static Country Belize = new Country("BZ", "Belize");
	public static Country Benin = new Country("BJ", "Benin");
	public static Country Bermuda = new Country("BM", "Bermuda");
	public static Country Bhutan = new Country("BT", "Bhutan");
	public static Country Bolivia = new Country("BO", "Bolivia");
	public static Country BosniaAndHerzegovina = new Country("BA", "Bosnia And Herzegovina");
	public static Country Botswana = new Country("BW", "Botswana");
	public static Country BouwetIsland = new Country("BV", "Bouwet Island");
	public static Country Brazil = new Country("BR", "Brazil");
	public static Country BritishIndianOceanTerritory = new Country("IO", "British Indian Ocean Territory");
	public static Country Brunei = new Country("BN", "Brunei Darussalam");
	public static Country Bulgaria = new Country("BG", "Bulgaria");
	public static Country BurcinaFaso = new Country("BF", "Burcina Faso");
	public static Country Burundi = new Country("BI", "Burundi");
	public static Country Cambodia = new Country("KH", "Cambodia");
	public static Country Cameroon = new Country("CM", "Cameroon");
	public static Country Canada = new Country("CA", "Canada");
	public static Country CapeVerde = new Country("CV", "Cape Verde");
	public static Country CaymanIslands = new Country("KY", "Cayman Islands");
	public static Country CentralAfricanRepublic = new Country("CF", "Central African Republic");
	public static Country Chad = new Country("TD", "Chad");
	public static Country Chile = new Country("CL", "Chile");
	public static Country China = new Country("CN", "China");
	public static Country ChristmasIsland = new Country("CX", "Christmas Island");
	public static Country CocosIslands = new Country("CC", "Cocos (Keeling) Islands");
	public static Country Colombia = new Country("CO", "Colombia");
	public static Country Comoros = new Country("KM", "Comoros");
	public static Country Congo = new Country("CG", "Congo");
	public static Country CongoDemocraticRepublic = new Country("CD", "Congo Democratic Republic");
	public static Country CookIslands = new Country("CK", "Cook Islands");
	public static Country CostaRica = new Country("CR", "Costa Rica");
	public static Country CoteDIvoire = new Country("CI", "Cote D\'Ivoire");
	public static Country Croatia = new Country("HR", "Croatia");
	public static Country Cuba = new Country("CU", "Cuba");
	public static Country Cyprus = new Country("CY", "Cyprus");
	public static Country CzechRepublic = new Country("CZ", "Czech Republic");
	public static Country Denmark = new Country("DK", "Denmark");
	public static Country Djibuti = new Country("DJ", "Djibuti");
	public static Country Dominica = new Country("DM", "Dominica");
	public static Country DominicanRepublic = new Country("DO", "Dominican Republic");
	public static Country Ecuador = new Country("EC", "Ecuador");
	public static Country Egypt = new Country("EG", "Egypt");
	public static Country ElSalvador = new Country("SV", "El Salvador");
	public static Country EquatorialGuinea = new Country("GQ", "Equatorial Guinea");
	public static Country Erithrea = new Country("ER", "Erithrea");
	public static Country Estonia = new Country("EE", "Estonia");
	public static Country Ethiopia = new Country("ET", "Ethiopia");
	public static Country FalklandIslands = new Country("FK", "Falkland Islands (Malvinas)");
	public static Country FaroeIslands = new Country("FO", "Faroe Islands");
	public static Country Fiji = new Country("FJ", "Fiji");
	public static Country Finland = new Country("FI", "Finland");
	public static Country France = new Country("FR", "France");
	public static Country FrenchGuiana = new Country("GF", "French Guiana");
	public static Country FrenchPolynesia = new Country("PF", "French Polynesia");
	public static Country FrenchSouthernTerritories = new Country("TF", "French Southern Territories");
	public static Country Gabon = new Country("GA", "Gabon");
	public static Country Gambia = new Country("GM", "Gambia");
	public static Country Georgia = new Country("GE", "Georgia");
	public static Country Germany = new Country("DE", "Germany");
	public static Country Ghana = new Country("GH", "Ghana");
	public static Country Gibraltar = new Country("GI", "Gibraltar");
	public static Country Greece = new Country("GR", "Greece");
	public static Country Greenland = new Country("GL", "Greenland");
	public static Country Grenada = new Country("GD", "Grenada");
	public static Country Guadeloupe = new Country("GP", "Guadeloupe");
	public static Country Guam = new Country("GU", "Guam");
	public static Country Guatemala = new Country("GT", "Guatemala");
	public static Country Guernsey = new Country("GG", "Guernsey");
	public static Country Guinea = new Country("GN", "Guinea");
	public static Country GuineaBissau = new Country("GW", "Guiunea-Bissau");
	public static Country Guyana = new Country("GY", "Guyana");
	public static Country Haiti = new Country("HT", "Haiti");
	public static Country HeardIslandandMcdonalIslands = new Country("HM", "Heard Island & Mcdonald Islands");
	public static Country VaticanCity = new Country("VA","Holy See (Vatican City State)");
	public static Country Honduras = new Country("'HN", "Honduras");
	public static Country HongKong = new Country("HK", "Hong Kong");
	public static Country Hungary = new Country("HU", "Hungary");
	public static Country Iceland = new Country("IS", "Iceland");
	public static Country India = new Country("IN", "India");
    public static Country Indonesia = new Country("ID", "Indonesia");
	public static Country Iran = new Country("IR", "Iran, Islamic Republic Of");
	public static Country Iraq = new Country("IQ","Iraq");
	public static Country Ireland = new Country("IE", "Ireland");
	public static Country IsleOfMan = new Country("IM", "Isle of Man");
	public static Country Israel = new Country("IL", "Israel");
	public static Country Italy = new Country("IT", "Italy");
	public static Country Jamaica = new Country("JM", "Jamaica");
	public static Country Japan = new Country("JP", "Japan");
	public static Country Jersey = new Country("JE", "Jersey");
	public static Country Kazakhstan = new Country("KZ", "Kazakhstan");
	public static Country Kenya = new Country("KE", "Kenya");
	public static Country Kiribati = new Country("KI", "Kiribati");
	public static Country Korea = new Country("KR", "Korea");
	public static Country Kuweit = new Country("KW", "Kuwait");
	public static Country Kyrgyzstan = new Country("KG", "Kyrgyzstan");
	public static Country LaoPeopleDemocraticRepublic = new Country("LA", "Lao People\'s Democratic Republic");
	public static Country Latvia = new Country("LV", "Latvia");
	public static Country Lithuania = new Country("LT", "Lithuania");
	public static Country Lebanon = new Country("LB", "Lebanon");
	public static Country Lesotho = new Country("LS", "Lesotho");
	public static Country Liberia = new Country("LR", "Liberia");
	public static Country Libya = new Country("LY", "Libyan Arab Jamahiriya");
	public static Country Liechtenstein = new Country("LI", "Liechtenstein");
	public static Country Luxembourg = new Country("LU", "Luxembourg");
	public static Country Macao = new Country("MO", "Macao");
	public static Country Macedonia = new Country("MK", "Macedonia");
	public static Country Madagascar = new Country("MG", "Madagascar");
	public static Country Malawi = new Country("MW", "Malawi");
	public static Country Malaysia = new Country("MY", "Malaysia");
	public static Country Maldives = new Country("MV", "Maldives");
	public static Country Mali = new Country("ML", "Mali");
	public static Country Malta = new Country("MT", "Malta");
	public static Country MarshallIslands = new Country("MH", "Marshall Islands");
	public static Country Martinique = new Country("MQ", "Martinique");
	public static Country Mauritania = new Country("MR", "Mauritania");
	public static Country Mairitius = new Country("MU", "Mauritius");
	public static Country Mayotte = new Country("YT", "Mayotte");
	public static Country Mexico = new Country("MX", "Mexico");
	public static Country Micronesia = new Country("FM", "Micronesia, Federated States Of");
	public static Country Moldova = new Country("MD", "Moldova");
	public static Country Monaco = new Country("MC", "Monaco");
	public static Country Mongolia = new Country("MN", "Mongolia");
	public static Country Montenegro = new Country("ME", "Montenegro");
	public static Country Montserrat = new Country("MS", "Montserrat");
	public static Country Marocco = new Country("MA", "Marocco");
	public static Country Mozambique = new Country("MZ", "Mozambique");
	public static Country Myanmar = new Country("MM", "Myanmar");
	public static Country Namibia = new Country("NA", "Namibia");
	public static Country Nauru = new Country("NR", "Nauru");
	public static Country Nepal = new Country("NP", "Nepal");
	public static Country Netherlands = new Country("NL", "Netherlands");
	public static Country NetherlandsAntilles = new Country("AN", "Netherlands Antilles");
	public static Country NewCaledonia = new Country("NC", "New Caledonia");
	public static Country NewZealand = new Country("NZ", "New Zealand");
	public static Country Nicaragua = new Country("NI", "Nicaragua");
	public static Country Niger = new Country("NE", "Niger");
	public static Country Nigeria = new Country("NG", "Nigeria");
	public static Country Niue = new Country("NU","Niue");
	public static Country NorfolkIsland = new Country("NF", "Norfolk Island");
	public static Country NorthernMarianaIslands = new Country("MP", "Northern Mariana Islands");
	public static Country Norway = new Country("NO", "Norway");
	public static Country Oman = new Country("OM", "Oman");
	public static Country Pakistan = new Country("PK", "Pakistan");
	public static Country Palau = new Country("PW", "Palau");
	public static Country PalestinianTerritory = new Country("PS", "Palestinian Territory, Occupied");
	public static Country Panama = new Country("PA", "Panama");
	public static Country PapuaNewGuinea = new Country("PG", "Papua New Guinea");
	public static Country Peru = new Country("PE", "Peru");
	public static Country Philippines = new Country("PH", "Philippines");
	public static Country Pitcairn = new Country("PN", "Pitcairn");
	public static Country Poland = new Country("PL", "Poland");
	public static Country Portugal = new Country("PT", "Portugal");
	public static Country PuertoRico = new Country("PR", "Puerto Rico");
	public static Country Qatar = new Country("QA", "Qatar");
	public static Country Reunion = new Country("RE", "Reunion");
	public static Country Romania = new Country("RO", "Romania");
	public static Country Russia = new Country("RU", "Russian Federation");
	public static Country Rwanda = new Country("RW", "Rwanda");
	public static Country SaintBarthelemy = new Country("BL", "Saint Barthelemy");
	public static Country SaintHelena = new Country("SH", "Saint Helena");
	public static Country SaintKittsAndNevis = new Country("KN", "Saint Kitts And Nevis");
	public static Country SaintLucia = new Country("LC", "Saint Lucia");
	public static Country SaintMartin = new Country("MF", "Saint Martin");
	public static Country SaintPierreAndMiquelon = new Country("PM", "Saint Pierre And Miquelon");
	public static Country SaintVincentAndGrenadines = new Country("VC", "Saint Vincent And Grenadines");
	public static Country Samoa = new Country("WS", "Samoa");
	public static Country SanMarino = new Country("SM", "San Marino");
	public static Country SaoTomeAndPrincipe = new Country("ST", "Sao Tome And Principe");
	public static Country SaudiArabia = new Country("SA", "Saudi Arabia");
	public static Country Senegal = new Country("SN", "Senegal");
	public static Country Serbia = new Country("RS", "Serbia");
	public static Country Seychelles = new Country("SC","Seychelles");
	public static Country SierraLeone = new Country("SL", "Sierra Leone");
	public static Country Singapore = new Country("SG", "Singapore");
	public static Country Slovakia = new Country("SK", "Slovakia");
	public static Country Slovenia = new Country("SI", "Slovenia");
	public static Country SolomonIslands = new Country("SB", "SolomonIslands");
	public static Country Somalia = new Country("SO", "Somalia");
	public static Country SouthAfrica = new Country("ZA", "South Africa");
	public static Country SouthGeorgiaAndSandwich = new Country("GS", "South Georgia And Sandwich Isl.");
	public static Country Spain = new Country("ES", "Spain");
	public static Country SriLanka = new Country("LK", "Sri Lanka");
	public static Country Sudan = new Country("SD", "Sudan");
	public static Country Suriname = new Country("SR", "Suriname");
	public static Country SvalbardAndJanMayen = new Country("SJ", "Svalbard And Jan Mayen");
	public static Country Swaziland = new Country("SZ", "Swaziland");
	public static Country Sweden = new Country("SE", "Sweden");
	public static Country Switzerland = new Country("CH", "Switzerland");
	public static Country Syria = new Country("SY", "Syrian Arab Republic");
	public static Country Taiwan = new Country("TW", "Taiwan");
	public static Country Tajikistan = new Country("TJ", "Tajikistan");
	public static Country Tanzania = new Country("TZ", "Tanzania");
	public static Country Thailand = new Country("TH", "Thailand");
	public static Country TimorLeste = new Country("TL", "Timor-Leste");
	public static Country Togo = new Country("TG", "Togo");
	public static Country Tokelau = new Country("TK", "Tokelau");
	public static Country Tonga = new Country("TO", "Tonga");
	public static Country TrinidadAndTobago = new Country("TT", "Trinidad And Tobago");
	public static Country Tunisia = new Country("TN", "Tunisia");
	public static Country Turkey = new Country("TR", "Turkey");
	public static Country Turkmenistan = new Country("TM", "Turkmenistan");
	public static Country TurksAndCaicosIslands = new Country("TC", "Turks And Caicos Islands");
	public static Country Tuvalu = new Country("TV", "Tuvalu");
	public static Country Uganda = new Country("UG", "Uganda");
	public static Country Ukraine = new Country("UA", "Ukraine");
	public static Country UnitedArabEmirates = new Country("AE", "United Arab Emirates");
	public static Country UnitedKingdom = new Country("GB", "United Kingdom");
	public static Country UnitedStates = new Country("US", "United States");
	public static Country UnitedStatesOutlyingIslands = new Country("UM", "United States Outlying Islands");
	public static Country Uruguay = new Country("UY", "Uruguay");
	public static Country Uzbekistan = new Country("UZ", "Uzbekistan");
	public static Country Vanuatu = new Country("VU", "Vanuautu");
	public static Country Venezuela = new Country("VE", "Venezuela");
	public static Country Vietnam = new Country("VN", "Viet Nam");
	public static Country VirginIslandsBritish = new Country("VG", "Virgin Islands, British");
	public static Country VirginIslandsUS = new Country("VI", "'Virgin Islands, U.S.");
	public static Country WallisAndFutuna = new Country("WF", "Wallis And Futuna");
	public static Country WesternSahara = new Country("EH", "Western Sahara");
	public static Country Yemen = new Country("YE", "Yemen");
	public static Country Zambia = new Country("ZM", "Zambia");
	public static Country Zimbabwe = new Country("ZW", "Zimbabwe");

	public String getCode() {
		return code;
	}

	public String getIsoCode(String name) {

		Field field = null;
		Country fieldValue = null;

		Class<? extends Country> c = this.getClass();

		try {
			field = c.getField(name);
			fieldValue = (Country) field.get(this);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			return name;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			return name;
		}

		return fieldValue.getCode();
	}

	public String getCountry() {

		return name;
	}
}
