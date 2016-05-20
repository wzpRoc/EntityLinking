# DROP TABLE candidateEntity;
CREATE TABLE candidateEntity(
	id	int unsigned	NOT NULL	primary key	comment 'ID',
	docId	int unsigned	NOT NULL		comment '文档ID',
	seqInDoc	int unsigned	NOT NULL		comment '文档内部指称序号',
	mentionId	int unsigned	NOT NULL		comment '指称ID',
	mentionName	varbinary(255)	NOT NULL		comment '实体名称',
	entityId	int			comment '候选实体ID',
	wikiTitle	varbinary(255)			comment '候选实体维基标题',
	probOfMentionToEntity	double			comment '指称到实体的概率',
	probOfNameToEntity	double			comment '指称名字到实体的概率',
	rankByProbOfNameToEntity	int			comment '指称到实体的概率排序',
	rankByProbOfMentionToEntity	int			comment '指称名字到实体的概率排序'
);

CREATE INDEX IDX_id ON candidateEntity(id);
CREATE INDEX IDX_docId ON candidateEntity(docId);
CREATE INDEX IDX_entityId ON candidateEntity(entityId);
CREATE INDEX IDX_wikiTitle ON candidateEntity(wikiTitle);
