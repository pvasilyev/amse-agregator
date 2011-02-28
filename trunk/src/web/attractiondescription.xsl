<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
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

    <!--<xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>-->

    <xsl:template match="page/data/collection" mode="show">
        <div id="main-container">
            <xsl:apply-templates select="attraction" mode="attractiondescription-xml"/>
        </div>
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
                    <!-- -1 - for field name -->
                    <xsl:if test="(count(field-map/node()[text()='true'])-1) != 1">
                        <xsl:if test="field-map/description = 'true'">
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=all"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                Общая информация
                            </a>
                        </xsl:if>

                        <xsl:if test="field-map/description = 'true'">
                            |
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=description"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                Описания
                            </a>
                        </xsl:if>

                        <xsl:if test="field-map/images = 'true'">
                            |
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=images"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                Галерея
                            </a>
                        </xsl:if>

                        <xsl:if test="field-map/list = 'true'">
                            |
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=list"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
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
                    </xsl:if>
                </td>
            </tr>
            <tr>
                <td class="description" colspan="1" width="30%">
                    <!--<xsl:if test="coordinates != ''">-->
                    <table>
                        <tr>
                            <td onload="GDownloadUrl()" onunload="GUnload()">
                                <!--<div id="google-map" style="width: 350px; height: 250px;"/>-->
                                <!--<div id="map_canvas" style="width: 350px; height: 250px;"/>-->
                                <!--<xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>-->
                                <div id="map_canvas" style="width: 350px; height: 250px;"/>
                                <!--	<xsl:apply-templates select="//data[@id='maps']//record" mode="googlemap"/>
                                -->
                            </td>
                            <td width="20px"></td>
                            <td>
                                <xsl:if test="images-array != ''">
                                    <xsl:variable name="count-of-images" select="count(images-array/string)"/>
                                    <table width="350px">
                                        <tr style="heigth: 150px">
                                            <td>

                                                <xsl:apply-templates select="images-array/string[1]"
                                                                     mode="attractiondescription-xml-mini"/>
                                            </td>
                                            <td>

                                                <xsl:apply-templates select="images-array/string[2]"
                                                                     mode="attractiondescription-xml-mini"/>
                                            </td>
                                            <td>

                                                <xsl:apply-templates select="images-array/string[3]"
                                                                     mode="attractiondescription-xml-mini"/>
                                            </td>
                                        </tr>
                                        <!-- Если меньше 4х картинок -->
                                        <xsl:if test="$count-of-images &lt; 3">
                                            <tr>
                                                <td class="small_image"/>
                                            </tr>
                                        </xsl:if>
                                        <tr style="heigth: 150px;">
                                            <td>
                                                <xsl:apply-templates select="images-array/string[4]"
                                                                     mode="attractiondescription-xml-mini"/>
                                            </td>
                                            <td>

                                                <xsl:apply-templates select="images-array/string[5]"
                                                                     mode="attractiondescription-xml-mini"/>
                                            </td>
                                            <td>

                                                <xsl:apply-templates select="images-array/string[6]"
                                                                     mode="attractiondescription-xml-mini"/>
                                            </td>
                                        </tr>
                                    </table>
                                </xsl:if>
                            </td>
                        </tr>
                    </table>
                    <!--</xsl:if>-->


                    <xsl:if test="images-array != '' and description=''">
                        <xsl:apply-templates select=".//images-array//string" mode="attractiondescription-xml"/>
                    </xsl:if>
                    <div id="aaa">
                        <xsl:if test="description != ''">
                            <xsl:value-of select="description" disable-output-escaping="yes"/>
                            <br/>
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=description">еще
                                описания
                            </a>

                        </xsl:if>
                    </div>


                    <xsl:if test="attraction-list/menu-item != ''">
                        <xsl:if test="type = 'City'">
                            Достопримечательности города
                        </xsl:if>
                        <xsl:if test="type = 'Country'">
                            Города страны
                        </xsl:if>
                        <xsl:if test="type = 'Continent'">
                            Страны континента
                        </xsl:if>
                        <br/>

                        <ul>
                            <xsl:for-each select="attraction-list/menu-item">
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
        </table>
    </xsl:template>

    <xsl:template match="images-array">
        <xsl:apply-templates select="string" mode="attractiondescription-xml"/>
    </xsl:template>

    <xsl:template match="string" mode="attractiondescription-xml">
        <img src="{.}" class="big_image"/>
        <span style="padding:0px 10px;"/>
    </xsl:template>

    <xsl:template match="string" mode="attractiondescription-xml-mini">
        <img src="{.}" class="small_image"/>
        <span style="padding:20px 0px;"/>
    </xsl:template>

    <xsl:template match="description">
        <xsl:value-of select="."/>
    </xsl:template>

</xsl:stylesheet>
