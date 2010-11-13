<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>
    <!--<xsl:include href="yandex.xsl"/>-->

    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="collection" mode="show">
        <xsl:for-each select="attraction">
            <table>
                <tr>
                    <td>
                        <b class="b-serp-item__number">
                            <img src="{image}" class="little_image"/>
                        </b>

                    </td>
                    <td width="80%" align="left">
                        <i class="b-serp-item__favicon"></i>
                        <h2 class="b-serp-item__title">
                            <a class="b-serp-item__title__link" tabindex="2" href="attractiondescription.xml?id={@uid}">
                                <xsl:value-of select="name"/>
                            </a>

                        </h2>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text"  colspan="2">
                        <xsl:value-of select="name"/>
                        kdslkdsjfl slfksdj flsdlfk sldfkjsl dfksjlf
                        ksdjhk sdjskfjs dkfjskdfskfh skdfjh sd
                        ksdjhk sdjskfjs dkfjskdfskfh skdfjh sd
                        ksdjhk sdjskfjs dkfjskdfskfh skdfjh sd
                        ksdjhk sdjskfjs dkfjskdfskfh skdfjh sd
                        ksdjhk sdjskfjs dkfjskdfskfh skdfjh sd
                        ksdjhk sdjskfjs dkfjskdfskfh skdfjh sd
                        skdjhfksdjh skdjf s
                    </td>
                </tr>
            </table>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>