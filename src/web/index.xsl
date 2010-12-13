<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>


        <!-- @todo заменить это на параметризуемый вызов блоков  -->
    <xsl:template name="leftmenu">
        <xsl:call-template name="countryTopBlock"/>
        <xsl:call-template name="continentSelectBlock"/>
    </xsl:template>
    
    <xsl:template name="main">
        <xsl:call-template name="indexMainContent"/> 
    </xsl:template>
    
        <!-- @todo заменить это на параметризуемый вызов блоков  -->
    <xsl:template name="rightmenu">
        <xsl:call-template name="attractionTopBlock"/> 
    </xsl:template>
    
    
    <xsl:template name="indexMainContent">
        <table class="photoTable">
            <tr>
                <xsl:for-each select="page/data[@id = 'topCity']/collection/record">
                    <td>
                        <div class="photoTableItem">
                            <a>
                                <xsl:attribute name="title">
                                    <xsl:value-of select="cells/cell[1]/value"/>
                                </xsl:attribute>
                                <xsl:attribute name="href">attractiondescription.xml?type=City&amp;id=<xsl:value-of select="cells/cell[2]/value"/></xsl:attribute>
                                <img width="160" height="139">
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="cells/cell[3]/value"/>
                                    </xsl:attribute>
                                </img>
                            </a>
                            <br/>
                            <a>
                                <xsl:attribute name="title">
                                    <xsl:value-of select="cells/cell[4]/value"/>
                                </xsl:attribute>
                                <xsl:attribute name="href">attractiondescription.xml?type=Country&amp;id=<xsl:value-of select="cells/cell[5]/value"/></xsl:attribute>
                                <xsl:value-of select="cells/cell[4]/value" disable-output-escaping="yes"/>
                            </a>
                            ::
                            <a>
                                <xsl:attribute name="title">
                                    <xsl:value-of select="cells/cell[1]/value"/>
                                </xsl:attribute>
                                <xsl:attribute name="href">attractiondescription.xml?type=City&amp;id=<xsl:value-of select="cells/cell[2]/value"/></xsl:attribute>
                                <xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>
                            </a>
                        </div>
                    </td>
                    <xsl:if test="position() mod 3 = 0">
                        <tr/>
                    </xsl:if>
                </xsl:for-each>
            </tr>
        </table>
    </xsl:template>
    
</xsl:stylesheet>
