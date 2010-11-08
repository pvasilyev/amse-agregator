<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>
    <!--<xsl:include href="yandex.xsl"/>-->

    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="collection" mode="show">
        <xsl:for-each select="attraction">
            <div>
                <ol>
                    <li>
                        <img src="{image}" class="little_image"/>
                        <h2 class="b-serp-item__title">
                            <xsl:variable name="id" select="@uid"/>

                            <a class="b-serp-item__title__link" tabindex="2"
                               href="attractiondescription.xml"
                                    >
                                <xsl:value-of select="name"/>
                            </a>
                        </h2>
                        <div class="b-serp-item__text" name="id" value="{uid}">
                            <xsl:value-of select="description"/>
                        </div>
                    </li>
                </ol>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>