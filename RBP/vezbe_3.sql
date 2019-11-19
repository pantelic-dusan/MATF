select *
from dosije
;

select indeks
from ispit
;

select distinct indeks
from ispit
;

select distinct oznaka_roka, ocena
from ispit
;

select indeks, prezime
from dosije
where ime='Nikola'
or mesto_rodjenja='Beograd'
;

select sifra
from predmet
where bodovi>=6
;

select *
from ispit
where ocena>5 and oznaka_roka='apr' and godina_roka=2015
;

select indeks, ocena, (bodovi-1)/10+1 as predlog
from ispit
;

select indeks, datum_rodjenja
from dosije
where indeks>20140000
order by datum_rodjenja desc
;

select *
from ispit
where ocena=8
order by datum_ispita desc, indeks asc
;

select naziv
from predmet
where bodovi between 4 and 6
;

select naziv
from predmet
where bodovi not between 4 and 6
;

select id_predmeta
from ispit
where ocena in (5,7,9)
;

select id_predmeta
from ispit
where ocena not in (5,7,9)
;

select naziv
from predmet
where naziv like '%a%'
;

select naziv
from predmet
where naziv like '%a'
;

select ime 
from dosije
where name like 'M____%'
;

select naziv
from predmet
where naziv like '_$%%$\__' escape '$'
;

select ime, prezime
from dosije
where mesto_rodjenja is null
;

select ime, prezime
from dosije
where mesto_rodjenja is not null
;

select ime, prezime
from dosije
where mesto_rodjenja<>'Kraljevo'
;

values (1), (2), (3)
;

values (1,2,3), (4,5,6)
;

values user
;

values current_time
;

values current_date
;

values current_timestamp
;















































































