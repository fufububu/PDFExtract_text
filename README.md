# PdfExtract_text
Extract text from a pdf file using window search coordinates and page range  
My first java program using beautiful **PDF Clown** library (Copyright © 2006-2015 Stefano Chizzolini http://www.pdfclown.org)  

## Getting Started


### Prerequisites
Developed and tested with:
 * Windows 7 64 bit
 * java version "1.8.0_25"
 * Java(TM) SE Runtime Environment (build 1.8.0_25-b18)
 * PDF Clown 0.1.2.0 library of Stefano Chizzolini

### Installing
If you want to modify/compile the program you can download PDFExtract_text.java source otherwise  
download jar file in a directory containing pdf file to be used and and execute it.  
Example:  
  java -jar PDFExtract_text.jar <filename.pdf> 0 0 595 842 27

If you are using Windows you can download DOS batch command interface **PDFExtracttext.cmd** and run it  
  
![alt text](https://github.com/fufububu/PdfExtract_text/blob/master/Image1.gif)

To run the program you will need:
 * a pdf file well build/structured otherwise some text will be missed: its better to perform an Acrobat "PDF Optimizer" action to fix/check pdf structure.
 * x origin, y origin , width and height of desired search area window: dpi must be used for coordinates and dimensions
 * page range to be scanned: 0 means all pages, xx means only page xx, xx-yy means pages from xx to yy

### Example
Download **PDFextract_pdf.jar**,**PDFExtracttext.cmd** and **Testfile.pdf** in your working directory.  
Lets assume we want to extract all the recipients of **Testfile.pdf** mailing file, we need to know recipents page coordinates  
and to obtain this kind of info we first execute a full text extraction of a single page.  
In this example page is A4 size and its dpi dimensions are 595x 842y, we search only page 5.  
Command and output will be as follows:  
  
![alt text](https://github.com/fufububu/PdfExtract_text/blob/master/Image2.gif)  
   
Recipient (Michael Robinson) coordinates are 53x dpi and 137y dpi, width  
of this string is 80 dpi and its height 10 dpi  
*Page : 5  Text [x:53,y:137,w:80,h:10] [font size:11]: Michael Robinson*  
We can now perform an extraction on the entire document using 53x and 137y window search origin  
and 120 as width, a bit more as not all recipients will be of same lenght, and 10 as height. We wil obtain:  
  
![alt text](https://github.com/fufububu/PdfExtract_text/blob/master/Image3.gif)  
  
You will notice not all recipiens are in the same position (pages 2,6 and 12) so we need to enlarge windows search width to 20dpi and obtain:    
  
![alt text](https://github.com/fufububu/PdfExtract_text/blob/master/Image4.gif)  
  
