<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="leftmenu">
        <!-- @todo заменить это на параметризуемый вызов блоков  -->
        <xsl:call-template name="countryTopBlock"/>
        <xsl:call-template name="continentSelectBlock"/>
    </xsl:template>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>

    <xsl:template name="rightmenu">
        <!-- @todo заменить это на параметризуемый вызов блоков  -->
        <xsl:call-template name="attractionTopBlock"/>
    </xsl:template>

    <xsl:template match="//collection" mode="show">
        <xsl:for-each select="attraction">
            <xsl:if test="type = 'Error'">
                <br/>
                <div class="description">
                    Информация временно недоступна.
                </div>
            </xsl:if>

            <table width="100%">
                <tr>
                    <td align="left" class="title">
                        <xsl:value-of select="name" disable-output-escaping="yes"/>
                    </td>
                </tr>
                <tr>
                    <td class="description" colspan="2">
                        <xsl:if test="description != ''">
                            Описание:
                            <br/>
                            <xsl:value-of select="description" disable-output-escaping="yes"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="copyright">
                        <xsl:if test="website != ''">
                            ©
                            <xsl:value-of select="website" disable-output-escaping="yes"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text" colspan="2">
                        <xsl:if test="architect != ''">
                            Архитектор:
                            <xsl:value-of select="architect" disable-output-escaping="yes"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text" colspan="2">
                        <xsl:if test="adress != ''">
                            Адресс:
                            <xsl:value-of select="adress" disable-output-escaping="yes"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td class="b-serp-item__text" colspan="2">
                        <xsl:if test="buildDate != ''">
                            Дата постройки:
                            <xsl:value-of select="buildDate" disable-output-escaping="yes"/>
                        </xsl:if>
                    </td>
                </tr>
                <tr>
                    <td width="30%">
                        <xsl:if test="images-array != ''">
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
                    <td width="80%" class="description">
                        <xsl:if test="//data[@id='showAttractionDesc']//menu-item != ''">
                            <xsl:if test="type = 'City'">
                                Достопримечательности города
                            </xsl:if>
                            <xsl:if test="type = 'Country'">
                                Города страны
                            </xsl:if>
                            <xsl:if test="type = 'Continent'">
                                Страны континента
                            </xsl:if>
                            <!--<xsl:if test="type = 'City'">-->
                                <!--Достопримечательности города-->
                            <!--</xsl:if>-->
<!---->
                            <!--Смотрите также:-->
                            <br/>
                            
                            <ul>
                            <xsl:for-each select="//data[@id='showAttractionDesc']//menu-item">
                                <li>
                                <a>
                                    <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                                select="type"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="name" disable-output-escaping="yes"/>
                                </a>
                                </li>
                            </xsl:for-each>
                            </ul>
                        </xsl:if>

                    </td>
                </tr>

            </table>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
