select ime, count(*)
from dosije
where pol = 'z'
group by ime
order by 2 desc
;

select substr(ime, 1, 15), substr(prezime, 1, 25),
    substr(naziv, 1, 75), coalesce(datum_usmenog, datum_pismenog)
from ispit i join predmet p on
    p.id_predmeta = i.id_predmeta
    join dosije d on d.indeks = i.indeks
where coalesce(datum_usmenog, datum_pismenog) = (
    select max(coalesce(datum_usmenog, datum_pismenog)
        from ispit
        where indeks = i.indeks
        and ocena > 5 and status_prijave = 'o'
    )
)
and ocena > 5 and status_prijave = 'o'
order by d.indeks, naziv
;

select distinct d.indeks, d.ime, d.prezime
from dosije d join ispit i on d.indeks = i.indeks
join predmet p on p.id_predmeta = i.id_predmeta
where p.sifra = 'R270' and i.ocena > 5
and i.status_prijave = 'o'
;

select indeks from ispit
where ocena > 5 and status_prijave = 'o'
group by indeks
having avg(ocena+1.0) > 9.8
union
select indeks from ispit
where godina_roka = 2013 and oznaka_roka = 'feb'
and ocena > 5 and status_prijave = 'x'
;

select p.naziv, count(up.id_uslovnog)
from smer s join nivo_kvalifikacije nk 
on s.id_nivoa = nk.id_nivoa
join obavezni_predmet op
on s.id_smera = op.id_smera
join predmet p on op.id_predmeta = p.id_predmeta
left join uslovni_predmet up
on op.id_predmeta =  up.id_predmeta
where s.naziv = 'Matematika' and nk.stepen = 'I'
group by p.id_predmeta, p.naziv
order by p.naziv
;

select s1.indeks, count(*)
from status s1 join status s2
on s1.indeks = s2.indeks
and s1.datum < s2.datum
and s1.status <> s2.status
where not exists (
    select * from status
    where indeks = s1.indeks
    and datum < s2.datum and datum > s1.datum
)
group by s1.indeks
having count(*) >= 3
order by 2 desc
;

select d.indeks, d.ime, d.prezime, s.oznaka, p.sifra,
    p.naziv, uk.godina, uk.semestar
from upisani_kurs uk
    right join dosije dosije d on uk.indeks = d.indeks
    join smer s on d.id_smera = s.id_smera
    left join predmet p on p.id_predmeta = uk.id_predmeta
where ime = 'Goran'
;

select d.indeks, d.ime || ' ' || d.prezime, 
    coalesce(count(p.bodovi), 0)
from dosije d left join ispit i on d.indeks = i.indeks
left join predmet p on p.id_predmeta = i.id_predmeta
and status_prijave = 'o' and ocena > 5
where id_smera in ( select s.id_smera from smer s
    join nivo_kvalifikacije nk on nk.id_smera = s.id_smera
    where nk.naziv like 'Osnovne%'
)
and year(datum_upisa) > 2010
group by d.indeks, d.ime || ' ' || d.prezime
order by 3
;

select d.indeks, d.ime, d.prezime, uk.godina, uk.semestar
from dosije d
    join upisani_kurs uk on d.indeks = uk.indeks
    join predmet p on p.id_predmeta = uk.id_predmeta
where p.sifra = 'P101'
and not exists(
    select * from ispit
    where indeks = d.indeks and godina_roka = uk.godina
    and semestar = uk.semestar
    and id_predmeta = p.id_predmeta
    and ocena > 5 and status_prijave = 'o'
)
;

