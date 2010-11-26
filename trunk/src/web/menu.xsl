<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">

        <table width="100%">
            <tr>
                <xsl:for-each select="//collection">
                    <xsl:sort order="ascending" select="menu-item[1]/name"/>
                    <td valign="top" width="20%" align="left">
                        <table>
                            <tr>
                                <xsl:value-of select="menu-item[1]/name"/>
                            </tr>
                            <xsl:for-each select="menu-item">
                                <xsl:sort order="ascending" select="name"/>

                                <tr>
                                    <xsl:if test="position() != 1">
                                        <xsl:if test="position() mod 2 = 0">
                                            <td class="description-grey">
                                            <a>
                                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of
                                                        select="id"/>&amp;type=Country</xsl:attribute>
                                                <xsl:value-of select="name"/>
                                            </a>
                                            </td>
                                        </xsl:if>
                                        <xsl:if test="position() mod 2 != 0">
                                            <td class="description">
                                            <a>
                                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of
                                                        select="id"/>&amp;type=Country</xsl:attribute>
                                                <xsl:value-of select="name"/>
                                            </a>
                                            </td>
                                        </xsl:if>
                                    </xsl:if>

                                </tr>
                            </xsl:for-each>
                        </table>
                        <xsl:value-of select="menu-item[1]/name"/>
                        <br/>


                    </td>
                </xsl:for-each>
            </tr>
        </table>

    </xsl:template>
</xsl:stylesheet>
