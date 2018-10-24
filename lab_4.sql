select model, ram, price from labor_sql.pc where ram>64 order by hd;
select * from labor_sql.outcome where month(date)=3;
(select maker, prod.model, price from (select maker, model from labor_sql.product where type like 'PC') as prod join labor_sql.pc as pc on prod.model=pc.model)
union 
(select maker, model, null from labor_sql.product where model not in (select model from labor_sql.pc) and type like 'PC');
select distinct maker from labor_sql.product join labor_sql.pc on labor_sql.product.model=labor_sql.pc.model;
select distinct maker from labor_sql.product where type like 'PC' and maker in(select distinct maker from labor_sql.product where type like 'Printer');
SELECT concat('FROM ', town_from,' TO ',town_to) as trip FROM labor_sql.trip;
select model, 'laptop' as type ,speed from labor_sql.laptop where speed<(select MIN(speed) from labor_sql.pc);
select ship, country from labor_sql.outcomes left join (select country,name, labor_sql.classes.class from labor_sql.ships join labor_sql.classes on labor_sql.ships.class=labor_sql.classes.class) as d1 on labor_sql.outcomes.ship=d1.name where  d1.country is not null;
select distinct d1.maker, case when d1.maker in (select distinct maker from labor_sql.product where type like 'Laptop') then concat('yes(', d2.count,')')
else 'no' end as answer from labor_sql.product as d1 left join (select maker, count(maker) as count from labor_sql.product where type like 'Laptop' group by maker) as d2 on d1.maker=d2.maker ;
(SELECT name, class from labor_sql.ships)
union 
select ship as name, null as class from labor_sql.outcomes where ship not in (select name from labor_sql.ships);
