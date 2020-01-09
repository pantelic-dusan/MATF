create table predmet_student (
    id_predmeta integer not null primary key,
    br_studenata smallint,
    foreign key fk_predmet(id_predmeta) references predmet
)
;

insert into predmet_student
select id_predmeta, 5
from obavezan_predmet
where id_predmeta = 201
;

select * from predmet_student
;

merge into predmet_student ps 
using (select p.id_predmeta, count(i.indeks) br
    from ispit i right join predmet p
    on i.id_predmeta = p.id_predmeta
    and ocena > 5 and status_prijave = 'o'
    group by p.id_predmeta    
) as pom
on ps.id_predmeta = pom.id_predmeta
when matched then
    update set ps.br_studenata = pom.br_studenata
when not matched then
    insert values(pom.id_predmeta, pom.br)
;

select * from predmet_student
;

drop table predmet_student
;

create table studenti_podaci (
    indeks integer not null primary key,
    broj_polozenih smallint,
    prosek float,
    datum_rodjenja date,
    foreign key (indeks) references dosije
)
;

insert into studenti_podaci
with diplomirani as (
    select indeks
    from dosije d
    where 'diplomirao' in (
        select status from status
        where indeks = d.indeks
    )
)
select indeks, count(*), avg(ocena+0.0), null
from ispit
where ocena > 5 and status_prijave = 'o'
and indeks not in (select indeks from diplomirani)
group by indeks
having avg(ocena+0.0) > 8
union select indeks, 40, 10.0, null
from diplomirani
;

merge into studenti_podaci sp 
using (
    select d.indeks, status, datum_rodjenja, count (
        case when ocena>5 and status_prijave='o' then ocena
        else null
        end
    ) brp,
    avg((case when ocena>5 and status_prijave='o' then ocena
        else null
        end)+0.0) prosek
    from dosije d left join ispit i on d.indeks = i.indeks
    left join status s on s.indeks = d.indeks
    where not exists (select * from status
        where indeks = d.indeks
        and datum > s.datum
    )
    and not exists (select * from status
        where indeks = d.indeks and datum = s.datum
        and status <> s.status
    )
    group by d.indeks, status, datum_rodjenja
) as pom
on pom.indeks = sp.indeks
when matched and pom.status = 'diplomirao' then 
    update set sp.datum_rodjenja = pom.datum_rodjenja
when matched and pom.status = 'budzet' then
    update set (sp.broj_polozenih, sp.prosek) = (pom.brp, pom.prosek)
when matched and pom.status = 'ispisan' then
    delete
when not matched and pom.status <> 'ispisan' then
    insert (indeks, broj_polozenih, prosek)
    values (pom.indeks, pom.brp, pom.prosek)
else
    ignore
;

with smerovi as (
    select d.id_smera, indeks/10000, count(*) br_studenata
    from dosije d join smer s
    on s.id_smera = d.id_smera
    join nivo_kvalifikacije nk
    on nk.id_nivoa = s.id_nivoa
    where nk.naziv like 'Osnovne%'
    group by d.id_smera, indeks/10000
), upisani_po_godini as (
    select godina, sum(br_studenata) br_studenata
    from smerovi
    group by godina
), prosek_po_smeru as (
    select id_smera, godina, avg(ocena+0.0) prosek
    from ispit i join dosije d 
    on d.indeks = i.indeks
    where ocena > 5 and status_prijave = 'o'
    group by id_smera, godina
)
select s.oznaka, s.naziv, sm.godina, 100.0*sm.br_studenata/upg.br_studenata, prosek
from smerovi sm join smer s 
on s.id_smera = sm.id_smera
join upisani_po_godini upg on sm.godina = upg.godina
left join prosek_po_smeru pps on pps.id_smera = sm.id_smera
and pps.godina = sm.godina
;

select d.indeks, d.ime, d.prezime, s.oznaka, p.sifra,
    p.naziv, i.godina, i.semestar, ocena
from dosije d left outer join ispit i 
on d.indeks = i.indeks and ocena > 5 and status_prijave = 'o'
join smer s on s.id_smera = d.id_smera
left outer join predmet p on p.id_predmeta = i.id_predmeta
where substr(d.ime, 1, 3) = 'Mil'
;

select p.sifra, substr(p.naziv, 1, 30), i.oznaka_roka,
count(*), sum (case when ocena>5 and status_prijave='o' then 1 else 0 end),
avg(case when ocena > 5 and status_prijave='o' then ocena+0.0 else null end)
from ispit i join predmet p on p.id_predmeta = i.id_predmeta
join dosije d on d.indeks = i.indeks
join smer s on s.id_smera = d.id_smera
where s.naziv = 'Racunarstvo' and i.godina = 2006
group by p.sifra, p.naziv, i.oznaka_roka
order by 6 desc
;

with upisali as (
    select s.id_smera, p.id_predmeta, d.indeks
    from nivo_kvalifikacije nk join smer s
    on nk.id_nivoa = s.id_nivoa
    join obavezan_predmet op on op.id_predmeta = s.id_smera
    join predmet p on op.id_predmeta = p.id_predmeta
    join upisani_kurs uk on uk.id_predmeta = p.id_predmeta
    join dosije d on d.indeks = uk.indeks
    and d.id_smera = s.id_smera
    where nk.naziv like 'Osnovne%' and uk.godina = 2007
),
ponovili as (
    select id_smera, id_predmeta, count(indeks) br_ponovo
    from upisali u
    where exists (
        select * from upisani_kurs
        where indeks = u.indeks
        and id_predmeta = u.id_predmeta and godina < 2007
    )
    group by id_smera, id_predmeta
)
select s.oznaka, s.naziv, p.naziv, count(u.indeks) br_upisanih,
br_ponovo, case when count(u.indeks) <> 0 then coalesce(br_ponovo, 0)*100.0/count(u.indeks)
                else 0 end as procenat
from upisali u right join smer s
on u.id_smera = s.id_smera
left join predmet p on u.id_predmeta = p.id_predmeta
left join ponovili pon on pon.id_smera = u.id_smera
and u.id_predmeta = pon.id_predmeta
where s.id_nivoa in (
    select id_nivoa from nivo_kvalifikacije 
    where naziv like ’Osnovne%’
)
group by s.id_smera, p.id_predmeta, br_ponovo,s.oznaka, s.naziv, p.naziv
order by br_upisanih desc
;

create table prvi_ispit (
    indeks integer not null primary key,
    datum date
)
;

insert into prvi_ispit
select indeks, min(coalesce(datum_usmenog, datum_pismenog))
from ispit
where ocena > 5 and status_prijave = 'o'
group by indeks
;

delete from prvi_ispit
where indeks in (
    select indeks
    from dosije d join smer s on s.id_smera = d.id_smera
    join nivo_kvalifikacije nk on nk.id_nivoa = s.id_nivoa
    where nk.naziv like 'Osnovne%'
)
;

update prvi_ispit as pi
set datum = (
    select max(coalesce(datum_usmenog, datum_pismenog)
    from ispit i 
    where i.indeks = pi.indeks and ocena > 5
    and status_prijave = 'o'
    )
)
where indeks in (
    select indeks
    from dosije d join smer s on s.id_smera = d.id_smera
    join nivo_kvalifikacije nk on nk.id_nivoa = s.id_nivoa
    where nk.naziv like 'Doktorske%'
)
;

merge into prvi_ispit pi
using (
    select d.indeks, nk.naziv,
    max(coalesce(datum_usmenog, datum_pismenog)) as naveci
    from dosije d join smer s on s.id_smera = d.id_smera
    join nivo_kvalifikacije nk on nk.id_nivoa = s.id_nivoa
    join ispit i on i.indeks = d.indeks
    where ocena > 5 and status_prijave = 'o'
    and (nk.naziv like ’Osnovne%’ or nk.naziv like ’Doktorske%’
) as pom
on pi.indeks = pom.indeks
when matched and pom.naziv like 'Osnovne%'
then delete
when matched and pom.naziv like 'Doktorske%'
update set pi.datum = pom.naveci
else ignore
;

drop table prvi_ispit
;

