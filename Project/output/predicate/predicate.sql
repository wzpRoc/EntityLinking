# DROP TABLE predicate;
CREATE TABLE predicate(
	id	int unsigned	NOT NULL	primary key	auto_increment	comment 'id',
	name	varchar(255)	NOT NULL	DEFAULT '0'		comment 'name',
	normalizedName	varchar(255)	NOT NULL	DEFAULT '0'		comment 'name',
	factCount	int unsigned	NOT NULL	DEFAULT '0'		comment 'factCount'
);

CREATE INDEX IDX_name ON predicate(name);
CREATE INDEX IDX_normalizedName ON predicate(normalizedName);
