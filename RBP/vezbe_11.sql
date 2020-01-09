with stud_bodovi as (
    select d.indeks, d.id_smera, sum(p.bodovi) ukupno
    from dosije d join ispit i 
    on d.indeks = i.indeks
    join predmet p on p.id_predmeta = i.id_predmeta
    where ocena > 5 and status_prijave = 'o'
    group by d.indeks, d.id_smera
)
select s.id_smera, substr(s.naziv,1 50), sk.indeks,
substr(rtrim(d.ime),1,15) || ' ' || substr(rtrim(d.prezime,1,25))
from smer s left join stud_bodovi sk 
on sk.id_smera = s.id_smera
left join dosije d on d.indeks = sk.indeks
where ukupno = (select max(ukupno)
    from stud_bodovi
    where id_smera = sk.id_smera)
or sk.indeks is NULL
order by sk.ukupno
;

select naziv, avg(
    case when ocena > 5 and status_prijave = 'o'
    then 100.0 else 0.0 end
)
from ispit i join predmet p 
on p.id_predmeta = i.id_predmeta
group by p.id_predmeta, naziv
;

select d.indeks, substr(rtrim(d.ime) || ' ' || rtrim(d.prezime), 1, 25),
    p.naziv
from dosije d join obavezan_predmet op 
on d.id_smera = op.id_smera
join smer s on s.id_smera = d.id_smera
join predmet p on p.id_predmeta = op.id_predmeta
where indeks between 20060001 and 20069999
and s.naziv like 'Racunarstvo i informatika'
and not exists (
    select * from ispit
    where i.indeks = d.indeks
    and i.id_predmeta = op.id_predmeta
    and ocena > 5 and status_prijave = 'o'
)
;

select d.indeks, ime, prezime, p_o.naziv, p_u.naziv
from dosije d join obavezan_predmet op
on op.id_smera = d.id_smera
join predmet p_o on p_o.id_predmeta = op.id_predmeta
join uslovni_predmet up
on up.id_predmeta = op.id_predmeta
join predmet p_u on p_u.id_predmeta = up.id_uslovnog
join smer s on d.id_smera = s.id_smera
where indeks/10000 = 2007
and s.naziv = 'Teorijska matematika i primene'
and not exists (
    select * from ispit
    where indeks = d.indeks
    and id_predmeta = p_u.id_predmeta
    and ocena > 5 and status_prijave = 'o'
)
;

with predmet_na_nivou as (
    select id_nivoa, id_predmeta, count(*) br_smerova
    from obavezan_predmet op join smer s
        on op.id_smera = s.id_smera
    group by id_nivoa, id_predmeta
),
smerovi_na_nivou as (
    select id_nivoa, count(*) br_smerova
    from smer s 
    group by id_nivoa
)
select nk.naziv, p.naziv
from predmet_na_nivou pnn join smerovi_na_nivou snn 
on pnn.id_nivoa = snn.id_nivoa
join nivo_kvalifikacije nk on pnn.id_nivoa = nk.id_nivoa
join predmet p on p.id_predmeta = pnn.id_predmeta
where pnn.br_smerova = snn.br_smerova
and exists (select * from smer where id_nivoa = nk.id_nivoa)
;

select d.indeks, ime, prezime, naziv, ocena,
    case
        when exists(select * from obavezan_predmet where id_predmeta = i.id_predmeta
            and d.id_smera = id_smera) then 'obavezan'
        when p.id_predmeta is NULL then NULL
        else 'izborni'
        end as kategorija
from dosije d left join ispit i on i.indeks = d.indeks
left join predmet p on p.id_predmeta = i.id_predmeta
and ocena > 5 and status_prijave = 'o'
where substr(ime, 1, 1) = 'P'
and month(datum_rodjenja) between 2 and 7
;

create index dosije_ime_prezime on dosije (ime desc, prezime desc)
;

drop index dosije_ime_prezime
;

create view ispitni_rok_2011 as
select godina, oznaka, naziv, pocetak_prijavljivanje, kraj_prijavljivanja
from ispitni_rok
where godina >= 2011
;

select * from ispitni_rok_2011
;

drop view ispitni_rok_2011
;

create view mp_ispiti1 as
select *
from ispit i
where exists (
    select * from dosije d
    where i.indeks = d.indeks
    and d.ime = 'Marko' and d.prezime = 'Petrovic'
)
;

drop view mp_ispiti1
;

create view polaganja_stud as 
select * from ispit i 
where exists (
    select * from dosije
    where indeks = i.indeks
    and ime in ('Marko', 'Marija')
)
and exists (
    select * from ispit
    where indeks = i.indeks and ocena > 5 and status_prijave = 'o'
    and oznaka_roka = 'apr' and godina_roka = 2008
)
with check option
;









