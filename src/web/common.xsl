<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">

        <html>
            <head>
                <title>
                    <xsl:text>Агрегатор</xsl:text>
                </title>
                <link type="text/css" rel="stylesheet" href="attraction.css"/>
            </head>
            <body>
                <table width="100%" valign="top">
                    <tr height="65px">
                        <td colspan="2" width="100%" background="images/bg-header.gif" align="center">
                            <i>
                                <b>
                                    <font size="5" color="#FFFFFF" face="Verdana">Маленькая Мир
                                    </font>
                                </b>
                            </i>
                            <br/>
                            <br/>
                            <i>
                                <b>
                                    <font color="#FFFFFF" size="1" face="Verdana">Путешествуйте с
                                        нами!
                                    </font>
                                </b>
                            </i>
                        </td>
                    </tr>
                    <tr align="center">
                        <td class="l-head__c" colspan="2">
                            <table class="b-head-tabs g-js">
                                <tr>
                                    <td class="b-head-tabs__item b-head-tabs__tab">
                                        <a class="b-head-tabs__link" href="attractions.xml">Главная
                                        </a>
                                    </td>
                                    <td class="b-head-tabs__item b-head-tabs__tab">
                                        <a class="b-head-tabs__link" href="attractions.xml">Будушая корзина
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr align="left">
                        <td valign="top" width="200px" class="category_list" align="center">
                            <table>
                                <tr>
                                    <b>
                                        <font size="4" color="#A3CE5E" face="Verdana">
                                            Общий список:
                                        </font>
                                    </b>
                                    <br/>

                                </tr>
                                <tr>
                                    <xsl:call-template name="menu"/>
                                </tr>
                            </table>

                        </td>
                        <td width="600px" valign="top" class="find_button">
                            <table width="600px" valign="top">
                                <tr>
                                    <xsl:call-template name="find"/>
                                </tr>
                                <tr align="left" width="70%">
                                    <td>
                                        <input type="checkbox" value="0" name="cityCheckbox"/>
                                        Город
                                    </td>
                                    <td>
                                        <label>
                                            <input type="checkbox" name="sdfsdfs"/>
                                            Река
                                        </label>
                                    </td>
                                    <td>
                                        <label>
                                            <input type="checkbox" name="sdfsdfs"/>
                                            Достопримечательность
                                        </label>
                                    </td>
                                    <td>
                                        <label>
                                            <input type="checkbox" name="sdfsdfs"/>
                                            Страна
                                        </label>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <xsl:call-template name="main"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>

        </html>


    </xsl:template>

    <xsl:template name="find">
        <form action="attractions.xml" method="post">
            <td class="b-search__input" valign="top" colspan="4">
                <div class="b-input">
                    <input size="80" tabindex="1" class="b-input__text g-js"
                           name="findTextBox"
                           value=""
                           maxlength="100"/>
                </div>
            </td>
            <td class="b-search__button">
                <input size="10" class="b-search__submit" type="submit"
                       tabindex="2"
                       value="Найти"/>
            </td>
        </form>
    </xsl:template>

    <xsl:template name="menu">
        <!--<form action="listofattractions.xml" method="post">

        </form>-->
        <b>
            <a href="" target="_blank">
                <font size="3" face="Verdana" color="#999999">Колизей</font>
            </a>
        </b>
        <br/>
        <b>
            <a href="" target="_blank">
                <font size="3" face="Verdana" color="#999999">Колизей</font>
            </a>
        </b>
        <br/>
        <b>
            <a href="" target="_blank">
                <font size="3" face="Verdana" color="#999999">Колизей</font>
            </a>
        </b>
        <br/>
        <b>
            <a href="" target="_blank">
                <font size="3" face="Verdana" color="#999999">Колизей</font>
            </a>
        </b>
        <br/>
        <b>
            <a href="" target="_blank">
                <font size="3" face="Verdana" color="#999999">Колизей</font>
            </a>
        </b>
        <br/>
        <b>
            <a href="" target="_blank">
                <font size="3" face="Verdana" color="#999999">Колизей</font>
            </a>
        </b>

    </xsl:template>

</xsl:stylesheet>