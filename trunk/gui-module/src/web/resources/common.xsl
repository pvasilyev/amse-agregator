<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">
        <html>
        <head>
            <title><xsl:text>Агрегатор</xsl:text></title>
            <link type="text/css" rel="stylesheet" href="basket.css"/>
        </head>
        <body>
            <xsl:call-template name="find"/>
            <xsl:call-template name="main"/>
        </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <table width="50%" align="center">
            <tr>
                <td>
                    <input type="text"/>
                </td>
                <td colspan="4" class="footer" align="center">
                    <input type="submit" value="Поиск" class="submit-button"/>
                </td>
            </tr>
        </table>
    </xsl:template>

</xsl:stylesheet>