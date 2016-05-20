# DROP TABLE wikiFact;
CREATE TABLE wikiFact(
	id	int unsigned	NOT NULL	primary key	auto_increment	comment 'ID',
	subjectId	int unsigned		DEFAULT '0'		comment 'subjectId',
	predicateId	int unsigned		DEFAULT '0'		comment 'predicateId',
	objectId	int unsigned		DEFAULT '0'		comment 'objectId',
	subjectName	varchar(4000)			comment 'subjectName',
	predicateName	varchar(4000)			comment 'predicateName',
	objectName	varchar(4000)			comment 'objectName'
);
