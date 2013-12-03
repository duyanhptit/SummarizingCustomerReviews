package vn.ptit.anhdinh.wordnet;

import org.junit.Test;

import vn.ptit.anhdinh.wordnet.utils.BuildWordNetUtils;

public class BuildWordNetUtilsTest {

	@Test
	public void TestBuildCluster() {
		BuildWordNetUtils.buildCluster("có ích");
		BuildWordNetUtils.buildCluster("có ích");
	}
}
