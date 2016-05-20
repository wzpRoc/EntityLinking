# DROP TABLE docEntity;
CREATE TABLE docEntity(
	docId	int	NOT NULL		comment '文档ID',
	date	date	NOT NULL		comment '日期',
	entityType	char	NOT NULL		comment '实体类型',
	entityId	int	NOT NULL		comment '实体ID',
	entityName	varchar(255)	NOT NULL		comment '实体名称',
	entityValue	varchar(255)	NOT NULL		comment '实体值',
	field	char			comment '字段',
	startPosition	bigint			comment '开始位置',
	endPosition	bigint			comment '结束位置',
	countryId	int			comment '国家ID',
	updateTime	datetime			comment '更新时间',
	updaterName	varchar(20)			comment '更新者'
);

CREATE INDEX IDX_docId ON docEntity(docId);
CREATE INDEX IDX_date ON docEntity(date);
CREATE INDEX IDX_entityType ON docEntity(entityType);
CREATE INDEX IDX_entityId ON docEntity(entityId);
