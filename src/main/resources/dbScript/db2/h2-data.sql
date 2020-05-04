--h2数据源配置数据源加载时执行的一些DDL语句
----在这里执行一些系统启动，加载数据源时。指定的一些ddl语句，数据定义语句，增删改查
insert into Sys_log(start_date) values ( current_timestamp());