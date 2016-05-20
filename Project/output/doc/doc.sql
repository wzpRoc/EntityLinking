# DROP TABLE doc;
CREATE TABLE doc(
	id	int unsigned	NOT NULL	primary key	auto_increment	comment 'ID',
	title	varbinary(255)	NOT NULL		comment '标题',
	content	text			comment '内容',
	tokens	text			comment 'token列表',
	url	varbinary(255)			comment 'URL',
	toeTag	varbinary(255)			comment '训练/测试标记',
	pubDate	date	NOT NULL		comment '发布日期'
);
