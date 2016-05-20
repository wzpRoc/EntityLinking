# DROP TABLE entity;
CREATE TABLE entity(
	id	int unsigned	NOT NULL	primary key	auto_increment	comment 'id',
	name	varchar(255)	NOT NULL	DEFAULT '0'		comment 'name',
	abst	varchar(4000)			comment 'abst',
	factCount	int unsigned	NOT NULL	DEFAULT '0'		comment 'factCount'
);

CREATE INDEX IDX_name ON entity(name);
