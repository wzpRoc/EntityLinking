# DROP TABLE pageAbst;
CREATE TABLE pageAbst(
	page_id	int unsigned	NOT NULL	primary key	auto_increment	comment 'page_id',
	page_title	varchar(255)	NOT NULL	DEFAULT '0'		comment 'page_title',
	page_abst	text			comment 'page_abst',
	page_abst_len	int unsigned	NOT NULL	DEFAULT '0'		comment 'page_abst_len'
);

CREATE INDEX IDX_page_title ON pageAbst(page_title);
