--Возраст студента не может быть меньше 16 лет.
alter table student
    add constraint check_student_age_more_16 check (age >= 16);

--Имена студентов должны быть уникальными и не равны нулю.
alter table student
    add constraint unique_student_name unique (name);
alter table student
    alter column name set not null;

--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty
    add constraint unique_name_color unique (name, color);

--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table student
    alter column age set default 20;

