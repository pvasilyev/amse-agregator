<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <table width="100%">
            <tr>
                <td class="title">
                    <br/>
                    Название: <br/>
                    <xsl:value-of select="//data[@id='cityPage']//name"/>
                </td>
            </tr>
            <tr>
                <td class="description">
                    <br/>
                    Описание: <br/>
                    <xsl:value-of select="//data[@id='cityPage']//description"/>
                </td>
            </tr>
            <tr>
                <td>
                    <br/>
                    Достопримечательности: <br/>
                    <xsl:for-each select="//data[@id='cityPage']//menu-item">
                        <a>
                            <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                            select="type"/>
                                    </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                        <br/>
                    </xsl:for-each>
                </td>
            </tr>
        </table>
    </xsl:template>
</xsl:stylesheet>
