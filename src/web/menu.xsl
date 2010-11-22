<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">

            <table width="80%" >
                <tr>
                    <xsl:for-each select="//collection">
                        <td colspan="2" valign="top" width="20%">
                            <xsl:value-of select="menu-item[1]/name"/><br/>
                                <xsl:for-each select="menu-item">
                                  <xsl:if test="position() != 1">
                                    <a>
                                        <xsl:attribute name="href">country.xml?id=<xsl:value-of select="id"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="name"/>
                                    </a>
                                    <br/>
                                  </xsl:if>
                            </xsl:for-each>

                        </td>
                    </xsl:for-each>
                </tr>
            </table>

    </xsl:template>
</xsl:stylesheet>
