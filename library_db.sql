create database QuanLySinhVien;

use QuanLySinhVien;

CREATE TABLE Class(
	ClassID int not null auto_increment,
    ClassName varchar(60) not null,
    StartDate datetime not null,
    Status Bit,
    Primary key (ClassID)
);

create table Student(
	StudentID int not null auto_increment,
    StudentName varchar(30) not null,
    Address varchar(50),
    Phone varchar(20),
    Status Bit,
    ClassID int not null,
    Primary key (StudentID),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID)
);

create table Subject(
	SubID int not null auto_increment primary key,
	SubName varchar(30) not null,
    Credit tinyint not null Default 1 check (Credit >= 1),
    Status Bit Default 1 
);

create table Mark(
	MarkID int not null auto_increment primary key,
	SubID int not null,
    StudentID int not null,
    Mark float default 0 check (Mark between 0 and 100),
    ExamTimes Tinyint Default 1,
    unique (SubID, StudentID),
    foreign key (SubID) references Subject (SubID),
    foreign key (StudentID) references Student (StudentID)
);

insert into Class(ClassName, StartDate, Status) values ('A1', '2008-12-20', 1);
insert into Class(ClassName, StartDate, Status) values ('A2', '2008-12-22', 1);
insert into Class(ClassName, StartDate, Status) values ('B3', current_date, 0);

insert into Student(StudentName, Address, Phone, Status, ClassID) values ('Hung', 'Ha Noi', '0912113113', 1, 1);
insert into Student(StudentName, Address, Status, ClassID) values ('Hoa', 'Hai Phong', 1, 1);
insert into Student(StudentName, Address, Phone, Status, ClassID) values ('Manh', 'HCM', '0123123123', 0, 2);

insert into Subject(SubName, Credit, Status) values ('CF', 5, 1);
insert into Subject(SubName, Credit, Status) values ('C', 6, 1);
insert into Subject(SubName, Credit, Status) values ('HDJ', 5, 1);
insert into Subject(SubName, Credit, Status) values ('RDBMS', 10, 1);

insert into Mark(SubID, StudentID, Mark, ExamTimes) values (1, 1, 8, 1);
insert into Mark(SubID, StudentID, Mark, ExamTimes) values (1, 2, 10, 2);
insert into Mark(SubID, StudentID, Mark, ExamTimes) values (2, 1, 12, 1);

select * from Student;

select * from Student
where Status = true;

select * from Subject
where Credit <10;

select * from Student
where ClassID = 1;

select S.StudentId, S.StudentName, C.ClassName 
from Student S join Class C on S.ClassID = C.ClassID;

select S.StudentId, S.StudentName, C.ClassName 
from Student S join Class C on S.ClassID = C.ClassID
where C.ClassName = 'A1';

select S.StudentId, S.StudentName, Sub.SubName, M.Mark
from Student S join Mark M on S.StudentId = M.StudentID join Subject Sub on M.SubID = Sub.SubID
where Sub.SubName = 'CF';

select StudentID, StudentName from Student
where StudentName like 'H%';

select * from Class
where month(StartDate) = 12;

select * from Subject
where Credit >= 3 and Credit <=5;

update Student set ClassID = 2 where StudentID = 1;
select * from Student;

SELECT S.StudentName, Sub.SubName, M.Mark
FROM Student S 
JOIN Mark M ON S.StudentId = M.StudentID 
JOIN Subject Sub ON M.SubID = Sub.SubID
ORDER BY M.Mark DESC, S.StudentName ASC;

select Address, count(StudentId) as So_luong_sinh_vien
from Student
GROUP BY Address;

select S.StudentID, S.StudentName, avg(M.Mark)
from Student S
join Mark M on M.StudentID = S.StudentID
GROUP BY S.StudentId, S.StudentName;

select S.StudentID, S.StudentName, avg(M.Mark) as diem_trung_binh
from Student S
join Mark M on M.StudentID = S.StudentID
GROUP BY S.StudentId, S.StudentName
having diem_trung_binh > 15;

select S.StudentID, S.StudentName, avg(M.Mark) as diem_trung_binh
from Student S
join Mark M on M.StudentID = S.StudentID
GROUP BY S.StudentId, S.StudentName
having diem_trung_binh >= all (select avg(Mark) from Mark group by Mark.StudentID);

select * from Subject
where Credit = (select max(Credit) from Subject);

select Sub.*, M.Mark
from Subject Sub
join Mark M on Sub.SubID = M.SubID
where M.Mark = all (select max(Mark) from Mark); 
 
select S.*, avg(M.Mark) as Diem_Trung_Binh
from Student S
join Mark M on M.StudentID = S.StudentID
group by S.StudentId, S.StudentName
order by Diem_Trung_Binh desc;

