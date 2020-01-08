select indeks
from dosije
where mesto_rodjenja in (select mesto_rodjenja
    from dosije where year(datum_upisa) < 2014
)
;

select naziv
from ispitini_rok ir
where not exists ( select * from ispit where
    id_predmeta in (select id_predmeta from predmet
        where bodovi = 8)
    and godina_roka = ir.godina_roka
    and oznaka_roka = ir.oznaka_roka
)
or exists (select * from ispit
    where godina_roka = ir.godina_roka
    and oznaka_roka <> ir.oznaka_roka
    and ocena = 5
    and id_predmeta in ( select id_predmeta from predmet
        where bodovi = 8)
)
;

select indeks, ime, prezime, coalesce(mesto_rodjenja, 'Nepoznato')
from dosije d
where indeks < any(select indeks from dosije
    where ime = 'Marija' and prezime = 'Savkovic'
)
;

select distinct p.*
from predmet p join ispit i1 on i1.id_predmeta = p.id_predmeta
    join dosije d1 on d1.indeks = i1.indeks
    join ispit i2 on i2.id_predmeta = p.id_predmeta
        and i2.indeks <> i1.indeks
    join dosije d2 on d2.indeks = i2.indeks
where d1.mesto_rodjenja = d2.mesto_rodjenja
and i1.ocena > 5 and i2.ocena > 5
;

select substr(char(indeks),5,4) || '/' || substr(char(indeks),1,4)
from ispit i
where ocena = 10
and not exists (select * from ispit
    where id_predmeta = i.id_predmeta
    and ocena = 10
    and indeks <> i.indeks
)
;

select ime || ' ' || prezime,
    month(current_date - datum_ispita)
    + 12 * year(current_date - datum_ispita)
    Broj_meseci
from ispit i join dosije d
on i.indeks = d.indeks
where ocena in (8,9) and day(datum_ispita) = 25
;

select p1.naziv, p2.naziv
from predmet p1, predmet p2
where p1.id_predmeta < p2.id_predmeta
and not exists (select * from ispit i1 join ispit i2
    on i1.indeks <> i2.indeks
    where i1.id_predmeta = p1.id_predmeta and i1.ocena > 5
    and i2.id_predmeta = p2.id_predmeta and i2.ocena > 5
    and abs(days(i2.datum_ispita)-days(i1.datum_ispita))<15
)
;

select indeks, year(datum_rodjenja) godina, naziv
from dosije d full outer join ispitini_rok ir
on year(datum_rodjenja) = ir.godina_roka
;
