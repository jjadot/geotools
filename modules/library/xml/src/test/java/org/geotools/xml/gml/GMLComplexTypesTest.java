package org.geotools.xml.gml;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import javax.naming.OperationNotSupportedException;

import org.geotools.xml.PrintHandler;
import org.geotools.xml.gml.GMLSchema.GMLComplexType;
import org.geotools.xml.schema.Element;
import org.junit.Assert;
import org.junit.Test;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class GMLComplexTypesTest {
	
	@Test
	public void whenEncodePointInPlaceOfLineStringThenThrowOperationNotSupportedException() throws Exception {
		GMLComplexType instance = GMLComplexTypes.LineStringPropertyType.getInstance();
		Point value = mock(Point.class);
		PrintHandler printHandler = mock(PrintHandler.class);
		try {
			instance.encode(null, value, printHandler, null);
			Assert.fail();
		} catch (OperationNotSupportedException e) {}
	}

	@Test
	public void whenEncodeLineStringWithoutElementThenEncodeWithDefaultElement() throws Exception {
		GMLComplexType instance = GMLComplexTypes.LineStringPropertyType.getInstance();
		LineString value = mock(LineString.class);
		when(value.getNumPoints()).thenReturn(2);
		PrintHandler printHandler = mock(PrintHandler.class);
		try {
			instance.encode(null, value, printHandler, null);
			verify(printHandler).startElement(GMLSchema.NAMESPACE, "lineStringProperty", null);
			verify(printHandler).endElement(GMLSchema.NAMESPACE, "lineStringProperty");
		} catch (OperationNotSupportedException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void whenEncodeLineStringWithElementThenEncodeWithGivenElement() throws Exception {
		GMLComplexType instance = GMLComplexTypes.LineStringPropertyType.getInstance();
		LineString value = mock(LineString.class);
		when(value.getNumPoints()).thenReturn(2);
		PrintHandler printHandler = mock(PrintHandler.class);
		Element element = mock(Element.class);
		URI uri = new URI("test");
		when(element.getNamespace()).thenReturn(uri);
		String nameElement = "elementName";
		when(element.getName()).thenReturn(nameElement);
		try {
			instance.encode(element, value, printHandler, null);
			verify(printHandler).startElement(uri, nameElement, null);
			verify(printHandler).endElement(uri, nameElement);
		} catch (OperationNotSupportedException e) {
			Assert.fail();
		}
	}
	
}
