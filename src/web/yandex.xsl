<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">

        <html id="nojs">
            <head>
                <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
                <link rel="shortcut icon" href="http://yandex.st/lego/_/pDu9OWAQKB0s2J9IojKpiS_Eho.ico"/>

                <link type="text/css" rel="stylesheet" href="attraction.css"/>
                <title>
                    <xsl:text>Агрегатор</xsl:text>
                </title>


            </head>
            <body class="b-page">
                <table class="l-head-hack" width="100%">
                    <tr>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr background="images/bg-header.gif">

                                <td width="40%" background="images/bg-header.gif" valign="top">
                                    <img src="images/bg-header.gif" width="19" height="69" colspan="2"/>
                                </td>
                                <td width="58%" background="images/bg-header.gif" valign="top">
                                    <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse"
                                           bordercolor="#111111" width="100%" id="AutoNumber4">
                                        <tr>
                                            <td width="100%">
                                                <i>
                                                    <b>
                                                        <font size="5" color="#FFFFFF" face="Verdana">Маленькая Италия
                                                        </font>
                                                    </b>
                                                </i>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="100%">
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
                        </table>
                    </tr>
                    <tr>
                        <td>

                        </td>

                        <td class="l-head-hack__l" width="100%">
                            <table class="l-head" align="left">
                                <tr>
                                    <td class="l-head__c" valign="center">
                                        <!--Верхнее меню-->
                                        <table class="b-head-tabs g-js">
                                            <tr>
                                                <td class="b-head-tabs__item b-head-tabs__tab">
                                                    <strong>Поиск</strong>
                                                </td>
                                                <td class="b-head-tabs__item b-head-tabs__tab">
                                                    <a class="b-head-tabs__link" href="attractioms.xml">Будушая корзина
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                        <table>
                                            <tr>
                                                <td width="30%" valign="top">
                                                    <table>
                                                        <td class="l-head__l">
                                                            <b>
                                                                <font size="4" color="#A3CE5E" face="Verdana">
                                                                    Общий список:
                                                                </font>
                                                            </b>
                                                            <br/>
                                                            <xsl:call-template name="menu"/>
                                                        </td>
                                                    </table>
                                                </td>
                                                <td width="15%">

                                                </td>
                                                <td>
                                                    <table width="100%" valign="top">
                                                        <tr>
                                                            <td valign="top">
                                                                        <xsl:call-template name="find"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="60%">
                                                                <xsl:call-template name="main"/>
                                                            </td>
                                                        </tr>
                                                    </table>

                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>

                    </tr>
                </table>

            </body>
        </html>
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

    <xsl:template name="find">
        <form action="attractions.xml" method="post">
            <table class="b-search">
                <tr>
                    <td class="b-search__input">
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
                </tr>
                <tr>
                    <td class="b-search__under">
                        <span class="b-search__precise">
                            <label class="b-search__precise-label">
                                <input
                                        class="b-search__precise-check" name="city"
                                        type="checkbox" value="-2" tabindex="1"/>
                                Города
                            </label>
                        </span>
                        <span class="b-search__precise">
                            <label class="b-search__precise-label">
                                <input
                                        class="b-search__precise-check" name="city"
                                        type="checkbox" value="-2" tabindex="1"/>
                                Реки
                            </label>
                        </span>
                        <span class="b-search__precise">
                            <label class="b-search__precise-label">
                                <input
                                        class="b-search__precise-check" name="city"
                                        type="checkbox" value="-2" tabindex="1"/>
                                Памятники
                            </label>
                        </span>
                    </td>
                    <td></td>
                </tr>
            </table>
        </form>
    </xsl:template>

</xsl:stylesheet>