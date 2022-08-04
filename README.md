# GameWord
Pet Project Game word.

Мое REST API выполняет функции игры в слова.
Возможности:
1)Создавать игрока 
2)Создавать игру 
3)Добавлять игроков в игру
Правила Игры:
1)Участник каждый раз подбирает понятие на последнюю букву названного перед ним слова. Однако, следуя правилам русского языка, в игре предусмотрены исключения. Если слово заканчивается на Ь, Ъ, Ы, Й, то сочиняют на предпоследнюю букву.
2)Наименования не должны повторяться – каждый раз придумывается новое.
Методы:
1)http://localhost:8080/player/create-player/{name_player} -создает игрока
2)http://localhost:8080/player/list-список игроков
3)http://localhost:8080/games/new/{name_player} - создание игры с игроком 
4)http://localhost:8080/games/plus/{name_player}/{id_play} - добавление игрока в игру
5)http://localhost:8080/games/play/{id_play}/{word} - начало игры - ЗАМЕСТО {word} вписывается слово - если оно подходит , то программа выдет последнию букву на которое надо написать следующее слово , игра заканчивается если написать слово на не подходящюю букву.
