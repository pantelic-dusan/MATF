select ime, prezime
from dosije
where indeks in (select indeks
    from ispit where id_predmeta=2001 and ocena>5
)
;

select ime, prezime
from dosije d
where exists (select *
    from ispit
    where d.indeks=indeks and id_predmeta=2001 and ocena>5
)
;

select indeks, ime, prezime
from dosije d
where not exists (
    select * from ispit where d.indeks=indeks
    and id_predmeta=3001
)
;

select indeks, ime, prezime
from dosije d join ispit i on i.indeks=d.indeks
join predmet p on p.id_predmeta=i.id_predmeta
where p.bodovi=5
;

select ime, prezime
from dosije d
where not exists (select * from predmet p
where not exists(select * from ispit i
where i.indeks=d.indeks and i.id_predmeta=p.id_predmeta
and i.ocena>5))
;

select distinct indeks
from ispit i
where not exists(select * from ispit i1
where i1.indeks=20140026 and i.ocena>5
and not exists(select * from ispit i2
where i2.id_predmeta=i1.id_predmeta and i2.ocena>5
and i2.indeks=i.indeks))
;

select indeks, id_predmeta, case ocena
    when 6 then 'sest'
    when 7 then 'sedam'
    when 8 then 'osam'
    when 9 then 'devet'
    when 10 then 'deset'
    else 'nepolozen'
    end as Ocena
from ispit
;

select naziv, case bodovi
            when bodovi<6 then 'lak'
            when bodovi between 6 and 7 then 'srednji'
            else 'tezak'
            end as Tezina
from predmet
;

select indeks, ime, prezime, case
    when not exists(select * from ispit where indeks=d.indeks) then 'brucos'
    when not exists(select * from ispit where indeks=d.indeks and ocena>5) then 'nepolozen'
    else 'student'
    end as "Status studenta"
from dosije d
;

select id_predmeta
from predmet
where bodovi>6
union
select id_predmeta
from ispit
where indeks=20130024
;

select bodovi
from predmet
union
select bodovi
from ispit
;

select id_predmeta
from ispit where godina_roka=2015 and oznaka_roka='jan'
intersect
select id_predmeta
from ispit where godina_roka=2015 and oznaka_roka='feb'
;

select id_predmeta
from ispit
where ocena>5 and indeks=20140021
except
select id_predmeta
from ispit
where ocena>5 and indeks=20140025
order by id_predmeta desc
;










