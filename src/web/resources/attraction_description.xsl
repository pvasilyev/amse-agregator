<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="collection" mode="show">
        <xsl:for-each select="attraction">
            <table>
                <td>
                   <img src="{image}" class="little_image"/><br/>
                </td>
                <td>
                    <a href="attraction_description.xml" type="submit"><xsl:value-of select="name"/></a>
                    <strong><xsl:value-of select="description"/></strong><br/>
                    <strong><xsl:value-of select="image"/></strong>
                </td>
            </table>
       </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>