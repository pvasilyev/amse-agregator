<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <!--<xsl:key name="attraction-item" match="/page/data/basket/content/basket-market-item" use="@uid"/>-->


    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>


    <xsl:template match="collection" mode="show">
        <xsl:for-each select="attraction">
            <strong><xsl:value-of select="name"/></strong>-
            <strong><xsl:value-of select="description"/></strong><br/>
        </xsl:for-each>
    </xsl:template>

    <!--<xsl:template match="name" mode="show">
        <xsl:value-of select="name"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="description" mode="show">
        <xsl:value-of select="description"/>
        <xsl:apply-templates/>
    </xsl:template>-->
</xsl:stylesheet>