with slusanost2007 as (
    select id_predmeta, semestar, count(indeks) br
    from upisan_kurs uk
    where godina = 2007
    group by id_predmeta, semestar
)
select naziv, semestar
from predmet p join slusanost2007 s 
on p.id_predmeta = s.id_predmeta
where br = (select max(br) from slusanost2007)
;

with diplomci as (
    select s.id_smera, s.naziv, d.indeks, datum_upisa,
    max(coalesce(datum_usmenog, datum_pismenog)) poslednji
    from dosije d join ispit i on i.indeks = d.indeks
    join smer s on d.id_smera = s.id_smera
    where s.bodovi <= (
        select sum (p.bodovi)
        from ispit i join predmet p
        on p.id_predmeta = i.id_predmeta
        where indeks = d.indeks and ocena > 5
        and status_prijave = 'o'
    )
    group by s.id_smera, s.naziv, d.indeks, datum_upisa
)
select naziv, avg(days(poslednji)-days(datum_upisa)*1.0)
from diplomci d
group by id_smera, naziv
;

with nagradjeni as (
    select d.indeks, s.naziv, s.id_smera, avg(ocena*1.0) prosek
    from dosije d join smer s on d.id_smera = s.id_smera
    join ispit i on i.indeks = d.indeks
    join predmet p on p.id_predmeta = i.id_predmeta
    where ocena > 5 and status_prijave = 'o'
    group by d.indeks, s.id_smera, s.naziv, s.bodovi
    having sum (p.bodovi) < s.bodovi
    and sum(p.bodovi) >= s.bodovi/4
),
rang as (
    select n.*, (select count (distinct prosek) +1
        from nagradjeni where id_smera = n.id_smera
        and prosek > n.prosek) as rbr
    from nagradjeni n
)
select naziv, indeks
from rang r
where rbr <= 5
order by naziv, prosek desc
;

create table kvota(
    godina smallint not null,
    id_smera integer not null,
    br_budzet smallint not null,
    br_samofinansiranje smallint not null,
    primary key (godina, id_smera),
    foreign key fk_kvota_smer(id_smera) references smer
)
;

insert into kvota
with aktuelni_status as (
    select s.indeks, d.id_smera, status 
    from status s join dosije d on d.indeks = s.indeks
    where not exists ( select * from status 
        where indeks = s.indeks and datum < s.datum
    )
)
select d.indeks/10000, d.id_smera,
sum(case when status = 'budzet' then 1 else 0 end),
sum(case when status = 'samofinansiranje' then 1 else 0 end)
from aktuelni_status d
group by d.indeks/10000, d.id_smera
;

insert into kvota
select year(current date), s.id_smera, 60, 30
from smer s join nivo_kvalifikacije nk on nk.id_nivoa = s.id_nivoa
where nk.naziv like 'Osnovne%'
;

update kvota
set br_budzet = (
    select max(mod(indeks,10000))
    from dosije d join smer s on s.id_smera = d.id_smera
    where year(datum_upisa) = 2007
    and oznaka = 'R'
)
where id_smera in (select id_smera
    from smer where oznaka = 'R'
)
and godina = 2007
;

drop table kvota
;





