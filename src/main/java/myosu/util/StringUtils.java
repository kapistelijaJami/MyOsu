package myosu.util;

public class StringUtils {
	public static String stringDelim(String delim, Object... strings) {
		String res = "";
		for (int i = 0; i < strings.length; i++) {
			String s = strings[i].toString();
			res += s;
			if (i == strings.length - 1) {
				break;
			}
			res += delim;
		}
		
		return res;
	}
	
	public static void printDelim(String delim, Object... strings) {
		System.out.println(stringDelim(delim, strings));
	}
	
	public static void printDelim(String delim, String... strings) {
		for (int i = 0; i < strings.length; i++) {
			String s = strings[i];
			System.out.print(s);
			if (i == strings.length - 1) {
				break;
			}
			System.out.print(delim);
		}
	}
	
	public static void printDelimLn(String delim, String... strings) {
		for (int i = 0; i < strings.length; i++) {
			String s = strings[i];
			System.out.print(s);
			if (i == strings.length - 1) {
				break;
			}
			System.out.print(delim);
		}
		System.out.println();
	}
}
