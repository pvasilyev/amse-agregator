<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" encoding="UTF-8"/>
<xsl:template match="/">
<html>
  <body>  	
  	<xsl:apply-templates select="//data[@id='forPrint']//record" mode="print-xml"/>
  </body>
  </html>
</xsl:template>
 <xsl:template match="record" mode="print-xml">
 	<br/><FONT size="5"><b>Название достопримечательности : </b></FONT><xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>
    <xsl:if test="cells/cell[2]/value != ' '">
    	<p><b> Координаты: </b><xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>, <xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>
    	</p>
	</xsl:if>
    <xsl:if test="cells/cell[4]/value != ' '">
    	<p><b> INFO : </b><xsl:value-of select="cells/cell[4]/value" disable-output-escaping="yes"/>
        </p>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>