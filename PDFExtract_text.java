
/*
 * FB 02/01/2018
 */

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.pdfclown.documents.Document;
import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.ContentScanner;
import org.pdfclown.documents.contents.objects.ContainerObject;
import org.pdfclown.documents.contents.objects.ContentObject;
import org.pdfclown.documents.contents.objects.Text;
import org.pdfclown.files.File;

public class PDFExtract_text {

    public static void main(String[] args) {

	String SRC;
	String PageRange;
	Integer Xmin;
	Integer Ymin;
	Integer Xwidth;
	Integer Yheight;
	Integer Pagefrom = null;
	Integer PageTo = null;

	File file;
	Integer index;

	if (args.length <= 5) {
	    System.out.println("*****************************************");
	    System.out.println("* Java program to extract text from pdf *");
	    System.out.println("*****************************************");
	    System.out.println("To get point coordinates information first perform a search on a given page");
	    System.out.println("  using the whole page dimension (points)");
	    System.out.println("  Example: for an A4 (595x842 points) at page 27 the command could be");
	    System.out.println("         java -jar Extract_text.jar <filename.pdf> 0 0 595 842 27");
	    System.out.println("NOTE: pdf should be well structured/build otherwise some text will be missed");
	    System.out.println("      It's best to perform an Acrobat 'PDF Optimizer' command ");
	    System.out.println("      before runnig this program");
	    System.out.println("Proper Usage is:");
	    System.out.println("       java -jar Extract_text.jar <filename> <xpos> <ypos> <width> <height> <pages>");
	    System.out.println("       where:");
	    System.out.println("      	    filename = file to check");
	    System.out.println("            xpos     = window width  (dpi)");
	    System.out.println("            ypos     = window height (dpi)");
	    System.out.println("            width    = window width  (dpi)");
	    System.out.println("            height   = window height (dpi)");
	    System.out.println("            pages    = pages to check 0 = all pages");
	    System.out.println("                                      x = page x");
	    System.out.println("                                      x-y = from page x to page y (included)");
	    System.exit(0);
	}

	SRC = args[0];
	Xmin = Integer.valueOf(args[1]);
	Ymin = Integer.valueOf(args[2]);
	Xwidth = Integer.valueOf(args[3]);
	Yheight = Integer.valueOf(args[4]);
	PageRange = args[5];

	String[] Pages = PageRange.split("-");
	if (Pages.length == 1) {
	    if (Integer.valueOf(Pages[0]) == 0) {
		Pagefrom = 1;
		PageTo = 99999;
	    } else {
		Pagefrom = Integer.valueOf(Pages[0]);
		PageTo = Pagefrom;
	    }
	} else if (Pages.length == 2) {
	    Pagefrom = Integer.valueOf(Pages[0]);
	    PageTo = Integer.valueOf(Pages[1]);
	}

	System.out.println("\nProgram start");
	System.out.println("Loading pdf: " + SRC);
	System.out.println(new java.util.Date() + "\n");
	try {
	    file = new File(SRC);
	} catch (Exception e) {
	    throw new RuntimeException(SRC + " file access error.", e);
	}
	System.out.println("Extracting text: ");
	System.out.println(new java.util.Date() + "\n");
	Document document = file.getDocument();
	//
	// 2. Iterating through the document pages...
	for (Page page : document.getPages()) {
	    index = page.getIndex() + 1;
	    if ((index >= Pagefrom) && (index <= PageTo)) {
		extract(new ContentScanner(page), index, Xmin, Ymin, Xwidth, Yheight);
	    } else if (index > PageTo)
		break;
	}
	try {
	    file.close();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println("\nProgram ended");
	System.out.println(new java.util.Date() + "\n");
    }

    /**
     * Scans a content level looking for text. NOTE: Page contents are represented
     * by a sequence of content objects, possibly nested into multiple levels.
     */
    private static void extract(ContentScanner level, Integer index, Integer Xmin, Integer Ymin, Integer Xwidth,
	    Integer Yheight) {

	int xpos;
	int ypos;

	if (level == null)
	    return;

	while (level.moveNext()) {
	    ContentObject content = level.getCurrent();
	    if (content instanceof Text) {
		ContentScanner.TextWrapper text = (ContentScanner.TextWrapper) level.getCurrentWrapper();
		for (ContentScanner.TextStringWrapper textString : text.getTextStrings()) {
		    Rectangle2D textStringBox = textString.getBox();

		    xpos = (int) Math.round(textStringBox.getX());
		    ypos = (int) Math.round(textStringBox.getY());

		    if ((xpos >= Xmin) && (xpos <= Xmin + Xwidth)) {
			if ((ypos >= Ymin) && (ypos <= Ymin + Yheight)) {
			    System.out.println("Page : " + index + "  Text [" + "x:" + Math.round(textStringBox.getX())
				    + "," + "y:" + Math.round(textStringBox.getY()) + "," + "w:"
				    + Math.round(textStringBox.getWidth()) + "," + "h:"
				    + Math.round(textStringBox.getHeight()) + "] [font size:"
				    + Math.round(textString.getStyle().getFontSize()) + "]: " + textString.getText());
			}
		    }
		}

	    } else if (content instanceof ContainerObject) {
		// Scan the inner level!
		extract(level.getChildLevel(), index, Xmin, Ymin, Xwidth, Yheight);
	    }
	}
    }
}
