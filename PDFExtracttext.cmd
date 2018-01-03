@echo off
rem FB 27/12/1987
rem Text extraction from pdf
rem based on PDFClown library and relative examples
set argC=0
for %%x in (%*) do Set /A argC+=1
if %argC% EQu 6 goto continue
@echo *****************************************
@echo * Java program to extract text from pdf *
@echo *****************************************
@echo To get text point coordinates information first perform a search on a 
@echo   given pageusing the whole page dimension (points)
@echo   Example: for an A4 (595x842 points) at page 27 the command could be
@echo            java -jar Extract_text.jar ^<filename.pdf^> 0 0 595 842 27
@echo:
@echo NOTE: pdf should be well structured/build otherwise some text will be missed
@echo       It's best to perform an Acrobat 'PDF Optimizer' command before runnig 
@echo       this program
@echo: 
@echo Proper Usage is: 
@echo  PDFExtracttext ^<filename^> ^<xpos^> ^<ypos^> ^<width^> ^<height^> ^<pages^>
@echo               filename = file to check
@echo               xpos     = window width  (points)
@echo               ypos     = window height (points)
@echo               width    = window width  (points)
@echo               height   = window height (points)
@echo               pages    = pages to check 0 = all pages
@echo                                         x = page x
@echo                                         x-y = from page x to page y (included)
goto end

:continue
java -jar PDFExtract_text.jar %1 %2 %3 %4 %5 %6


:end
pause