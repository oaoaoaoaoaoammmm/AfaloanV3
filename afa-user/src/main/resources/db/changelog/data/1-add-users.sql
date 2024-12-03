insert into application.users (username,
                               password,
                               confirmed,
                               confirmed_username,
                               blocked,
                               role)
VALUES ('daniil.afanasev.2018@mail.ru', '$2a$10$J4KjRrjR1SzK0aSB9ugSFOFuyUNZzWv6cIIjeSW8CT1TmlV15blZO', true, true, false, 'SUPERVISOR'),
       ('lucky898322@gmail.com', '$2a$10$92WKWFbkcadKpFHsLKEscexNZd17x.iLzUUtuBaPK9uoYUs/AllfK', true, true, false, 'WORKER'),
       ('79852601706@yandex.ru', '$2a$10$tEUzUTVex4BF6ZfB99GI4.TWankH01Ziovn3AcKQ7K67bqhh2AYfy', true, true, false, 'CUSTOMER');
