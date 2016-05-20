# DROP TABLE docWordFreq;
CREATE TABLE docWordFreq(
	wordName	varbinary(255)	NOT NULL	primary key	comment '词的名称',
	freq	int unsigned	NOT NULL	DEFAULT '0'		comment '文档中出现的次数',
	idf	double			comment '在语料库中的idf'
);

CREATE INDEX IDX_wordName ON docWordFreq(wordName);
