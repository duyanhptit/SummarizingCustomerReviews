package vn.edu.ptit.wordnet.utils;

import org.junit.Assert;
import org.junit.Test;

public class DecomposeUnicodeTest {

	@Test
	public void TestGetComposeUnicode() {
		String test = "1 chữ tệ cho zalo này,không hài lòng tý nào cả";
		String expect = "1 chữ tệ cho zalo này,không hài lòng tý nào cả";

		Assert.assertFalse("This case expect false", expect.equals(test));
		DecomposeUnicode decomposeUnicode = new DecomposeUnicode();
		String actual = decomposeUnicode.getComposedUnicode(test);
		Assert.assertTrue("This case expect true", expect.equals(actual));
	}

	@Test
	public void TestComposeUnicode() {

	}
}
