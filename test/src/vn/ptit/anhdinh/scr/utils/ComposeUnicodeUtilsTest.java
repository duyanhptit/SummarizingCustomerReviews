package vn.ptit.anhdinh.scr.utils;

import org.junit.Assert;
import org.junit.Test;

import vn.ptit.anhdinh.scr.utils.ComposeUnicodeUtils;

public class ComposeUnicodeUtilsTest {
	
	private static final String[] DECOMPOSE_UNICODE = {
		"á", "à", "ã", "ả", "ạ", "Á", "À", "Ã", "Ả", "Ạ",
		"ắ", "ằ", "ẵ", "ẳ", "ặ", "Ắ", "Ằ", "Ẵ", "Ẳ", "Ặ",
		"ấ", "ầ", "ẫ", "ẩ", "ậ", "Ấ", "Ầ", "Ẫ", "Ẩ", "Ậ",
		"é", "è", "ẽ", "ẻ", "ẹ", "É", "È", "Ẽ", "Ẻ", "Ẹ",
		"ế", "ề", "ễ", "ể", "ệ", "Ế", "Ề", "Ễ", "Ể", "Ệ",
		"í", "ì", "ĩ", "ỉ", "ị", "Í", "Ì", "Ĩ", "Ỉ", "Ị",
		"ú", "ù", "ũ", "ủ", "ụ", "Ú", "Ù", "Ũ", "Ủ", "Ụ",
		"ứ", "ừ", "ữ", "ử", "ự", "Ứ", "Ừ", "Ữ", "Ử", "Ự",
		"ó", "ò", "õ", "ỏ", "ọ", "Ó", "Ò", "Õ", "Ỏ", "Ọ",
		"ố", "ồ", "ỗ", "ổ", "ộ", "Ố", "Ồ", "Ỗ", "Ổ", "Ộ",
		"ớ", "ờ", "ỡ", "ở", "ợ", "Ớ", "Ờ", "Ỡ", "Ở", "Ợ",
		"ý", "ỳ", "ỹ", "ỷ", "ỵ", "Ý", "Ỳ", "Ỹ", "Ỷ", "Ỵ"
	};
	private static final String[] COMPOSE_UNICODE = {
		"á", "à", "ã", "ả", "ạ", "Á", "À", "Ã", "Ả", "Ạ",
		"ắ", "ằ", "ẵ", "ẳ", "ặ", "Ắ", "Ằ", "Ẵ", "Ẳ", "Ặ",
		"ấ", "ầ", "ẫ", "ẩ", "ậ", "Ấ", "Ầ", "Ẫ", "Ẩ", "Ậ",
		"é", "è", "ẽ", "ẻ", "ẹ", "É", "È", "Ẽ", "Ẻ", "Ẹ",
		"ế", "ề", "ễ", "ể", "ệ", "Ế", "Ề", "Ễ", "Ể", "Ệ",
		"í", "ì", "ĩ", "ỉ", "ị", "Í", "Ì", "Ĩ", "Ỉ", "Ị",
		"ú", "ù", "ũ", "ủ", "ụ", "Ú", "Ù", "Ũ", "Ủ", "Ụ",
		"ứ", "ừ", "ữ", "ử", "ự", "Ứ", "Ừ", "Ữ", "Ử", "Ự",
		"ó", "ò", "õ", "ỏ", "ọ", "Ó", "Ò", "Õ", "Ỏ", "Ọ",
		"ố", "ồ", "ỗ", "ổ", "ộ", "Ố", "Ồ", "Ỗ", "Ổ", "Ộ",
		"ớ", "ờ", "ỡ", "ở", "ợ", "Ớ", "Ờ", "Ỡ", "Ở", "Ợ",
		"ý", "ỳ", "ỹ", "ỷ", "ỵ", "Ý", "Ỳ", "Ỹ", "Ỷ", "Ỵ"
	};

	@Test
	public void TestGetComposeUnicode() {
		String test = "1 chữ tệ cho zalo này,không hài lòng tý nào cả";
		String expect = "1 chữ tệ cho zalo này,không hài lòng tý nào cả";

		Assert.assertFalse("This case expect false", expect.equals(test));
		ComposeUnicodeUtils composeUnicodeUtils = new ComposeUnicodeUtils();
		String actual = composeUnicodeUtils.getComposedUnicode(test);
		Assert.assertTrue("This case expect true", expect.equals(actual));
	}

	@Test
	public void TestComposeUnicode() {
		ComposeUnicodeUtils composeUnicodeUtils = new ComposeUnicodeUtils();
		for(int i = 0; i < DECOMPOSE_UNICODE.length; i++){
			String character = DECOMPOSE_UNICODE[i];
			String expect = COMPOSE_UNICODE[i];
			String actual = composeUnicodeUtils.getComposedUnicode(character);
			System.out.println("Check convert decompose to compose unicode. Character: " + character);
			Assert.assertTrue("Check convert decompose to compose unicode. Character: " + character, expect.equals(actual) );
		}
	}
}
