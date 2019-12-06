--h2数据源配置数据源加载时执行的一些DML语句
----在这里执行一些在数据源加载时，执行一些DML语句，数据库操作语句，建表，删表，改表
create table if not exists Sys_log (
start_date timestamp
);