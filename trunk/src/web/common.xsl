<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <!--<xsl:key match="/page/items/item" name="item" use="@uid"/>-->

    <xsl:template match="/">

        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <meta http-equiv="Content-Language" content="ru"/>
                <title>AMSE Туризм</title>
                <link href="style.css" type="text/css" rel="stylesheet"/>
            </head>
            <body>
                <table class="extTable">
                    <tr>
                        <td class="extTableTD">
                            <table class="header">
                                <tr>
                                    <td id="logo_p1">
                                    </td>
                                    <td colspan="2">
                                        <table id="searchContainer">
                                            <xsl:call-template name="find"/>
                                        </table>

                                    </td>
                                </tr>
                                <tr>
                                    <td id="logo_p2">
                                    </td>
                                    <td id="logo_p3">
                                    </td>
                                    <td id="linksContainer">
                                        <table id="linksTable" align="right">
                                            <tr>
                                                <td>
                                                    <a href="menu.xml">Все страны</a>
                                                </td>
                                                <td>
                                                    <a href="#">Top10</a>
                                                </td>
                                                <td>
                                                    <a href="hotels.xml">Отели</a>
                                                </td>
                                                <td>
                                                    <a href="largefind.xml">Расширенный поиск</a>
                                                </td>
                                            </tr>
                                            <!-- Добавить сюда вторую строку дял подчеркиваний ссылок. -->
                                        </table>
                                    </td>
                                </tr>
                            </table>

                            <table class="pageContainer">

                                <tr>
                                    <td id="leftColomn">
                                        <!-- Эту таблицу потмо разумно вынести в отдельный шаблон. !!!Популярные страны/города.!!! -->
                                        <table class="simpleTop">
                                            <tr>
                                                <td class="simpleTopHeader">
                                                    Популярные страны:
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <xsl:call-template name="leftmenulist"/>
                                                </td>
                                            </tr>


                                        </table>


                                        <!-- Этот блок потмо разумно вынести в отдельный шаблон. !!!Выбор стран по континентам!!! -->
                                        <div class="simpleBlock">
                                            <img usemap="#continentsMap" src="images/continents.jpg" alt="" border="0"/>
                                            <map id="map" name="continentsMap">
                                                <area alt="no image"
                                                      href="attractiondescription.xml?id=4cec637b334fdf4ca1723c40&amp;type=Continent"
                                                      shape="poly" coords="15,28,86,21,41,67"
                                                      title="Северная Америка"/>
                                                <area alt="no image"
                                                      href="attractiondescription.xml?id=4cec637e334fdf4ca5723c40&amp;type=Continent"
                                                      shape="poly"
                                                      coords="47,67,73,81,57,113,43,80" title="Южная Америка"/>
                                                <area alt="no image"
                                                      href="attractiondescription.xml?id=4cec637c334fdf4ca2723c40&amp;type=Continent"
                                                      shape="poly"
                                                      coords="87,58,106,21,139,27,129,51,111,57" title="Европа"/>
                                                <area alt="no image"
                                                      href="attractiondescription.xml?id=4cec637e334fdf4ca4723c40&amp;type=Continent"
                                                      shape="poly"
                                                      coords="138,36,181,42,169,72,132,70,127,53,138,52" title="Азия"/>
                                                <area alt="no image"
                                                      href="attractiondescription.xml?id=4cec637d334fdf4ca3723c40&amp;type=Continent"
                                                      shape="poly"
                                                      coords="102,56,121,58,134,72,115,96,95,70" title="Африка"/>
                                                <area alt="no image"
                                                      href="attractiondescription.xml?id=4cec637f334fdf4ca6723c40&amp;type=Continent"
                                                      shape="poly"
                                                      coords="148,108,177,110,189,91,171,79,147,88"
                                                      title="Австралия и Океания"/>
                                            </map>
                                        </div>

                                    </td>
                                    <td id="centerColomn">
                                        <!-- Этот блок потмо разумно вынести в отдельный шаблон. !!!Города с картинками!!! -->
                                        <xsl:call-template name="main"/>
                                        
                                    </td>
                                    <td id="rightColomn">
                                        <table class="attractionsTop">
                                            <tr>
                                                <td class="attractionsTopHeader">
                                                    Популярные достопримечательности:
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <xsl:call-template name="rightmenulist"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>

                            <div class="copyright" align="left">
                                Это копирайт (c)
                                <br/>
                                2010
                            </div>

                        </td>
                    </tr>
                </table>
            </body>

        </html>


    </xsl:template>

    

    <xsl:template name="find">
        <!--<form action="attractions.xml" method="post">-->
            <tr>
                <td id="searchForm">
                    <form id="searchForm" method="POST" action="attractions.xml">
                        <input type="text" id="search" name="findTextBox"/>
                    </form>
                </td>
                <td class="searchButtonTd">
                    <a class="buttonLink" onclick="">Найти</a>
                </td>
            </tr>
        <!--</form>-->
    </xsl:template>

    <xsl:template name="leftmenulist">
        <table>
            <xsl:for-each select="//data[@id='leftMenu']//left-menu-item">
                <xsl:sort order="ascending" select="name"/>
                <!-- Начало элемента -->
                <tr>
                    <td class="simpleTopItemTitle">
                        <a class="left-menu-link">
                            <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                        select="type"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="simpleTopItemContent">
                        <a class="left-menu-link">
                            <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                        select="type"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                            <a class="left-menu-link">
                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                            select="type"/>
                                </xsl:attribute>
                                <xsl:value-of select="name"/>
                            </a>

                        </a>
                    </td>
                </tr>
                <!-- конец элемента -->

                <br/>
            </xsl:for-each>
        </table>

    </xsl:template>

    <xsl:template name="rightmenulist">
        <table class="attractionsTopItemFirst">
            <xsl:for-each select="//data[@id='rightMenu']//right-menu-item">

                <tr>
                    <td colspan="2" class="attractionsTopItemP1">
                        <a class="right-menu-link">
                            <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                        select="type"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>

                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="images/kolizey.jpg" alt="no image"/>
                    </td>
                    <td class="attractionsTopItemP2">
                        <xsl:value-of select="id"/>
                        sl djfslkjdlsj dlkjsldkfjs ldkjsl djflsdj
                    </td>
                </tr>
            </xsl:for-each>
        </table>

    </xsl:template>

</xsl:stylesheet>
