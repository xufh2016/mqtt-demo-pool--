```sql
create table s_qc_rt_measure_data(
    system_time TIMESTAMP, -- comment '仪器时间'
    task_type NCHAR(64), -- comment '任务类型'
    task_id BIGINT UNSIGNED , -- comment '任务编号'
    dev_gps BINARY(30), -- comment '仪器GEO数据'
    data_type BINARY(10), -- comment '数据类型 state-状态，info-信息，report-报告，work-测量'
    task_mode TINYINT UNSIGNED , -- comment '独立任务=0,质控任务-烟尘采样=1,质控任务-烟气分析=2,质控任务-烟气采样=3,质控任务-大气采样=4,质控任务-颗粒物采样=5'
    create_time TIMESTAMP, -- comment '入库时间'
    measure_data NCHAR(5000)  -- comment '检测内容'
) tags(inst_model BINARY(20),inst_id BINARY(20));
```