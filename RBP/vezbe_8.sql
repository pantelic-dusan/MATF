select count(*) ukupno, min(ime), max(indeks)
from dosije
;

select count(*), count(mesto_rodjenja), count(distinct mesto_rodjenja)
from dosije
;

select count(*)
from dosije
where indeks = 20140022 and ocena > 5
;

select count( distinct indeks)
from ispit
where ocena = 8
;

select i.indeks, sum(p.bodovi)
from ispit i join predmet p
on p.id_predmeta = i.id_predmeta
where ocena > 5
group by indeks
;

select indeks, sum(p.bodovi)
from ispit i join predmet p
on p.id_predmeta = i.id_predmeta
where ocena > 5
group by indeks
having sum(p.bodovi) >= 20
order by 2
;

select indeks, avg(ocena+0.0) prosek
from ispit
where ocena > 5
group by indeks
order by prosek desc
;

select oznaka_roka, godina_roka, id_predmeta, count(*)
from ispit
where ocena > 5
group by oznaka_roka, godina_roka, id_predmeta
;

select id_predmeta, count(*)
from ispit
where ocena > 5
group by indeks
having count(*) >= 3
union
select id_predmeta, count(*)
from ispit
where ocena > 5
group by id_predmeta
having count(*) >= 3
order by 2 desc
;

select naziv, count(*)
from ispit i right join predmet p 
on p.id_predmeta = i.id_predmeta
group by p.naziv
order by 2 desc
;

select indeks, sum (case ocena
                    when 8 then 1 else 0 end),
                sum (case ocena
                    when 9 then 1 else 0)
from ispit
group by indeks
;

select *
from dosije
where datum_rodjenja = (select min(datum_rodjenja
    from dosije where datum_rodjenja is not null)
)
;

with klasifikacija as (
    select id_predmeta, case
        when bodovi < 6 then 'lak'
        when bodovi between 6 and 8 then 'srednji'
        else 'tezak'
        end as tezina
    from predmet p
)
select tezina, count(*)
from klasifikacija
group by tezina
;

with pom  as (
    select indeks, sum(p.bodovi) polozeno
    from ispit i join predmet p
    on p.id_predmeta = i.id_predmeta
    where ocena > 5
    group by indeks
)
select * from pom
where polozeno > 20
;

with pom as (
    select oznaka_roka, godina_roka, count(distinct id_predmeta)
    from ispit
    where ocena > 5
    group by godina_roka, oznaka_roka
    having count(distinct id_predmeta) >= 2
)
select count(*)
from pom
;

select id_predmeta, count (distinct indeks)
from ispit
group by id_predmeta
union select id_predmeta, 0
from predmet
where id_predmeta not in (select id_predmeta from ispit)
order by 2 desc
;

with pom as (
    select indeks, id_predmeta
    from ispit
    where ocena = 5
)
select ime, prezime, count(distinct id_predmeta)
from padali i right outer join dosije d
on i.indeks = d.indeks
group by d.indeks, ime, prezime
;

with pom as (
    select distinct indeks, oznaka_roka, godina_roka
    from ispit i
    where ocena > 5
    order by indeks, oznaka_roka
),
vise as (
    select indeks, count(*)
    from pom
    group by indeks
    having count(*) >= 2
)
select count(*)
from vise
;

