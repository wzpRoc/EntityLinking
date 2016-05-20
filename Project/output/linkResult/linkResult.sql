# DROP TABLE linkResult;
CREATE TABLE linkResult(
	docId	int	NOT NULL		comment '文档ID',
	date	date	NOT NULL		comment '日期',
	entityType	char	NOT NULL		comment '实体类型',
	entityId	int	NOT NULL		comment '实体ID',
	entityName	varchar(255)	NOT NULL		comment '实体名称',
	entityValue	varchar(255)	NOT NULL		comment '实体值',
	startPosition	bigint			comment '开始位置',
	endPosition	bigint			comment '结束位置',
	experimentId	varchar(255)	NOT NULL		comment '实验ID',
	probability0	int			comment '最大概率',
	entityId0	int			comment '最大概率实体ID',
	entityTitle0	varchar(255)			comment '最大概率实体标题',
	probability1	double			comment '次大概率',
	entityId1	int			comment '次大概率实体ID',
	entityTitle1	varchar(255)			comment '次大概率实体标题',
	probabilityDiff	double			comment '最大与次大概率之差',
	correct	tinyint			comment '是否正确'
);

CREATE INDEX IDX_docId ON linkResult(docId);
CREATE INDEX IDX_date ON linkResult(date);
CREATE INDEX IDX_entityType ON linkResult(entityType);
CREATE INDEX IDX_entityId ON linkResult(entityId);
