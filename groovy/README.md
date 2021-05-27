# Ejercicio #3
## dentro del mySQL data base sample
```
select user_crea,count(*) from cuentas
where fecha_calc > '2021-05-26' and fecha_crea > '2021-05-27'
group by user_crea order by 1;
```
