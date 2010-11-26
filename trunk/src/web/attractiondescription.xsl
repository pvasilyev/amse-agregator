<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>


    <xsl:template match="//collection" mode="show">
        <xsl:for-each select="attraction">
            <xsl:if test="type = 'Error'">
                <br/>
                <text-area class="description">Информация временно недоступна.
                </text-area>
            </xsl:if>

            <table width="100%">
                <tr>
                    <td align="left" class="title">
                         <xsl:value-of select="name"/>
                    </td>
                </tr>
                <tr>
                    <td width="30%">
                        <xsl:if test="images-array != ''">
                            <!--<img src="{image}" class="big_image"/>-->
                            <xsl:for-each select="//data[@id='showAttractionDesc']//images-array">
                                <xsl:for-each select="//data[@id='showAttractionDesc']//string">
                                    <img src="{.}" class="big_image"/>
                                   <span style="padding:0px 10px;"/>
                                </xsl:for-each>

                            </xsl:for-each>
                        </xsl:if>

                    </td>
                </tr>
                <tr>
                    <td class="description" colspan="2">
                        <xsl:if test="description != ''">
                            Описание:
                            <xsl:value-of select="description"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="copyright">
                        <xsl:if test="website != ''">
                            © <xsl:value-of select="website"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text" colspan="2">
                        <xsl:if test="architect != ''">
                            Архитектор:
                            <xsl:value-of select="architect"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text" colspan="2">
                        <xsl:if test="adress != ''">
                            Адресс:
                            <xsl:value-of select="adress"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text" colspan="2">
                        <xsl:if test="buildDate != ''">
                            Дата постройки:
                            <xsl:value-of select="buildDate"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td width="80%">
                        <xsl:for-each select="//data[@id='showAttractionDesc']//menu-item">
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
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>