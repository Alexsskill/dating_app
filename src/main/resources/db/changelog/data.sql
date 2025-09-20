INSERT INTO users (first_name, last_name, bio, city, age, phone, email, password, gender, role, looking_for, added_at, updated_at)
VALUES
-- Мужчины ищут серьезные отношения
('Иван', 'Петров', 'Люблю спорт и путешествия', 'Москва', 28, '+79161234501', 'ivan.petrov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),
('Алексей', 'Сидоров', 'Программист, увлекаюсь фотографией', 'Санкт-Петербург', 32, '+79161234502', 'aleksey.sidorov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),
('Дмитрий', 'Иванов', 'Люблю активный отдых и книги', 'Новосибирск', 25, '+79161234503', 'dmitry.ivanov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),

-- Женщины ищут серьезные отношения
('Екатерина', 'Смирнова', 'Учусь на врача, люблю музыку', 'Москва', 26, '+79161234504', 'ekaterina.smirnova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),
('Анна', 'Кузнецова', 'Дизайнер, увлекаюсь живописью', 'Санкт-Петербург', 29, '+79161234505', 'anna.kuznetsova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),
('Мария', 'Попова', 'Люблю готовить и путешествовать', 'Екатеринбург', 31, '+79161234506', 'maria.popova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),

-- Мужчины ищут дружбу
('Сергей', 'Васильев', 'Спортсмен, ищу единомышленников', 'Казань', 24, '+79161234507', 'sergey.vasilev@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FRIENDSHIP', NOW(), NOW()),
('Андрей', 'Павлов', 'Студент, люблю видеоигры', 'Нижний Новгород', 22, '+79161234508', 'andrey.pavlov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FRIENDSHIP', NOW(), NOW()),
('Максим', 'Семенов', 'Музыкант, ищу друзей для джема', 'Ростов-на-Дону', 27, '+79161234509', 'maxim.semenov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FRIENDSHIP', NOW(), NOW()),

-- Женщины ищут дружбу
('Ольга', 'Голубева', 'Люблю кино и театр', 'Самара', 30, '+79161234510', 'olga.golubeva@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FRIENDSHIP', NOW(), NOW()),
('Наталья', 'Виноградова', 'Фитнес-тренер, ищу подруг', 'Краснодар', 28, '+79161234511', 'natalya.vinogradova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FRIENDSHIP', NOW(), NOW()),
('Ирина', 'Белова', 'Люблю читать и обсуждать книги', 'Воронеж', 33, '+79161234512', 'irina.belova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FRIENDSHIP', NOW(), NOW()),

-- Мужчины ищут семью
('Артем', 'Крылов', 'Хочу создать крепкую семью', 'Уфа', 35, '+79161234513', 'artem.krylov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FAMILY', NOW(), NOW()),
('Владимир', 'Лебедев', 'Готов к серьезным отношениям', 'Пермь', 37, '+79161234514', 'vladimir.lebedev@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FAMILY', NOW(), NOW()),
('Павел', 'Егоров', 'Ищу спутницу жизни', 'Волгоград', 40, '+79161234515', 'pavel.egorov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FAMILY', NOW(), NOW()),

-- Женщины ищут семью
('Светлана', 'Орлова', 'Хочу создать уютный дом', 'Красноярск', 34, '+79161234516', 'svetlana.orlova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FAMILY', NOW(), NOW()),
('Татьяна', 'Андреева', 'Мечтаю о большой семье', 'Саратов', 29, '+79161234517', 'tatiana.andreeva@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FAMILY', NOW(), NOW()),
('Юлия', 'Макарова', 'Готова к материнству', 'Тюмень', 31, '+79161234518', 'yuliya.makarova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FAMILY', NOW(), NOW()),

-- Мужчины ищут общение
('Роман', 'Никитин', 'Люблю интересные беседы', 'Иркутск', 26, '+79161234519', 'roman.nikitin@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'TALKING', NOW(), NOW()),
('Александр', 'Захаров', 'Философ, ищу собеседников', 'Хабаровск', 38, '+79161234520', 'alexander.zakharov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'TALKING', NOW(), NOW()),
('Георгий', 'Соловьев', 'Люблю обсуждать науку', 'Владивосток', 42, '+79161234521', 'georgy.soloviev@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'TALKING', NOW(), NOW()),

-- Женщины ищут общение
('Елена', 'Воробьева', 'Люблю культурные дискуссии', 'Ярославль', 27, '+79161234522', 'elena.vorobeva@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'TALKING', NOW(), NOW()),
('Алиса', 'Федорова', 'Студентка, ищу друзей для общения', 'Томск', 21, '+79161234523', 'alisa.fedorova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'TALKING', NOW(), NOW()),
('Ксения', 'Морозова', 'Психолог, люблю помогать людям', 'Омск', 36, '+79161234524', 'ksenia.morozova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'TALKING', NOW(), NOW()),

-- Разные гендеры
('Михаил', 'Козлов', 'OTHER bio description', 'Калининград', 30, '+79161234525', 'mikhail.kozlov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'OTHER', 'USER', 'FRIENDSHIP', NOW(), NOW()),
('Александра', 'Новикова', 'OTHER bio description', 'Сочи', 28, '+79161234526', 'aleksandra.novikova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'OTHER', 'USER', 'TALKING', NOW(), NOW()),
('Денис', 'Михеев', 'OTHER bio description', 'Тверь', 32, '+79161234527', 'denis.mikheev@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'OTHER', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),

-- Дополнительные пользователи
('Виктор', 'Тарасов', 'Предприниматель, люблю спорт', 'Белгород', 39, '+79161234528', 'viktor.tarasov@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'SERIOUS_RELATIONSHIP', NOW(), NOW()),
('Людмила', 'Абрамова', 'Учитель, люблю детей', 'Липецк', 45, '+79161234529', 'lyudmila.abramova@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'FEMALE', 'USER', 'FAMILY', NOW(), NOW()),
('Григорий', 'Медведев', 'Инженер, увлекаюсь техникой', 'Ставрополь', 33, '+79161234530', 'grigory.medvedev@mail.ru', '$2a$10$rOzJq5kY9U6ZQ1Q1Q1Q1QO', 'MALE', 'USER', 'FRIENDSHIP', NOW(), NOW());