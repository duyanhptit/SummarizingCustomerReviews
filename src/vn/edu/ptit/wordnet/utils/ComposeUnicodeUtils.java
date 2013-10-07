/*
 * DecomposeUnicode.java
 *
 * � Tinhte.com, 2010
 * Confidential and proprietary.
 * 
 * Developer: Keeper
 * Email: louis.keeper@gmail.com
 */

package vn.edu.ptit.wordnet.utils;

import java.util.Hashtable;

/**
 * Class DecomposeUnicode is used to decompode composed unicode characters
 */
public class ComposeUnicodeUtils {

	private final Hashtable UNICODE_TABLE;
	private final Hashtable MIRROR_UNICODE_TABLE;
	private final Hashtable HTML_CODE_TABLE;

	// Constructor
	public ComposeUnicodeUtils() {

		// Prepare data for UNICODE_TABLE
		UNICODE_TABLE = new Hashtable();
		// prepareUNICODE_TABLE();

		// Prepare data for HTML_CODE_TABLE
		HTML_CODE_TABLE = new Hashtable();
		// prepareHTML_CODE_TABLE();

		// Prepare data for MIRROR_UNICODE_TABLE
		MIRROR_UNICODE_TABLE = new Hashtable();
		prepareMIRROR_UNICODE_TABLE(); // Use this application.

	}

	private void prepareUNICODE_TABLE() {

		UNICODE_TABLE.put(new Character('\u1ea3'), "\u0061\u0309"); // a?
		UNICODE_TABLE.put(new Character('\u1ea1'), "\u0061\u0323"); // a.

		UNICODE_TABLE.put(new Character('\u1eaf'), "\u0103\u0301"); // a('
		UNICODE_TABLE.put(new Character('\u1eb1'), "\u0103\u0300"); // a(`
		UNICODE_TABLE.put(new Character('\u1eb3'), "\u0103\u0309"); // a(?
		UNICODE_TABLE.put(new Character('\u1eb5'), "\u0103\u0303"); // a(~
		UNICODE_TABLE.put(new Character('\u1eb7'), "\u0103\u0323"); // a(.

		UNICODE_TABLE.put(new Character('\u1ea5'), "\u00e2\u0301"); // a^'
		UNICODE_TABLE.put(new Character('\u1ea7'), "\u00e2\u0300"); // a^`
		UNICODE_TABLE.put(new Character('\u1ea9'), "\u00e2\u0309"); // a^?
		UNICODE_TABLE.put(new Character('\u1eab'), "\u00e2\u0303"); // a^~
		UNICODE_TABLE.put(new Character('\u1ead'), "\u00e2\u0323"); // a^.

		UNICODE_TABLE.put(new Character('\u1ebb'), "\u0065\u0309"); // e?
		UNICODE_TABLE.put(new Character('\u1ebd'), "\u0065\u0303"); // e~
		UNICODE_TABLE.put(new Character('\u1eb9'), "\u0065\u0323"); // e.

		UNICODE_TABLE.put(new Character('\u1ebf'), "\u00ea\u0301"); // e^'
		UNICODE_TABLE.put(new Character('\u1ec1'), "\u00ea\u0300"); // e^`
		UNICODE_TABLE.put(new Character('\u1ec3'), "\u00ea\u0309"); // e^?
		UNICODE_TABLE.put(new Character('\u1ec5'), "\u00ea\u0303"); // e^~
		UNICODE_TABLE.put(new Character('\u1ec7'), "\u00ea\u0323"); // e^.

		UNICODE_TABLE.put(new Character('\u1ec9'), "\u0069\u0309"); // i?
		UNICODE_TABLE.put(new Character('\u1ecb'), "\u0069\u0323"); // i.

		UNICODE_TABLE.put(new Character('\u1ecf'), "\u006f\u0309"); // o?
		UNICODE_TABLE.put(new Character('\u1ecd'), "\u006f\u0323"); // o.

		UNICODE_TABLE.put(new Character('\u1ed1'), "\u00f4\u0301"); // o^'
		UNICODE_TABLE.put(new Character('\u1ed3'), "\u00f4\u0300"); // o^`
		UNICODE_TABLE.put(new Character('\u1ed5'), "\u00f4\u0309"); // o^?
		UNICODE_TABLE.put(new Character('\u1ed7'), "\u00f4\u0303"); // o^~
		UNICODE_TABLE.put(new Character('\u1ed9'), "\u00f4\u0323"); // o^.

		UNICODE_TABLE.put(new Character('\u1edb'), "\u01a1\u0301"); // o+'
		UNICODE_TABLE.put(new Character('\u1edd'), "\u01a1\u0300"); // o+`
		UNICODE_TABLE.put(new Character('\u1edf'), "\u01a1\u0309"); // o+?
		UNICODE_TABLE.put(new Character('\u1ee1'), "\u01a1\u0303"); // o+~
		UNICODE_TABLE.put(new Character('\u1ee3'), "\u01a1\u0323"); // o+.

		UNICODE_TABLE.put(new Character('\u1ee7'), "\u0075\u0309"); // u?
		UNICODE_TABLE.put(new Character('\u1ee5'), "\u0075\u0323"); // u.

		UNICODE_TABLE.put(new Character('\u1ee9'), "\u01b0\u0301"); // u+'
		UNICODE_TABLE.put(new Character('\u1eeb'), "\u01b0\u0300"); // u+`
		UNICODE_TABLE.put(new Character('\u1eed'), "\u01b0\u0309"); // u+?
		UNICODE_TABLE.put(new Character('\u1eef'), "\u01b0\u0303"); // u+~
		UNICODE_TABLE.put(new Character('\u1ef1'), "\u01b0\u0323"); // u+.

		UNICODE_TABLE.put(new Character('\u1ef7'), "\u0079\u0309"); // y?
		UNICODE_TABLE.put(new Character('\u1ef9'), "\u0079\u0303"); // y~
		UNICODE_TABLE.put(new Character('\u1ef5'), "\u0079\u0323"); // y.

		// Capital

		UNICODE_TABLE.put(new Character('\u1ea2'), "\u0041\u0309"); // A
		UNICODE_TABLE.put(new Character('\u1ea0'), "\u0041\u0323");

		UNICODE_TABLE.put(new Character('\u1eae'), "\u0102\u0301");
		UNICODE_TABLE.put(new Character('\u1eb0'), "\u0102\u0300");
		UNICODE_TABLE.put(new Character('\u1eb2'), "\u0102\u0309"); // A(
		UNICODE_TABLE.put(new Character('\u1eb4'), "\u0102\u0303");
		UNICODE_TABLE.put(new Character('\u1eb6'), "\u0102\u0323");

		UNICODE_TABLE.put(new Character('\u1ea4'), "\u00c2\u0301");
		UNICODE_TABLE.put(new Character('\u1ea6'), "\u00c2\u0300");
		UNICODE_TABLE.put(new Character('\u1ea8'), "\u00c2\u0309"); // A^
		UNICODE_TABLE.put(new Character('\u1eaa'), "\u00c2\u0303");
		UNICODE_TABLE.put(new Character('\u1eac'), "\u00c2\u0323");

		UNICODE_TABLE.put(new Character('\u1eba'), "\u0045\u0309");
		UNICODE_TABLE.put(new Character('\u1ebc'), "\u0045\u0303"); // E
		UNICODE_TABLE.put(new Character('\u1eb8'), "\u0045\u0323");

		UNICODE_TABLE.put(new Character('\u1ebe'), "\u00ca\u0301");
		UNICODE_TABLE.put(new Character('\u1ec0'), "\u00ca\u0300");
		UNICODE_TABLE.put(new Character('\u1ec2'), "\u00ca\u0309"); // E^
		UNICODE_TABLE.put(new Character('\u1ec4'), "\u00ca\u0303");
		UNICODE_TABLE.put(new Character('\u1ec6'), "\u00ca\u0323");

		UNICODE_TABLE.put(new Character('\u1ec8'), "\u0049\u0309"); // I
		UNICODE_TABLE.put(new Character('\u1eca'), "\u0049\u0323");

		UNICODE_TABLE.put(new Character('\u1ece'), "\u004f\u0309"); // O
		UNICODE_TABLE.put(new Character('\u1ecc'), "\u004f\u0323");

		UNICODE_TABLE.put(new Character('\u1ed0'), "\u00d4\u0301");
		UNICODE_TABLE.put(new Character('\u1ed2'), "\u00d4\u0300");
		UNICODE_TABLE.put(new Character('\u1ed4'), "\u00d4\u0309"); // O^
		UNICODE_TABLE.put(new Character('\u1ed6'), "\u00d4\u0303");
		UNICODE_TABLE.put(new Character('\u1ed8'), "\u00d4\u0323");

		UNICODE_TABLE.put(new Character('\u1eda'), "\u01a0\u0301");
		UNICODE_TABLE.put(new Character('\u1edc'), "\u01a0\u0300");
		UNICODE_TABLE.put(new Character('\u1ede'), "\u01a0\u0309"); // O+
		UNICODE_TABLE.put(new Character('\u1ee0'), "\u01a0\u0303");
		UNICODE_TABLE.put(new Character('\u1ee2'), "\u01a0\u0323");

		UNICODE_TABLE.put(new Character('\u1ee6'), "\u0055\u0309"); // U
		UNICODE_TABLE.put(new Character('\u1ee4'), "\u0055\u0323");

		UNICODE_TABLE.put(new Character('\u1ee8'), "\u01af\u0301");
		UNICODE_TABLE.put(new Character('\u1eea'), "\u01af\u0300");
		UNICODE_TABLE.put(new Character('\u1eec'), "\u01af\u0309"); // U+
		UNICODE_TABLE.put(new Character('\u1eee'), "\u01af\u0303");
		UNICODE_TABLE.put(new Character('\u1ef0'), "\u01af\u0323");

		UNICODE_TABLE.put(new Character('\u1ef6'), "\u0059\u0309");
		UNICODE_TABLE.put(new Character('\u1ef8'), "\u0059\u0303"); // Y
		UNICODE_TABLE.put(new Character('\u1ef4'), "\u0059\u0323");
	}

	private void prepareHTML_CODE_TABLE() {
		HTML_CODE_TABLE.put("&#7843;", "\u0041\u0309"); // A?
		HTML_CODE_TABLE.put("&#7843;", "\u0061\u0309"); // a?
		HTML_CODE_TABLE.put("&#7841;", "\u0041\u0323"); // A.
		HTML_CODE_TABLE.put("&#7841;", "\u0061\u0323"); // a.

		HTML_CODE_TABLE.put("&#7854;", "\u0102\u0301"); // A('
		HTML_CODE_TABLE.put("&#7855;", "\u0103\u0301"); // a('
		HTML_CODE_TABLE.put("&#7856;", "\u0102\u0300"); // A(`
		HTML_CODE_TABLE.put("&#7857;", "\u0103\u0300"); // a(`
		HTML_CODE_TABLE.put("&#7858;", "\u0102\u0309"); // A(?
		HTML_CODE_TABLE.put("&#7859;", "\u0103\u0309"); // a(?
		HTML_CODE_TABLE.put("&#7860;", "\u0102\u0303"); // A(~
		HTML_CODE_TABLE.put("&#7861;", "\u0103\u0303"); // a(~
		HTML_CODE_TABLE.put("&#7862;", "\u0102\u0323"); // A(.
		HTML_CODE_TABLE.put("&#7863;", "\u0103\u0323"); // a(.

		HTML_CODE_TABLE.put("&#7844;", "\u00c2\u0301"); // A^'
		HTML_CODE_TABLE.put("&#7846;", "\u00c2\u0300"); // A^`
		HTML_CODE_TABLE.put("&#7848;", "\u00c2\u0309"); // A^?
		HTML_CODE_TABLE.put("&#7850;", "\u00c2\u0303"); // A^~
		HTML_CODE_TABLE.put("&#7852;", "\u00c2\u0323"); // A^.

		HTML_CODE_TABLE.put("&#7845;", "\u00e2\u0301"); // a^'
		HTML_CODE_TABLE.put("&#7847;", "\u00e2\u0300"); // a^`
		HTML_CODE_TABLE.put("&#7849;", "\u00e2\u0309"); // a^?
		HTML_CODE_TABLE.put("&#7851;", "\u00e2\u0303"); // a^~
		HTML_CODE_TABLE.put("&#7853;", "\u00e2\u0323"); // a^.

		HTML_CODE_TABLE.put("&#7866;", "\u0045\u0309"); // E?
		HTML_CODE_TABLE.put("&#7868;", "\u0045\u0303"); // E~
		HTML_CODE_TABLE.put("&#7864;", "\u0045\u0323"); // E.

		HTML_CODE_TABLE.put("&#7867;", "\u0065\u0309"); // e?
		HTML_CODE_TABLE.put("&#7869;", "\u0065\u0303"); // e~
		HTML_CODE_TABLE.put("&#7865;", "\u0065\u0323"); // e.

		HTML_CODE_TABLE.put("&#7870;", "\u00ca\u0301"); // E^'
		HTML_CODE_TABLE.put("&#7872;", "\u00ca\u0300"); // E^`
		HTML_CODE_TABLE.put("&#7874;", "\u00ca\u0309"); // E^?
		HTML_CODE_TABLE.put("&#7876;", "\u00ca\u0303"); // E^~
		HTML_CODE_TABLE.put("&#7878;", "\u00ca\u0323"); // E^.

		HTML_CODE_TABLE.put("&#7871;", "\u00ea\u0301"); // e^'
		HTML_CODE_TABLE.put("&#7873;", "\u00ea\u0300"); // e^`
		HTML_CODE_TABLE.put("&#7875;", "\u00ea\u0309"); // e^?
		HTML_CODE_TABLE.put("&#7877;", "\u00ea\u0303"); // e^~
		HTML_CODE_TABLE.put("&#7879;", "\u00ea\u0323"); // e^.

		HTML_CODE_TABLE.put("&#7880;", "\u0049\u0309"); // I?
		HTML_CODE_TABLE.put("&#7882;", "\u0049\u0323"); // I.

		HTML_CODE_TABLE.put("&#7881;", "\u0069\u0309"); // i?
		HTML_CODE_TABLE.put("&#7883;", "\u0069\u0323"); // i.

		HTML_CODE_TABLE.put("&#7886;", "\u004f\u0309"); // O?
		HTML_CODE_TABLE.put("&#7884;", "\u004f\u0323"); // O.

		HTML_CODE_TABLE.put("&#7887;", "\u006f\u0309"); // o?
		HTML_CODE_TABLE.put("&#7885;", "\u006f\u0323"); // o.

		HTML_CODE_TABLE.put("&#7888;", "\u00d4\u0301"); // O^'
		HTML_CODE_TABLE.put("&#7889;", "\u006f\u0301"); // o^'
		HTML_CODE_TABLE.put("&#7890;", "\u00d4\u0300"); // O^`
		HTML_CODE_TABLE.put("&#7891;", "\u006f\u0300"); // o^`
		HTML_CODE_TABLE.put("&#7892;", "\u00d4\u0309"); // O^?
		HTML_CODE_TABLE.put("&#7893;", "\u006f\u0309"); // o^?
		HTML_CODE_TABLE.put("&#7894;", "\u00d4\u0303"); // O^~
		HTML_CODE_TABLE.put("&#7895;", "\u006f\u0303"); // o^~
		HTML_CODE_TABLE.put("&#7896;", "\u00d4\u0323"); // O^.
		HTML_CODE_TABLE.put("&#7897;", "\u006f\u0323"); // o^.

		HTML_CODE_TABLE.put("&#7898;", "\u01a0\u0301"); // O+'
		HTML_CODE_TABLE.put("&#7900;", "\u01a0\u0300"); // O+`
		HTML_CODE_TABLE.put("&#7902;", "\u01a0\u0309"); // O+?
		HTML_CODE_TABLE.put("&#7904;", "\u01a0\u0303"); // O+~
		HTML_CODE_TABLE.put("&#7906;", "\u01a0\u0323"); // O+.

		HTML_CODE_TABLE.put("&#7899;", "\u01a1\u0301"); // o+'
		HTML_CODE_TABLE.put("&#7901;", "\u01a1\u0300"); // o+`
		HTML_CODE_TABLE.put("&#7903;", "\u01a1\u0309"); // o+?
		HTML_CODE_TABLE.put("&#7905;", "\u01a1\u0303"); // o+~
		HTML_CODE_TABLE.put("&#7907;", "\u01a1\u0323"); // o+.

		HTML_CODE_TABLE.put("&#7910;", "\u0055\u0309"); // U?
		HTML_CODE_TABLE.put("&#7908;", "\u0055\u0323"); // U.

		HTML_CODE_TABLE.put("&#7911;", "\u0075\u0309"); // u?
		HTML_CODE_TABLE.put("&#7909;", "\u0075\u0323"); // u.

		HTML_CODE_TABLE.put("&#7912;", "\u01af\u0301"); // U+'
		HTML_CODE_TABLE.put("&#7914;", "\u01af\u0300"); // U+`
		HTML_CODE_TABLE.put("&#7916;", "\u01af\u0309"); // U+?
		HTML_CODE_TABLE.put("&#7918;", "\u01af\u0303"); // U+~
		HTML_CODE_TABLE.put("&#7920;", "\u01af\u0323"); // U+.

		HTML_CODE_TABLE.put("&#7913;", "\u01b0\u0301"); // u+'
		HTML_CODE_TABLE.put("&#7915;", "\u01b0\u0300"); // u+`
		HTML_CODE_TABLE.put("&#7917;", "\u01b0\u0309"); // u+?
		HTML_CODE_TABLE.put("&#7919;", "\u01b0\u0303"); // u+~
		HTML_CODE_TABLE.put("&#7921;", "\u01b0\u0323"); // u+.

		HTML_CODE_TABLE.put("&#7926;", "\u0059\u0309"); // Y?
		HTML_CODE_TABLE.put("&#7928;", "\u0059\u0303"); // Y~
		HTML_CODE_TABLE.put("&#7924;", "\u0059\u0323"); // Y.

		HTML_CODE_TABLE.put("&#7927;", "\u0079\u0309"); // y?
		HTML_CODE_TABLE.put("&#7929;", "\u0079\u0303"); // y~
		HTML_CODE_TABLE.put("&#7925;", "\u0079\u0323"); // y.
	}

	private void prepareMIRROR_UNICODE_TABLE() {

		MIRROR_UNICODE_TABLE.put("\u0061\u0301", new Character('\u00e1')); // a' ACUTE
		MIRROR_UNICODE_TABLE.put("\u0061\u0300", new Character('\u00e0')); // a` GRAVE
		MIRROR_UNICODE_TABLE.put("\u0061\u0309", new Character('\u1ea3')); // a? HOOK ABOVE
		MIRROR_UNICODE_TABLE.put("\u0061\u0303", new Character('\u00e3')); // a~ TILDE
		MIRROR_UNICODE_TABLE.put("\u0061\u0323", new Character('\u1ea1')); // a. DOT BELOW

		MIRROR_UNICODE_TABLE.put("\u0103\u0301", new Character('\u1eaf')); // a('
		MIRROR_UNICODE_TABLE.put("\u0103\u0300", new Character('\u1eb1')); // a(`
		MIRROR_UNICODE_TABLE.put("\u0103\u0309", new Character('\u1eb3')); // a(?
		MIRROR_UNICODE_TABLE.put("\u0103\u0303", new Character('\u1eb5')); // a(~
		MIRROR_UNICODE_TABLE.put("\u0103\u0323", new Character('\u1eb7')); // a(.

		MIRROR_UNICODE_TABLE.put("\u00e2\u0301", new Character('\u1ea5')); // a^'
		MIRROR_UNICODE_TABLE.put("\u00e2\u0300", new Character('\u1ea7')); // a^`
		MIRROR_UNICODE_TABLE.put("\u00e2\u0309", new Character('\u1ea9')); // a^?
		MIRROR_UNICODE_TABLE.put("\u00e2\u0303", new Character('\u1eab')); // a^~
		MIRROR_UNICODE_TABLE.put("\u00e2\u0323", new Character('\u1ead')); // a^.

		MIRROR_UNICODE_TABLE.put("\u0065\u0301", new Character('\u00e9')); // e'
		MIRROR_UNICODE_TABLE.put("\u0065\u0300", new Character('\u00e8')); // e`
		MIRROR_UNICODE_TABLE.put("\u0065\u0309", new Character('\u1ebb')); // e?
		MIRROR_UNICODE_TABLE.put("\u0065\u0303", new Character('\u1ebd')); // e~
		MIRROR_UNICODE_TABLE.put("\u0065\u0323", new Character('\u1eb9')); // e.

		MIRROR_UNICODE_TABLE.put("\u00ea\u0301", new Character('\u1ebf')); // e^'
		MIRROR_UNICODE_TABLE.put("\u00ea\u0300", new Character('\u1ec1')); // e^`
		MIRROR_UNICODE_TABLE.put("\u00ea\u0309", new Character('\u1ec3')); // e^?
		MIRROR_UNICODE_TABLE.put("\u00ea\u0303", new Character('\u1ec5')); // e^~
		MIRROR_UNICODE_TABLE.put("\u00ea\u0323", new Character('\u1ec7')); // e^.

		MIRROR_UNICODE_TABLE.put("\u0069\u0301", new Character('\u00ed')); // i'
		MIRROR_UNICODE_TABLE.put("\u0069\u0300", new Character('\u00ec')); // i`
		MIRROR_UNICODE_TABLE.put("\u0069\u0309", new Character('\u1ec9')); // i?
		MIRROR_UNICODE_TABLE.put("\u0069\u0303", new Character('\u0129')); // i~
		MIRROR_UNICODE_TABLE.put("\u0069\u0323", new Character('\u1ecb')); // i.

		MIRROR_UNICODE_TABLE.put("\u006f\u0301", new Character('\u00f3')); // o'
		MIRROR_UNICODE_TABLE.put("\u006f\u0300", new Character('\u00f2')); // o`
		MIRROR_UNICODE_TABLE.put("\u006f\u0309", new Character('\u1ecf')); // o?
		MIRROR_UNICODE_TABLE.put("\u006f\u0303", new Character('\u00f5')); // o~
		MIRROR_UNICODE_TABLE.put("\u006f\u0323", new Character('\u1ecd')); // o.

		MIRROR_UNICODE_TABLE.put("\u00f4\u0301", new Character('\u1ed1')); // o^'
		MIRROR_UNICODE_TABLE.put("\u00f4\u0300", new Character('\u1ed3')); // o^`
		MIRROR_UNICODE_TABLE.put("\u00f4\u0309", new Character('\u1ed5')); // o^?
		MIRROR_UNICODE_TABLE.put("\u00f4\u0303", new Character('\u1ed7')); // o^~
		MIRROR_UNICODE_TABLE.put("\u00f4\u0323", new Character('\u1ed9')); // o^.

		MIRROR_UNICODE_TABLE.put("\u01a1\u0301", new Character('\u1edb')); // o+'
		MIRROR_UNICODE_TABLE.put("\u01a1\u0300", new Character('\u1edd')); // o+`
		MIRROR_UNICODE_TABLE.put("\u01a1\u0309", new Character('\u1edf')); // o+?
		MIRROR_UNICODE_TABLE.put("\u01a1\u0303", new Character('\u1ee1')); // o+~
		MIRROR_UNICODE_TABLE.put("\u01a1\u0323", new Character('\u1ee3')); // o+.

		MIRROR_UNICODE_TABLE.put("\u0075\u0301", new Character('\u00fa')); // u'
		MIRROR_UNICODE_TABLE.put("\u0075\u0300", new Character('\u00f9')); // u`
		MIRROR_UNICODE_TABLE.put("\u0075\u0309", new Character('\u1ee7')); // u?
		MIRROR_UNICODE_TABLE.put("\u0075\u0303", new Character('\u0169')); // u~
		MIRROR_UNICODE_TABLE.put("\u0075\u0323", new Character('\u1ee5')); // u.

		MIRROR_UNICODE_TABLE.put("\u01b0\u0301", new Character('\u1ee9')); // u+'
		MIRROR_UNICODE_TABLE.put("\u01b0\u0300", new Character('\u1eeb')); // u+`
		MIRROR_UNICODE_TABLE.put("\u01b0\u0309", new Character('\u1eed')); // u+?
		MIRROR_UNICODE_TABLE.put("\u01b0\u0303", new Character('\u1eef')); // u+~
		MIRROR_UNICODE_TABLE.put("\u01b0\u0323", new Character('\u1ef1')); // u+.

		MIRROR_UNICODE_TABLE.put("\u0079\u0301", new Character('\u00fd')); // y'
		MIRROR_UNICODE_TABLE.put("\u0079\u0300", new Character('\u1ef3')); // y`
		MIRROR_UNICODE_TABLE.put("\u0079\u0309", new Character('\u1ef7')); // y?
		MIRROR_UNICODE_TABLE.put("\u0079\u0303", new Character('\u1ef9')); // y~
		MIRROR_UNICODE_TABLE.put("\u0079\u0323", new Character('\u1ef5')); // y.

		// Capital

		MIRROR_UNICODE_TABLE.put("\u0041\u0301", new Character('\u00c1')); // A'
		MIRROR_UNICODE_TABLE.put("\u0041\u0300", new Character('\u00c0')); // A`
		MIRROR_UNICODE_TABLE.put("\u0041\u0309", new Character('\u1ea2')); // A?
		MIRROR_UNICODE_TABLE.put("\u0041\u0303", new Character('\u00c3')); // A~
		MIRROR_UNICODE_TABLE.put("\u0041\u0323", new Character('\u1ea0')); // A.

		MIRROR_UNICODE_TABLE.put("\u0102\u0301", new Character('\u1eae')); // A('
		MIRROR_UNICODE_TABLE.put("\u0102\u0300", new Character('\u1eb0')); // A(`
		MIRROR_UNICODE_TABLE.put("\u0102\u0309", new Character('\u1eb2')); // A(?
		MIRROR_UNICODE_TABLE.put("\u0102\u0303", new Character('\u1eb4')); // A(~
		MIRROR_UNICODE_TABLE.put("\u0102\u0323", new Character('\u1eb6')); // A(.

		MIRROR_UNICODE_TABLE.put("\u00c2\u0301", new Character('\u1ea4'));
		MIRROR_UNICODE_TABLE.put("\u00c2\u0300", new Character('\u1ea6'));
		MIRROR_UNICODE_TABLE.put("\u00c2\u0309", new Character('\u1ea8'));
		MIRROR_UNICODE_TABLE.put("\u00c2\u0303", new Character('\u1eaa'));
		MIRROR_UNICODE_TABLE.put("\u00c2\u0323", new Character('\u1eac'));

		MIRROR_UNICODE_TABLE.put("\u0045\u0309", new Character('\u1eba'));
		MIRROR_UNICODE_TABLE.put("\u0045\u0303", new Character('\u1ebc'));
		MIRROR_UNICODE_TABLE.put("\u0045\u0323", new Character('\u1eb8'));

		MIRROR_UNICODE_TABLE.put("\u00ca\u0301", new Character('\u1ebe'));

		MIRROR_UNICODE_TABLE.put("\u00ca\u0300", new Character('\u1ec0'));
		MIRROR_UNICODE_TABLE.put("\u00ca\u0309", new Character('\u1ec2'));
		MIRROR_UNICODE_TABLE.put("\u00ca\u0303", new Character('\u1ec4'));
		MIRROR_UNICODE_TABLE.put("\u00ca\u0323", new Character('\u1ec6'));

		MIRROR_UNICODE_TABLE.put("\u0049\u0309", new Character('\u1ec8'));
		MIRROR_UNICODE_TABLE.put("\u0049\u0323", new Character('\u1eca'));

		MIRROR_UNICODE_TABLE.put("\u004f\u0309", new Character('\u1ece'));
		MIRROR_UNICODE_TABLE.put("\u004f\u0323", new Character('\u1ecc'));

		MIRROR_UNICODE_TABLE.put("\u00d4\u0301", new Character('\u1ed0'));
		MIRROR_UNICODE_TABLE.put("\u00d4\u0300", new Character('\u1ed2'));
		MIRROR_UNICODE_TABLE.put("\u00d4\u0309", new Character('\u1ed4'));
		MIRROR_UNICODE_TABLE.put("\u00d4\u0303", new Character('\u1ed6'));
		MIRROR_UNICODE_TABLE.put("\u00d4\u0323", new Character('\u1ed8'));

		MIRROR_UNICODE_TABLE.put("\u01a0\u0301", new Character('\u1eda'));
		MIRROR_UNICODE_TABLE.put("\u01a0\u0300", new Character('\u1edc'));
		MIRROR_UNICODE_TABLE.put("\u01a0\u0309", new Character('\u1ede'));
		MIRROR_UNICODE_TABLE.put("\u01a0\u0303", new Character('\u1ee0'));
		MIRROR_UNICODE_TABLE.put("\u01a0\u0323", new Character('\u1ee2'));

		MIRROR_UNICODE_TABLE.put("\u0055\u0309", new Character('\u1ee6'));
		MIRROR_UNICODE_TABLE.put("\u0055\u0323", new Character('\u1ee4'));

		MIRROR_UNICODE_TABLE.put("\u01af\u0301", new Character('\u1ee8'));
		MIRROR_UNICODE_TABLE.put("\u01af\u0300", new Character('\u1eea'));
		MIRROR_UNICODE_TABLE.put("\u01af\u0309", new Character('\u1eec'));
		MIRROR_UNICODE_TABLE.put("\u01af\u0303", new Character('\u1eee'));
		MIRROR_UNICODE_TABLE.put("\u01af\u0323", new Character('\u1ef0'));

		MIRROR_UNICODE_TABLE.put("\u0059\u0309", new Character('\u1ef6'));
		MIRROR_UNICODE_TABLE.put("\u0059\u0303", new Character('\u1ef8'));
		MIRROR_UNICODE_TABLE.put("\u0059\u0323", new Character('\u1ef4'));
	}

	/**
	 * Convert the composed unicode character to decomposed unicode String.
	 * 
	 * @param composedUniChar The composed unicode character.
	 * @return The decomposed unicode string.
	 */
	public String getUnicodeComposeString(char composedUniChar) {
		if (UNICODE_TABLE.get(new Character(composedUniChar)) != null) {
			return (String) UNICODE_TABLE.get(new Character(composedUniChar));
		} else {
			return composedUniChar + "";
		}
	}

	/**
	 * Decompose composed unicode string and return new string.
	 * 
	 * @param composedUniStr The composed unicode string.
	 * @return The decomposed unicode string.
	 */
	public String getUnicodeComposeString(String composedUniStr) {
		StringBuffer newStr = new StringBuffer();
		int strLen = composedUniStr.length();
		for (int i = 0; i < strLen; i++) {
			char charAtI = composedUniStr.charAt(i);
			String returnStr = (String) UNICODE_TABLE.get(new Character(charAtI));
			if (returnStr != null) {
				newStr.append(returnStr);
			} else {
				newStr.append(charAtI);
			}
		}

		return newStr.toString();
	}

	/**
	 * Decompose Composed Unicode String/HTML Characters Code and return new Decomposed Unicode String. This method is similar to the method above but plus the HTML Character Codes.
	 * 
	 * @param text The text needs to be converted.
	 * @return The decomposed unicode string.
	 */
	public String convertHTMLCode2Unicode(String text) {
		StringBuffer newString = new StringBuffer();
		int textLen = text.length();

		for (int i = 0; i < textLen; i++) {
			char charAtI = text.charAt(i);
			if (i + 2 < textLen && charAtI == '&' && text.charAt(i + 1) == '#' && text.charAt(i + 2) == '7') {
				int indexOfSemiColon = text.indexOf(';', i);

				String aHTMLCode = text.substring(i, indexOfSemiColon + 1);

				aHTMLCode = (String) HTML_CODE_TABLE.get(aHTMLCode);

				if (aHTMLCode != null) {
					newString.append(aHTMLCode);
					i = indexOfSemiColon;
				} else {
					newString.append(charAtI);
				}
			} else {
				String returnStr = (String) UNICODE_TABLE.get(new Character(charAtI));
				if (returnStr != null) {
					newString.append(returnStr);
				} else {
					newString.append(charAtI);
				}
			}
		}
		return newString.toString();
	}

	/**
	 * Convert the decomposed unicode into composed unicode string
	 * 
	 * @param decomposedStr The decomposed unicode string.
	 * @return The composed unicode string.
	 */

	public String getComposedUnicode(String decomposedStr) {

		StringBuffer newStr = new StringBuffer();

		for (int i = 0; i < decomposedStr.length(); i++) {
			if ((i + 1) == decomposedStr.length()) {
				newStr.append(decomposedStr.charAt(i));
				break;
			}
			String aDecChar = "" + decomposedStr.charAt(i) + decomposedStr.charAt(i + 1);
			if (MIRROR_UNICODE_TABLE.get(aDecChar) != null) {
				newStr.append(MIRROR_UNICODE_TABLE.get(aDecChar));
				i++;
			} else {
				newStr.append(decomposedStr.charAt(i));
			}
		}
		return newStr.toString();
	}

}
