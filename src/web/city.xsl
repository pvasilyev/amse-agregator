<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>


    <xsl:template match="/">
         <html>
        <head>
            <title><xsl:text>Simple City</xsl:text></title>
        </head>
        <body>
            <table width="80%" >
                <tr>
                    <td>
                        <xsl:value-of select="//name"/>
                    </td>
                    <td rowspan="2" valign="top">
                        <xsl:for-each select="//menu-item">
                            <a>
                                        <xsl:attribute name="href">attr.xml?id=<xsl:value-of select="id"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="name"/>
                            </a><br/>
                        </xsl:for-each>

                    </td>
                </tr>
                <tr>
                    <td align="justify">
                        <xsl:value-of select="//description"/>
                    </td>
                </tr>
            </table>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
