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

    <xsl:template match="page/data/collection" mode="show">
        <xsl:apply-templates select="attraction" mode="attractiondescription-xml"/>
    </xsl:template>

    <xsl:template match="attraction" mode="attractiondescription-xml">
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
            <tr align="left">
                <td>
                    <xsl:if test="tab-map//description = 'true'">
                        <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=description">
                            Общая информация
                        </a>
                    </xsl:if>

                    <xsl:if test="tab-map//images = 'true'">
                        |
                        <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=images">
                            Галерея
                        </a>
                    </xsl:if>

                    <xsl:if test="tab-map//list = 'true'">
                        |
                        <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=list">
                            <xsl:if test="type = 'City'">
                                Достопримечательности города
                            </xsl:if>
                            <xsl:if test="type = 'Country'">
                                Города страны
                            </xsl:if>
                            <xsl:if test="type = 'Continent'">
                                Страны континента
                            </xsl:if>
                        </a>
                    </xsl:if>
                </td>
            </tr>
            <tr>
                <td class="description" colspan="1" width="30%">
                    <xsl:if test=".//images-array != ''">
                        <xsl:apply-templates select=".//images-array//string" mode="attractiondescription-xml"/>
                    </xsl:if>

                    <xsl:if test="description != ''">
                        <xsl:value-of select="description" disable-output-escaping="yes"/>
                    </xsl:if>


                    <xsl:if test=".//menu-item != ''">
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
                                    <a href="attractiondescription.xml?id={id}&amp;type={type}">
                                        <xsl:value-of select="name" disable-output-escaping="yes"/>
                                    </a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                </td>
            </tr>
            <!--<tr>
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
                        <xsl:apply-templates select=".//images-array//string" mode="attractiondescription-xml"/>
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
                        &lt;!&ndash;<xsl:if test="type = 'City'">&ndash;&gt;
                        &lt;!&ndash;Достопримечательности города&ndash;&gt;
                        &lt;!&ndash;</xsl:if>&ndash;&gt;
                        &lt;!&ndash;&ndash;&gt;
                        &lt;!&ndash;Смотрите также:&ndash;&gt;
                        <br/>

                        <ul>
                            <xsl:for-each select="//data[@id='showAttractionDesc']//menu-item">
                                <li>
                                    <a href="attractiondescription.xml?id={id}&amp;type={type}">
                                        <xsl:value-of select="name" disable-output-escaping="yes"/>
                                    </a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>

                </td>
            </tr>    -->

        </table>
    </xsl:template>

    <xsl:template match="images-array">
        <xsl:apply-templates select=".//string" mode="attractiondescription-xml"/>
    </xsl:template>

    <xsl:template match="string" mode="attractiondescription-xml">
        <img src="{.}" class="big_image"/>
        <span style="padding:0px 10px;"/>
    </xsl:template>

    <xsl:template match="description">
        <xsl:value-of select="."/>
    </xsl:template>


    <xsl:template name="mainAttractionDescriptionContainer">

    </xsl:template>


</xsl:stylesheet>
