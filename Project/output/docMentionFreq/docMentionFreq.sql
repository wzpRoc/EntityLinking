# DROP TABLE docMentionFreq;
CREATE TABLE docMentionFreq(
	mentionName	varbinary(255)	NOT NULL	primary key	comment '命名实体指称名称',
	freq	int unsigned	NOT NULL	DEFAULT '0'		comment '在语料库中出现的次数',
	idf	double			comment '在语料库中的idf'
);

CREATE INDEX IDX_mentionName ON docMentionFreq(mentionName);
