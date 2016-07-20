package eu.veldsoft.share.with.us.model;

import java.util.Random;

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
}
