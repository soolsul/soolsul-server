SET
foreign_key_checks = 0;

insert into custom_user (id, email, password, deleted)
    value ('user_id_1', 'shine@google.com', '12341234', false);

insert into user_info (id, name, nickname, phone, profile_image, user_id, deleted)
    value ('user_info_id_1', 'test', 'tester', '010-1234-5678', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQx1-fDj8PMv7zgNU_1UB3foB0RIBEVExpJNQ&usqp=CAU', 'user_id_1', false);

insert into bar (id, created_at, update_at, bar_category_id, description, latitude, longitude, name, region_id)
values ('bar_uuid_1', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565494, 126.992493,
        '소규모 와인바', 'region_id'),
       ('bar_uuid_2', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565214, 126.994546,
        '머리조심', '을지로 3가'),
       ('bar_uuid_3', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565336, 126.993517,
        '김림', '을지로 3가'),
       ('bar_uuid_4', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565923, 126.991082,
        '디엣지', '을지로 3가'),
       ('bar_uuid_5', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565172, 126.992418,
        '굴', '을지로 3가'),
       ('bar_uuid_6', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.566905, 126.994710,
        '알렉스룸', '을지로 3가'),
       ('bar_uuid_7', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.564667, 126.994026,
        '빈달루', '을지로 3가'),
       ('bar_uuid_8', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565310, 126.991741,
        '믹스드라인', '을지로 3가'),
       ('bar_uuid_9', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565852, 126.989936,
        '역전할머니', '을지로 3가'),
       ('bar_uuid_10', '2022-11-18 15:30:25', '2022-11-18 15:30:25', 'bar_category_id', 'good', 37.565426, 126.991087,
        '밑', '을지로 3가');

insert into post (id, bar_id, contents, owner_id, score)
values ('uuid_0', 'bar_uuid_1', 'contents', 'user_id_1', 4.91),
       ('uuid_1', 'bar_uuid_1', 'contents', 'user_id_1', 3.95),
       ('uuid_2', 'bar_uuid_2', 'contents', 'user_id_1', 3.91),
       ('uuid_3', 'bar_uuid_2', 'contents', 'user_id_1', 2.37),
       ('uuid_4', 'bar_uuid_3', 'contents', 'user_id_1', 1.57),
       ('uuid_5', 'bar_uuid_3', 'contents', 'user_id_1', 2.88),
       ('uuid_6', 'bar_uuid_4', 'contents', 'user_id_1', 3.88),
       ('uuid_7', 'bar_uuid_4', 'contents', 'user_id_1', 2.67),
       ('uuid_8', 'bar_uuid_5', 'contents', 'user_id_1', 3.06),
       ('uuid_9', 'bar_uuid_5', 'contents', 'user_id_1', 3.98),
       ('uuid_10', 'bar_uuid_6', 'contents', 'user_id_1', 1.26),
       ('uuid_11', 'bar_uuid_6', 'contents', 'user_id_1', 3.23),
       ('uuid_12', 'bar_uuid_7', 'contents', 'user_id_1', 4.03),
       ('uuid_13', 'bar_uuid_8', 'contents', 'user_id_1', 1.03),
       ('uuid_14', 'bar_uuid_9', 'contents', 'user_id_1', 4.94),
       ('uuid_15', 'bar_uuid_10', 'contents', 'user_id_1', 2.55);

insert into post_photo (id, bar_id, deleted, extension, original_file_name, uuid_file_url, post_id)
values ('photo_1', 'bar_uuid_1', 0, '.jpg', 'name',
        'https://img.lovepik.com/photo/20211120/medium/lovepik-red-wine-picture_500465374.jpg', 'uuid_0'),
       ('photo_2', 'bar_uuid_2', 0, '.jpg', 'name',
        'https://img.lovepik.com/photo/20211120/medium/lovepik-red-wine-picture_500465374.jpg', 'uuid_1'),
       ('photo_3', 'bar_uuid_3', 0, '.jpg', 'name',
        'https://img.lovepik.com/photo/20211120/medium/lovepik-red-wine-picture_500508753.jpg', 'uuid_0'),
       ('photo_4', 'bar_uuid_4', 0, '.jpg', 'name',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLbARAticMygATi7RIg8JhNTRNNbdfOr1hD8XTQLPJwWi8IJsA8T_oLxTs_MZ9Q8PW6iY&usqp=CAU',
        'uuid_1'),
       ('photo_5', 'bar_uuid_5', 0, '.jpg', 'name',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRo-_REiJwuL3TH45j4h9uvDVv3vGf4JZ4WJWhBjdV-fUYNDFjN47MqNvsK5xVeIZdu8lw&usqp=CAU',
        'uuid_2');

SET
foreign_key_checks = 1;
