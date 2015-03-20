Обозначу тут круг задач и предложений, которые мы будем решать по ходу этого семестра. Предлагаю обсуждать что-то, если не понравится или есть комментарии.

## Usability (front-end) && Public UI ##

  1. Сделать удобным просмотр конкретных объектов:
    1. сделать унификацию для GUI: чтобы одинаковые по типу объекты отображались одинаково, чтобы на соответствующих страничках совпадали шрифты, типы ссылок и т.д.; [Наташа AC, issue 4,5](http://code.google.com/p/amse-agregator/issues/detail?id=4)
    1. Описания лучше всего выводить по одному в виде краткого обзора на пару абзацев + ссылка на источник (на сайт аллтревел.мазафака-урл.ком). Володя сказал, что когда это дело пойдет в продакшн, не надо делать копипасты чужих текстов, это нелегально [Наташа, issue 12](http://code.google.com/p/amse-agregator/issues/detail?id=12);
    1. если есть картинка, хотелось бы знать, что на ней отображено и где это можно найти (в идеале -- ссылка на этот объект, как на википедии); [Наташа, issue 15](http://code.google.com/p/amse-agregator/issues/detail?id=15)
    1. если отображаем "что-то" (**_достопримечательность_**, **_город_**, **_страна_**), то очень хочется чтобы картинки про это место шли "параллельно" с текстом, а не так, чтобы сначала текст, а потом картинки, или наборот. В панелях сбоку отображать **_ближайшее_** или **_популярное_** в этом же городе, стране или неподалеку соответственно. [Наташа AC, issue 4,5](http://code.google.com/p/amse-agregator/issues/detail?id=4) + [Паша В. AC, issue 11](http://code.google.com/p/amse-agregator/issues/detail?id=11)
    1. у нас есть страница с [континентом](http://home.korgov.ru/attractiondescription.xml?id=4d077df9a598f1b3b55478fa&type=Continent), на ней есть список стран. Надо сделать этот список более презентабельным, например, как [тут](http://home.korgov.ru/menu.xml). Аналогично со списком городов у страны и списком достопримечательностей у города. [Наташа, issue 19](http://code.google.com/p/amse-agregator/issues/detail?id=19)
  1. Очень нужна карта с отображением объектов.
    1. научиться делать масштабирование страны, города в зависимости от тех достопримечательностей, которые у нас есть; [Ира, issue 20](http://code.google.com/p/amse-agregator/issues/detail?id=20)
    1. научиться выводить краткую информацию на tips'ах. Взять за основу модель вывода, которая есть на гугл-картах. [Ира, issue 21](http://code.google.com/p/amse-agregator/issues/detail?id=21)
  1. В топ-блоках справа-слева надо подписывать категорию объекта, а также сделать ранжирование. Допустим, user видит город и говорит: отранжируй мне этот список по дворцам и соборам. Если user видит достопримечательность, то может сказать: отранжируй по ближайшим объектам. Этого сейчас очень не хватает. [issue 14](http://code.google.com/p/amse-agregator/issues/detail?id=14)
  1. Сделать поддежрку доступа пользователей со всеми вытекающими последствиями [Кирилл + Ваня, issue 8](http://code.google.com/p/amse-agregator/issues/detail?id=8):
    1. личная информация пользователя (нужна отдельная база);
    1. оставлять комментарии (~ twitter, facebook ...);
    1. уметь делать ранжирование информации на основе **_отзывов_** и **_предпочтений_** пользователей.
  1. Если есть информация, то желательно выдавать ссылку на эту статью.
  1. Хочется уметь выдавать ссылку на результаты поисковых запросов.
  1. Сделать удобный расширенный поиск. Основная идея в том, что в поисковой строке писать локацию (например, "италия"). А потом категорию объектов задавать с помощью фильтров. Например, такая user story [Тимофей, issue 7](http://code.google.com/p/amse-agregator/issues/detail?id=7).

## Back-end ##
  1. Правильно обрезать тэги в описаниях, чтобы слова не слипались; (_Ира вроде что-то уже выложила, надо посмотреть_) [Ира, issue 6](http://code.google.com/p/amse-agregator/issues/detail?id=6)
  1. Добиться максимальной унификации в названиях объектов; [Наташа, issue 6](http://code.google.com/p/amse-agregator/issues/detail?id=6)
  1. Намайнить координаты и положить их в базу; [Паша В. + Кирилл AC, issue 10](http://code.google.com/p/amse-agregator/issues/detail?id=10)
  1. Кластеризация объектов по координатам; [Паша Т., issue 9](http://code.google.com/p/amse-agregator/issues/detail?id=9)
  1. Научиться отображать объекты на карте;
  1. Научитсья ранжировать информацию по популярности;
  1. Научиться обображать короткое описание объекта (abstract). Тут два пути:
    1. Строить его на основе имеющегося (10% за этот вариант)
    1. Замайнить сайт, на котором есть такое краткое описание для каждого объекта (_**90% что так и будет**_). Например, на сайте ru.worldpoi.info есть такая информация. Даже у меня в конфиге это описание скрыто под переменной "descrA".
  1. Добавить базу для пользователей и уметь хранить информацию о них. [Кирилл + Ваня, issue 8](http://code.google.com/p/amse-agregator/issues/detail?id=8)
  1. Попробовать выцепить информацию о времени работы разных заведений (сейчас оно намайнено с сайта ляляля.ua). Перевести это время в GMT, и уметь грамотно его выводить. [Паша Т., issue 9](http://code.google.com/p/amse-agregator/issues/detail?id=9)
  1. Научиться извлекать дополнительные параметры для объектов: например, если это "Собор Парижской Богоматери", то хочется знать, что это именно собор, более того, он католический. Вобщем, чтобы графа "тэги" у объектов не пустели. Все это нужно для расширенного поиска.
  1. Уметь из набора текстов выбирать самый лучший: по набору каких-то слов, по "литературности" изложения и т.д. Задача творческая, тут подход индивидуальный [Паша Т., issue 13](http://code.google.com/p/amse-agregator/issues/detail?id=13)
  1. Все exception'ы, которые вылетают, надо обрабатывать по-умному и выводить валидный контент. Т.е. выдавать код ошибки, часть stackTrace, надо подумать, это для всех задача, потому что exception'ы могут вываливаться у всех. [ВСЕ, issue 16](http://code.google.com/p/amse-agregator/issues/detail?id=16)
  1. Научитсья работать с рубриками: знать, что данный объект это "_**то-то**_" или "_**то-то**_". Т.е. возможно придется написать пару-тройку методов для базы: типичных запросов к базе. Типа: ```
getAllObjectsByCategory(city_id, Database.CATEGORY);``` [issue 17](http://code.google.com/p/amse-agregator/issues/detail?id=17)
  1. Уметь находить ближайшие объекты по категории относительно данного. Типа: ```
getNearestByCategory(object_id, Database.CATEGORY)``` [issue 18](http://code.google.com/p/amse-agregator/issues/detail?id=18)


---


Добавляем сюда комментарии, если таковые есть