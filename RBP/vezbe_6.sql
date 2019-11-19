select sifra, naziv, decimal(bodovi*1.35,4,1) as uvecanje
from predmet
;

select ceil(bodovi*1.35) as uvecanje
from predmet
where bodovi*1.35>8
;

select indeks, ime || ' ' || predmet "ime i prezime",
    length(ime)+length(prezime) "broj slova",
    substr(ime,1,1) || substr(prezime,1,1) inicijali,
    replace(mesto_rodjenja, 'Beograd', 'Bg') "mesto"
from dosije
;

values char(current_date, ISO), char(current_date, EUR),
char(current_date, USA), char(current_date, LOCAL)
;

values dayname(date('2018-11-18'))
;

values dayofyear(current_date), week(current_date),
dayofweek(current_date), dayname(current_date),
monthname(current_date)
;

values hour(current_time), minute(current_time), second(current_time)
;

values current_date + 12 years + 5 months + 25 days
;

select *
from ispit
where datum_ispita > date('2015-04-01')
;

select *
from ispit
where year(current_date) - year(datum_ispita) <=4
;

select indeks, days(current_date) - days(datum_ispita)
from ispit i
where datum_ispita >= all(select datum_ispita from ispit
where indeks=i.indeks and datum_ispita is not null)
;

select indeks, ime, prezime,
coalesce(mesto_rodjenja, 'Nepoznato')
from dosije
;

select indeks, ime, prezime,
nullif(mesto_rodjenja, 'Beograd')
from dosije
;

