# DROP TABLE mention;
CREATE TABLE mention(
	id	int unsigned	NOT NULL	primary key	auto_increment	comment 'ID',
	docId	int unsigned	NOT NULL		comment '文档ID',
	seq	int unsigned	NOT NULL		comment '文档内部序号',
	startIdx	int unsigned	NOT NULL		comment '开始位置',
	endIdx	int unsigned	NOT NULL		comment '结束位置',
	name	varbinary(255)	NOT NULL		comment '字符串',
	entityId	int			comment '实体ID',
	wikiTitle	varbinary(255)			comment '维基标题',
	initialWeightInDoc	float			comment '初始权重',
	tf	double			comment 'tf',
	idf	double			comment 'idf',
	tfidf	double			comment 'tfidf'
);

CREATE INDEX IDX_docId ON mention(docId);
CREATE INDEX IDX_str ON mention(str);
CREATE INDEX IDX_entityId ON mention(entityId);
