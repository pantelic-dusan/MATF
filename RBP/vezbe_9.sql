insert into ispitni_rok
values (2015, 'okt2', 'Oktobar 2 2015')
;

insert into dosije
values (20140055, 'Ivan', 'Markovic', null, null, 'Beograd')
;

insert into ispit(indeks, id_predmeta, oznaka_roka, godina_roka, ocena)
select 20140055, id_predmeta, 'okt2', 2015, 8
from predmet 
where naziv = 'Analiza 2'
;

delete from dosije
where indeks = 20140055
;

delete from ispit
where ocena = 6
;

delete from ispit
;

delete from ispit i
where id_predmeta in (select id_predmeta
    from predmet where naziv='Analiza 3'
)
;

delete from dosije
where indeks not in (select indeks from ispit)
;

update predmet
set bodovi = 20
where naziv = 'Uvod u organizaciju racunara'
;

update dosije
set mesto_rodjenja = null,
datum_rodjenja = datum_rodjenja + (1991 - year(datum_rodjenja)) years
where ime = 'Milan' and prezime = 'Savkovic'
;

update predmet
set bodovi = bodovi*2
;

update predmet
set bodovi = bodovi/2
;

update ispit
set ocena = 6
where ocena = 5 and id_predmeta in (
    select id_predmeta from predmet where naziv = 'Programiranje 1'
)
;

update ispit
set (datum_ispita, ocena) = (select max(datum_ispita), 10 from ispit)
where godina_roka = 2015 and oznaka_roka = 'okt2'
;

create table polozeni_predmeti like ispit
;

alter table polozeni_predmeti
    add primary key (indeks, id_predmeta, godina_roka, oznaka_roka)
    add foreign ky fkDosije(indeks) references dosije
        on delete cascade
    add foreign key fkPredmet(id_predmeta) references predmet
    add foreign key fkRok(godina_roka, oznaka_roka) references ispitni_rok
;

alter table polozeni_predmeti
drop datum_ispita;

reorg table polozeni_predmeti
;

alter table polozeni_predmeti
    add constraint bodovi_ispit check (bodovi between 51 and 100)
    alter column ocena set default 6
;

drop table polozeni_predmeti
;

create table student_ispiti (
    indeks integer not null,
    polozeni_predmeti smallint,
    prosek double,
    foreign key (indeks) references dosije
)
;

alter table student_ispiti
    add broj_ispita smallint
    add constraint ispit check(broj_ispita >= polozeni_predmeti)
;

insert into student_ispiti
select d.indeks,
    nullif(sum(case when ocena >5 then 1 else 0 end), 0),
    avg(case when ocena>5 then ocena*1.0 else null end),
    nullif(count(i.indeks), 0)
from ispit i right join dosije d
on d.indeks = i.indeks
group by d.indeks
;
