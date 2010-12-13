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
                <script type="text/javascript">
                    function searchClicked(){
                        document.searchForm.submit();
                    }
                </script>
            </head>
            <body>
                <table class="extTable">
                    <tr>
                        <td class="extTableTD">
                            <table class="header">
                                <tr>
                                    <td id="logo_p1" onclick="location.href='index.xml';">
                                    </td>
                                    <td colspan="2">
                                        <table id="searchContainer">
                                            <xsl:call-template name="find"/>
                                        </table>

                                    </td>
                                </tr>
                                <tr>
                                    <td id="logo_p2"  onclick="location.href='index.xml';">
                                    </td>
                                    <td id="logo_p3"  onclick="location.href='index.xml';">
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
                                        <xsl:call-template name="leftmenu"/>

                                    </td>
                                    <td id="centerColomn">
                                        <!-- Этот блок потмо разумно вынести в отдельный шаблон. !!!Города с картинками!!! -->
                                        <xsl:call-template name="main"/> 
                                        
                                    </td>
                                    <td id="rightColomn">
                                         <xsl:call-template name="rightmenu"/>
                                    </td>
                                </tr>
                            </table>

                            <div class="klcopyright">
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
                    <form id="searchForm" name="searchForm" method="POST" action="search.xml">
                        <input type="text" id="search" name="findTextBox"/>
                    </form>
                </td>
                <td class="searchButtonTd">
                    <a class="buttonLink" onclick="searchClicked()">Найти</a>
                </td>
            </tr>
        <!--</form>-->
    </xsl:template>
    
    
       <xsl:template name="countryTopBlock">
        <table class="simpleTop">
            <tr>
                <td class="simpleTopHeader">
                    Популярные страны:
                </td>
            </tr>
            <xsl:for-each select="page/data[@id = 'countryTopBlock']/collection/record">

                <tr>
                    <td class="simpleTopItemTitle">
                        <a>
                            <xsl:attribute name="href">attractiondescription.xml?type=Country&amp;id=<xsl:value-of select="cells/cell[2]/value"/></xsl:attribute>
                            <xsl:copy-of select="cells/cell[1]/value" />
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="simpleTopItemContent">
                        <xsl:for-each select="cells/cell[3]/value/record">
                            <a>
                                <xsl:attribute name="href">attractiondescription.xml?type=City&amp;id=<xsl:value-of select="cells/cell[2]/value"/></xsl:attribute>
                                <xsl:copy-of select="cells/cell[1]/value" />
                            </a><br/>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template name="continentSelectBlock">
        <div class="simpleBlock">
            <img usemap="#continentsMap" src="images/continents.jpg" alt="" border="0" />
            <map name="continentsMap" >
                <xsl:for-each select="page/data[@id = 'continentSelectBlock']/collection/record">
                    <area shape="poly">
                        <xsl:attribute name="coords">
                            <xsl:if test = "cells/cell[1]/value = 'Австралия и Океания'">
                                148,108,177,110,189,91,171,79,147,88
                            </xsl:if>
                            <xsl:if test = "cells/cell[1]/value = 'Северная Америка'">
                                15,28,86,21,41,67
                            </xsl:if>
                            <xsl:if test = "cells/cell[1]/value = 'Южная Америка'">
                                47,67,73,81,57,113,43,80
                            </xsl:if>
                            <xsl:if test = "cells/cell[1]/value = 'Европа'">
                                87,58,106,21,139,27,129,51,111,57
                            </xsl:if>
                            <xsl:if test = "cells/cell[1]/value = 'Азия'">
                                138,36,181,42,169,72,132,70,127,53,138,52
                            </xsl:if>
                            <xsl:if test = "cells/cell[1]/value = 'Африка'">
                                102,56,121,58,134,72,115,96,95,70
                            </xsl:if>
                        </xsl:attribute>
                        <xsl:attribute name="href">continent.xml?id=<xsl:value-of select="cells/cell[2]/value"/></xsl:attribute>
                        <xsl:attribute name="title">
                            <xsl:value-of select="cells/cell[1]/value"/>
                        </xsl:attribute>
                    </area>
                </xsl:for-each>
                
            </map>
        </div>
    </xsl:template>
    
    <xsl:template name="attractionTopBlock">
        <table class="attractionsTop">
            <tr>
                <td class="attractionsTopHeader">
                    Популярные достопримечательности:
                </td>
            </tr>
            <xsl:for-each select="page/data[@id = 'attractionTopBlock']/collection/record">
                <tr>
                    <td>
                    <xsl:if test="position() mod 2 = 0">
                        <xsl:attribute name="class">
                            attractionsTopItemFirst
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="position() mod 2 != 0">
                        <xsl:attribute name="class">
                            attractionsTopItemSecond
                        </xsl:attribute>
                    </xsl:if>
                        <xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes" />
                        <br/>
                        <div class="attractionsTopItemP1">
                            <a>
                                <xsl:attribute name="href">attractiondescription.xml?type=Attraction&amp;id=<xsl:value-of select="cells/cell[2]/value"/></xsl:attribute>
                                <img width="66" height="55">
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="cells/cell[4]/value"/>
                                    </xsl:attribute>
                                </img>
                            </a>
                        </div>
                        <div class="attractionsTopItemP2">
                            <xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes" />
                        </div>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

    <!--<xsl:template name="attractionTopBlock2">-->
        <!--<xsl:apply-templates select="page/data[@id = 'attractionTopBlock']/collection/record"/>-->
    <!--</xsl:template>-->

    <xsl:template name="attractionTopBlock2" match="page/data[@id = 'attractionTopBlock']/collection/record">

    </xsl:template>



<!--
    <xsl:template name="leftmenulist">
        <table>
            <xsl:for-each select="//data[@id='leftMenu']//left-menu-item">
                <xsl:sort order="ascending" select="name"/>

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


                <br/>
            </xsl:for-each>
        </table>

    </xsl:template>
-->
<!--
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
-->
</xsl:stylesheet>
