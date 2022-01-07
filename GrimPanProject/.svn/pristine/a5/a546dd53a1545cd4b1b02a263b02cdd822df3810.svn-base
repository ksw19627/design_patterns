package hufs.cse.grimpan4;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import org.w3c.dom.Element;

public class SVG2GrimShapeTranslator {

	public static GrimShape translateSVG2Shape(Element gelem){

		GrimShape grimShape =  null;

		String pathDefString = gelem.getAttribute("d");
		//System.out.println("<!-- path def "+pathDefString+" -->");
		SVGPath2Path2DParser p2lParser = new SVGPath2Path2DParser(pathDefString);
		ArrayList<Path2D> p2dList = p2lParser.getPath2DList();
		//System.out.println("p2dlist size="+p2dList.size());
		if (p2dList.size()==0){
			return null;
		}
		grimShape =  new GrimShape(p2dList.get(0));

		String styleDefString = gelem.getAttribute("style");
		if (!styleDefString.equals("")){
			String[] styleAttrs = styleDefString.split(";");
			if (styleAttrs.length <= 2){ // fill style
				grimShape.setGrimColor(getColorFromRGBString(styleAttrs[0]));
				grimShape.setGrimFill(true);

			}
			else { // non fill style
				grimShape.setGrimColor(getColorFromRGBString(styleAttrs[1]));
				grimShape.setGrimFill(false);
				grimShape.setGrimStrokeWidth(getStrokeWidth(styleAttrs[2])); 
			}
			return grimShape;
		}
		styleDefString = gelem.getAttribute("fill");
		if (styleDefString.length()>0 && styleDefString.charAt(0)=='#'){
			grimShape.setGrimColor(getColorFromHexaString(styleDefString));
			grimShape.setGrimFill(true);

			return grimShape;
		}
		else if(styleDefString.equals("none")){
			grimShape.setGrimColor(getColorFromHexaString(styleDefString));
			grimShape.setGrimFill(false);
		}

		return grimShape;
	}
	static Color getColorFromRGBString(String rgbStr){
		Color res = new Color(0, 0, 0);
		int istart = rgbStr.indexOf("rgb(");
		if (istart == -1) return res;
		int iend = rgbStr.indexOf(")");
		if (iend == -1) return res;
		//System.out.println("rgbStr="+rgbStr.substring(istart+4, iend));
		String[] rgbVals = rgbStr.substring(istart+4, iend).split(",");
		if (rgbVals.length != 3) return res;

		res = new Color(Integer.parseInt(rgbVals[0]), Integer.parseInt(rgbVals[1]), Integer.parseInt(rgbVals[2]));
		return res;
	}
	static Color getColorFromHexaString(String hexStr){
		Color res = new Color(0, 0, 0);
		if (hexStr.length()<7) return res;

		String redHex = hexStr.substring(1, 3);
		String greHex = hexStr.substring(3, 5);
		String bluHex = hexStr.substring(5);

		res = new Color(Integer.parseInt(redHex,16), Integer.parseInt(greHex,16), 
				Integer.parseInt(bluHex,16));
		return res;
	}
	static float getStrokeWidth(String widStr){
		int istart = widStr.indexOf(":");
		if (istart == -1) return 1f;
		String numStr = widStr.substring(istart+1).trim();

		return Float.parseFloat(numStr);
	}
}
