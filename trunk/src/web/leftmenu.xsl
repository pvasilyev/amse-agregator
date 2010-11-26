<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="menulist">
        <xsl:for-each select="//collection">
            <xsl:for-each select="left-menu-item">

                <a>
                    <xsl:attribute name="href">country.xml?id=<xsl:value-of select="id"/>
                    </xsl:attribute>
                    <xsl:value-of select="name"/>
                </a>
                <br/>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
