# DROP TABLE doc_lob;
CREATE TABLE doc_lob(
	id	int	NOT NULL	primary key	auto_increment	comment 'ID',
	abst	text			comment '摘要',
	content	text			comment '正文'
);
