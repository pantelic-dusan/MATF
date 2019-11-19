select ime, prezime, id_predmeta
from dosije d join ispit i
on d.indeks = i.indeks
;

select indeks, ime, prezime, naziv
from dosije d join ispit i
on i.indeks = d.indeks
join predmet p on p.id_predmeta = i.id_predmeta
where i.ocena>5
;

select d1.indeks, d2.indeks
from dosije d1 join dosije d2
on d1.mesto_rodjenja = d2.mesto_rodjenja
and d1.indeks < d2.indeks
;

select indeks, naziv
from ispit i join predmet p
on i.id_predmeta = p.id_predmeta
where i.ocena>7
order by 1 asc
;

select ir1.naziv, ir2.naziv
from ispit i1 join ispitni_rok ir1
on i1.oznaka_roka = ir1.oznaka_roka and i1.godina_roka = ir1.godina_roka
join ispit i2 on i1.indeks=i2.indeks and i1.id_predmeta = i2.id_predmeta
and (i1.oznaka_roka<>i2.oznaka_roka or i1.godina_roka<>i2.godina_roka)
join ispitni_rok ir2 on i2.oznaka_roka = ir2.oznaka_roka and
ir2.godina_roka = i2.godina_roka
;

select p.naziv, i.godina_roka, i.oznaka_roka
from predmet p left join ispit i
on i.id_predmeta = p.id_predmeta
order by p.naziv
;

select ir.naziv, i.ocena
from ispitni_rok ir left outer join ispit i
on i.godina_roka=ir.godina_roka and i.oznaka_roka=ir.oznaka_roka
order by ir.naziv
;

select p.naziv, i.indeks, i.ocena, i.datum
from predmet p left outer join ispit i on i.id_predmeta = p.id_predmeta
and i.ocena>5
;

select p.naziv, p.bodovi, i.indeks, i.ocena, i.godina_roka, i.oznaka_roka
from predmet p full outer join ispit i
on i.id_predmeta=p.id_predmeta and i.ocena=p.bodovi
;

select *
from predmet
where bodovi < any(select bodovi from predmet)
;

select *
from dosije
where datum_rodjenja <= all(select datum_rodjenja from dosije
where mesto_rodjenja is not NULL)
;






