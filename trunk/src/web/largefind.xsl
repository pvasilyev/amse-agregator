<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <td width="100%" valign="top">
            <form action="attractions.xml" method="post">
               <h2>Расширенный поиск</h2>

                <table width="100%" valign="top">
                    <tr>
                        <td colspan="2">
                            Введите название объекта для поиска:
                        </td>
                        <td valign="top" colspan="3">
                                <div>
                                    <input name="findTextBox"
                                           value=""
                                           maxlength="40"/>
                                </div>
                            </td>

                    </tr>
                    <tr align="left" width="70%" class="checkbox">
                        <td>
                            <input type="checkbox" name="countryCheckbox"/>
                            Страна
                        </td>
                        <td>
                            <input type="checkbox" name="cityCheckbox"/>
                            Город
                        </td>
                        <td>
                            <input type="checkbox" name="museumCheckbox"/>
                            Музей
                        </td>
                        <td>
                            <input type="checkbox" name="archAttractionCheckbox"/>
                            Архитектура
                        </td>
                        <td>
                            <input type="checkbox" name="naturalAttractionCheckbox"/>
                            Природа
                        </td>

                    </tr>
                    <tr class="checkbox">
                        <td>
                            <input type="checkbox" name="cafeCheckbox"/>
                            Кафе
                        </td>

                        <td colspan="2">
                            <input type="checkbox" name="entertainmentCheckbox"/>
                            Развлечения
                        </td>
                        <td>
                            <input type="checkbox" name="shoppingCheckbox"/>
                            Шопинг
                        </td>
                        <td>
                            <input type="checkbox" name="hotelCheckbox"/>
                            Отель
                        </td>
                    </tr>
                    <tr>
                        <td class="b-search__button">
                                <input size="10" class="b-search__submit" type="submit"
                                       tabindex="2"
                                       value="Найти"/>
                            </td>
                    </tr>

                </table>
            </form>

        </td>
    </xsl:template>

</xsl:stylesheet>
