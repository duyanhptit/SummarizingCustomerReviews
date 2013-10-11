package vn.ptit.anhdinh.wordnet;

import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vn.ptit.anhdinh.scr.utils.FileUtils;

public class BuildWordNet {

	public static void main(String[] args) throws Exception {
		String inputFile = "data/commentsOfZalo.xml";
		Document document = FileUtils.ReadFileXML(inputFile);
		List<String> adjectives = new LinkedList<String>();

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression xPathExpression = xPath.compile("//w[@pos=\"A\"]");
		NodeList nodeAdjectives = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodeAdjectives.getLength(); i++) {
			Element elAdjective = (Element) nodeAdjectives.item(i);
			NodeList nodeValue = elAdjective.getChildNodes();
			String adjective = nodeValue.item(0).getNodeValue();
			System.out.println(adjective);
			adjectives.add(adjective);
		}
	}
}
