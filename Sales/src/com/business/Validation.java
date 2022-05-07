package com.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	private Pattern pattern;

	private Matcher matcher;

	private final String USERNAME_PATTERN = "^[a-z0-9_-]{5,15}$";

	private final String EMAIL_PATTERN =

			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private final String NUMBER_PATTERN = "^[0-9]{3,14}$";

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public boolean validateUsername(final String username) {
		pattern = Pattern.compile(USERNAME_PATTERN);

		matcher = pattern.matcher(username);

		return matcher.matches();

	}

	public boolean isStringLonger(String toValidate) {

		if (toValidate.length() > 50)
			return true;
		return false;
	}

	public boolean isAddressLonger(String toValidate) {

		if (toValidate.length() > 200)
			return true;
		return false;
	}

	public boolean validateMail(final String checkedMail) {
		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(checkedMail);

		return matcher.matches();

	}

	public boolean validateNumber(String number) {
		pattern = Pattern.compile(NUMBER_PATTERN);

		matcher = pattern.matcher(number);
		System.out.println(matcher.matches());

		return matcher.matches();

	}

	boolean isValidDate(String input) {
		try {
			format.parse(input);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
