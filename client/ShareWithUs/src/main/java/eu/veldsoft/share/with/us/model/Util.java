package eu.veldsoft.share.with.us.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Common utilities class, used in all packages.
 *
 * @author Ventsislav Medarov
 */
final public class Util {
	/**
	 * Private constructor because objects will not be created.
	 */
	private Util() {
	}

	/**
	 * Map table of cyrillic letters to latin letters.
	 */
	private static final Map<String, String> CYRILLIC_TO_LATIN = new HashMap<String, String>();

	/**
	 * Initialize static fields.
	 */
	static {
		CYRILLIC_TO_LATIN.put("а", "a");
		CYRILLIC_TO_LATIN.put("б", "b");
		CYRILLIC_TO_LATIN.put("в", "v");
		CYRILLIC_TO_LATIN.put("г", "g");
		CYRILLIC_TO_LATIN.put("д", "d");
		CYRILLIC_TO_LATIN.put("е", "e");
		CYRILLIC_TO_LATIN.put("ж", "zh");
		CYRILLIC_TO_LATIN.put("з", "z");
		CYRILLIC_TO_LATIN.put("и", "i");
		CYRILLIC_TO_LATIN.put("й", "y");
		CYRILLIC_TO_LATIN.put("к", "k");
		CYRILLIC_TO_LATIN.put("л", "l");
		CYRILLIC_TO_LATIN.put("м", "m");
		CYRILLIC_TO_LATIN.put("н", "n");
		CYRILLIC_TO_LATIN.put("о", "o");
		CYRILLIC_TO_LATIN.put("п", "p");
		CYRILLIC_TO_LATIN.put("р", "r");
		CYRILLIC_TO_LATIN.put("с", "s");
		CYRILLIC_TO_LATIN.put("т", "t");
		CYRILLIC_TO_LATIN.put("у", "u");
		CYRILLIC_TO_LATIN.put("ф", "f");
		CYRILLIC_TO_LATIN.put("х", "h");
		CYRILLIC_TO_LATIN.put("ц", "ts");
		CYRILLIC_TO_LATIN.put("ч", "ch");
		CYRILLIC_TO_LATIN.put("ш", "sh");
		CYRILLIC_TO_LATIN.put("щ", "sht");
		CYRILLIC_TO_LATIN.put("ъ", "a");
		CYRILLIC_TO_LATIN.put("ь", "y");
		CYRILLIC_TO_LATIN.put("ю", "yu");
		CYRILLIC_TO_LATIN.put("я", "ya");
		CYRILLIC_TO_LATIN.put("А", "A");
		CYRILLIC_TO_LATIN.put("Б", "B");
		CYRILLIC_TO_LATIN.put("В", "V");
		CYRILLIC_TO_LATIN.put("Г", "G");
		CYRILLIC_TO_LATIN.put("Д", "D");
		CYRILLIC_TO_LATIN.put("Е", "E");
		CYRILLIC_TO_LATIN.put("Ж", "ZH");
		CYRILLIC_TO_LATIN.put("З", "Z");
		CYRILLIC_TO_LATIN.put("И", "I");
		CYRILLIC_TO_LATIN.put("Й", "Y");
		CYRILLIC_TO_LATIN.put("К", "K");
		CYRILLIC_TO_LATIN.put("Л", "L");
		CYRILLIC_TO_LATIN.put("М", "M");
		CYRILLIC_TO_LATIN.put("Н", "N");
		CYRILLIC_TO_LATIN.put("О", "O");
		CYRILLIC_TO_LATIN.put("П", "P");
		CYRILLIC_TO_LATIN.put("Р", "R");
		CYRILLIC_TO_LATIN.put("С", "S");
		CYRILLIC_TO_LATIN.put("Т", "T");
		CYRILLIC_TO_LATIN.put("У", "U");
		CYRILLIC_TO_LATIN.put("Ф", "F");
		CYRILLIC_TO_LATIN.put("Х", "H");
		CYRILLIC_TO_LATIN.put("Ц", "TS");
		CYRILLIC_TO_LATIN.put("Ч", "CH");
		CYRILLIC_TO_LATIN.put("Ш", "SH");
		CYRILLIC_TO_LATIN.put("Щ", "SHT");
		CYRILLIC_TO_LATIN.put("Ъ", "A");
		CYRILLIC_TO_LATIN.put("Ь", "Y");
		CYRILLIC_TO_LATIN.put("Ю", "YU");
		CYRILLIC_TO_LATIN.put("Я", "YA");
	}

	/**
	 * Pseudo-random number generator.
	 */
	public static final Random PRNG = new Random();

	/**
	 * Instance hash key value.
	 */
	public static final String SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY = "eu.veldsoft.share.with.us.instance.hash.code";

	/**
	 * Instance hash JSON key value.
	 */
	public static final String JSON_INSTNCE_HASH_CODE_KEY = "instance_hash";

	/**
	 * Parent message hash JSON key value.
	 */
	public static final String JSON_PARENT_HASH_CODE_KEY = "parent_hash";

	/**
	 * Message hash JSON key value.
	 */
	public static final String JSON_MESSAGE_HASH_CODE_KEY = "message_hash";

	/**
	 * Last known message hash JSON key value.
	 */
	public static final String JSON_LAST_MESSAGE_HASH_CODE_KEY = "last_message_hash";

	/**
	 * Message JSON key value.
	 */
	public static final String JSON_MESSAGE_KEY = "message";

	/**
	 * Message JSON key value.
	 */
	public static final String JSON_RATING_KEY = "rating";

	/**
	 * Global data-time message registration JSON key value.
	 */
	public static final String JSON_REGISTERED_KEY = "registered";

	/**
	 * Record found JSON key value.
	 */
	public static final String JSON_FOUND_KEY = "found";

	/**
	 * Consultant names JSON key value.
	 */
	public static final String JSON_NAMES_KEY = "names";

	/**
	 * Consultant email JSON key value.
	 */
	public static final String JSON_EMAIL_KEY = "email";

	/**
	 * Consultant phone JSON key value.
	 */
	public static final String JSON_PHONE_KEY = "phone";

	/**
	 * Find better way for giving value of this constant.
	 */
	public static final int ALARM_REQUEST_CODE = 0;

	/**
	 * Parent message hash key.
	 */
	public static final String PARENT_MESSAGE_HASH_KEY = "eu.veldsoft.share.with.us.parent.message.hash";

	/**
	 * Registration time stamp key.
	 */
	public static final String REGISTERED_KEY = "eu.veldsoft.share.with.us.registered";

	/**
	 * Convert text from cyrillic letters to latin letters.
	 * 
	 * @param text
	 *            Text in cyrillic letters.
	 * 
	 * @return Text in latin letters.
	 */
	public static String cyrillicToLatin(String text) {
		String result = "" + text;

		for (String key : CYRILLIC_TO_LATIN.keySet()) {
			result = Pattern.compile(key).matcher(result)
					.replaceAll(CYRILLIC_TO_LATIN.get(key));
		}

		return result;
	}
}
