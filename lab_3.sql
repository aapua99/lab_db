SELECT ship, battle, result
FROM outcomes
WHERE result='sunk'
ORDER BY ship DESC;

SELECT class
FROM ships
WHERE class LIKE '%o' AND class NOT LIKE '%go';

SELECT distinct case when pc.model>d2.model then concat(pc.model, ' ',d2.model)
when pc.model< d2.model then concat(d2.model,' ', pc.model) 
else concat(pc.model,' ',d2.model) end as model, pc.speed, pc.ram
FROM pc join pc as d2 on (pc.model != d2.model and pc.ram=d2.ram and pc.speed=d2.speed) or ( pc.ram=d2.ram and pc.speed=d2.speed and (pc.hd!=d2.hd or pc.cd != d2.cd));
    SELECT class, country
    FROM classes
    WHERE country LIKE 'Ukraine'
UNION
    SELECT class, country
    FROM classes
    WHERE NOT EXISTS(SELECT class, country
    FROM classes
    WHERE country LIKE 'Ukraine');
SELECT DISTINCT maker
FROM product
WHERE type LIKE 'laptop' AND maker IN (SELECT maker
    FROM product
    WHERE type LIKE 'printer');
SELECT 'FROM '+town_from+' to '+town_to
FROM trip;

select country, min(launched) as year , max(l) as count
from (
select country, launched, count(launched) as l
    from ships join classes on ships.class = classes.class
    group  by launched, country) as p
group by p.country;
SELECT maker, AVG(screen)
from product join laptop on product.model=laptop.model
GROUP BY maker;
    select p.point, p.date, Case
when out>out_day then 'more than once a day'
when out_day>out then 'once a day'
when out=out_day then 'both'
end as out
    from outcome_o join (select Sum(out) as out_day, date, point
        from outcome
        group by date, point) as p on outcome_o.point=p.point AND outcome_o.date=p.date
union
    select point, date , 'once a day' as out
    from outcome_o
    where outcome_o.date not in (select date
    from outcome)
union
    select point, date , 'more than once a day' as out
    from outcome
    where outcome.date not in (select date
    from outcome_o);
select name
from ships
where ships.name like '% %';