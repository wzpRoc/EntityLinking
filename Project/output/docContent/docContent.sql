# DROP TABLE docContent;
CREATE TABLE docContent(
	docId	int unsigned	NOT NULL	DEFAULT '0'		comment '文档id',
	content	text	NOT NULL		comment '文档内容'
);

CREATE INDEX IDX_docId ON docContent(docId);
