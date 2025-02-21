create table book_rank
(
    id         bigint auto_increment comment '主键' primary key,
    book_id    bigint comment '书籍Id',
    cat_id     int         null comment '分类ID',
    rank_type  varchar(50) null comment '排名类型',
    rank_value varchar(50) null comment '排名值',
    rank_date  varchar(32) null comment '排名日期',
    rank_order int         not null comment '排名',
    ext_info   text comment '扩展字段'
) comment '书籍排名' charset = utf8mb4;

ALTER TABLE book_rank
    ADD COLUMN rank_date varchar(32) DEFAULT NULL COMMENT '排名日期';

ALTER TABLE book_rank
    ADD COLUMN book_name varchar(50) DEFAULT NULL COMMENT '书名';
ALTER TABLE book_rank
    ADD COLUMN author varchar(50) DEFAULT NULL COMMENT '作者名';


ALTER TABLE book_rank
    ADD COLUMN rank_date varchar(50) DEFAULT NULL COMMENT '排名日期';



ALTER TABLE book
    ADD COLUMN cat2_id INT DEFAULT NULL COMMENT '分类Id2';


INSERT INTO novel_plus.book_category (id, work_direction, name, sort, create_user_id, create_time, update_user_id, update_time) VALUES (7, 1, '女生频道', 16, null, null, null, null);
