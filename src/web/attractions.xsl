<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:for-each select="//collection">
            <xsl:for-each select="attraction">

                <!--<xsl:if test="type != ''">-->

                <!--<xsl:variable name="c">City</xsl:variable>-->
                <!--<xsl:if test="type = 'City')">-->
                <!--<text class="attraction_name_big">-->
                <!--<xsl:value-of select="type"/>-->
                <!--</text>-->
                <!--</xsl:if>-->
                <!--</xsl:if>-->

                <table>
                    <tr>
                        <td>

                        </td>
                        <td align="left">
                            <i class="b-serp-item__favicon"></i>
                            <h2 class="b-serp-item__title">
                                <a>
                                    <xsl:attribute name="href">city.xml?id=<xsl:value-of select="id"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="name"/>
                                </a>
                                <br/>

                            </h2>
                        </td>
                    </tr>
                    <tr>
                        <td class="b-serp-item__text" colspan="2">
                            <xsl:if test="description != '')">
                                <xsl:value-of select="description"/>
                            </xsl:if>
                        </td>
                    </tr>

                </table>
            </xsl:for-each>
        </xsl:for-each>

    </xsl:template>


</xsl:stylesheet>