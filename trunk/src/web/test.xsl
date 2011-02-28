<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="html" indent="yes" encoding="utf-8"/>

    <xsl:template match="/">


                <html>

                    <head>
                        <title>Часы и дата на JavaScript</title>
                    </head>
                    <body>
                        <h1>Часы и дата на JavaScript</h1>
                        <div>
                            <span id="hours"></span>
                        </div>

                        <script type="text/javascript">

                            obj_hours=document.getElementById("hours");

                            name_month=new Array ("января","февраля","марта", "апреля","мая",
                            "июня","июля","августа","сентября", "октября","ноября","декабря");
                            name_day=new Array ("воскресенье","понедельник", "вторник","среда","четверг",
                            "пятница","суббота");

                            function wr_hours()
                            {
                            time=new Date();

                            time_sec=time.getSeconds();
                            time_min=time.getMinutes();
                            time_hours=time.getHours();
                            time_wr=((time_hours &lt; 10)?"0":"")+time_hours;
                            time_wr+=":";
                            time_wr+=((time_min &lt; 10)?"0":"")+time_min;
                            time_wr+=":";
                            time_wr+=((time_sec &lt; 10)?"0":"")+time_sec;

                            time_wr=" сегодня "+name_day[time.getDay()]+", "+time.getDate()+"
                            "+name_month[time.getMonth()]+" "+time.getFullYear()+" г. время "+time_wr;

                            obj_hours.innerHTML=time_wr;
                            }

                            wr_hours();
                            setInterval("wr_hours();",1000);

                        </script>

                    </body>
                </html>

    </xsl:template>

    <xsl:template match="node()">
        <xsl:copy>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
