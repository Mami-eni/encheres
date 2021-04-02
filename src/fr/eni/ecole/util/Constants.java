package fr.eni.ecole.util;

public interface Constants {
	String REGEX_PWD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";
	String REGEX_CODE_POSTAL = "[0-9]{5}";
	String REGEX_TELEPHONE = "[0-9]{5}";
	String REGEX_DATE = "^(202[1-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
}
