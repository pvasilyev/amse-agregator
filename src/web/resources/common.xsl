<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>

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
                    <tr>
                        <td colspan="2" width="100%" background="images/bg-header.gif">
                            <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse"
                                   bordercolor="#111111" width="100%" id="AutoNumber4">
                                <tr>
                                    <td align="center">
                                        <i>
                                            <b>
                                                <font size="5" color="#FFFFFF" face="Verdana">Маленькая Италия
                                                </font>
                                            </b>
                                        </i>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <p align="left"/>
                                        <i>
                                            <b>
                                                <font color="#FFFFFF" size="1" face="Verdana">Путешествуйте с
                                                    нами!
                                                </font>
                                            </b>
                                        </i>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="l-head__c">
                            <table class="b-head-tabs g-js">
                                <tr>
                                    <td class="b-head-tabs__item b-head-tabs__tab">
                                        <a class="b-head-tabs__link" href="attractioms.xml">Главная
                                        </a>
                                    </td>
                                    <td class="b-head-tabs__item b-head-tabs__tab">
                                        <a class="b-head-tabs__link" href="attractioms.xml">Будушая корзина
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr align="left" colspan="2">
                        <td width="100px" class="l-head__l" align="right">
                            <b>
                                <font size="4" color="#A3CE5E" face="Verdana">
                                    Общий список:
                                </font>
                            </b>
                            <br/>
                            <xsl:call-template name="menu"/>
                        </td>
                        <td width="500px">
                            <table width="500px" valign="top">
                                <tr>
                                    <xsl:call-template name="find"/>
                                </tr>
                                <tr>
                                    <td>
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
            <td class="b-search__input" valign="top">
                <div class="b-input">
                    <input tabindex="1" class="b-input__text g-js"
                           name="findTextBox"
                           value=""
                           maxlength="300"/>
                </div>
            </td>
            <td class="b-search__button">
                <input class="b-search__submit" type="submit"
                       tabindex="2" value="Найти"/>
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