<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:include href="common.xsl"/>
    <xsl:template name="main">

        <table width="100%">
            <tr>
                <td class="title">
                    <br/>
                    Название страны: <br/>
                    <xsl:value-of select="//description-model/name"/>
                </td>
            </tr>
            <tr>
                <td class="description">
                    <br/>
                    Описание страны: <br/>
                    <xsl:value-of select="//description"/>
                </td>
            </tr>
            <tr>
                <td>
                    <br/>
                    Города страны: <br/>
                    <xsl:for-each select="//menu-item">
                        <a>
                            <xsl:attribute name="href">city.xml?id=<xsl:value-of select="id"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                        <br/>
                    </xsl:for-each>

                </td>

            </tr>
            <!--<tr>-->
            <!--<td>-->
            <!--<xsl:value-of select="//description-model/name"/>-->
            <!--</td>-->
            <!--<td rowspan="2" valign="top">-->
            <!--<xsl:for-each select="//menu-item">-->
            <!--<a>-->
            <!--<xsl:attribute name="href">city.xml?id=<xsl:value-of select="id"/>-->
            <!--</xsl:attribute>-->
            <!--<xsl:value-of select="name"/>-->
            <!--</a><br/>-->
            <!--</xsl:for-each>-->
            <!---->
            <!--</td>-->
            <!--</tr>-->
            <!--<tr>-->
            <!--<td align="justify">-->
            <!--<xsl:value-of select="//description"/>-->
            <!--</td>-->
            <!--</tr>-->
        </table>

    </xsl:template>
</xsl:stylesheet>