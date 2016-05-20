# DROP TABLE entityName;
CREATE TABLE entityName(
	entityId	int unsigned	NOT NULL		comment 'entityId',
	entityTitle	varchar(255)	NOT NULL	DEFAULT '0'		comment 'entityTitle',
	predicateName	varchar(255)			comment 'predicateName',
	entityName	varchar(255)	NOT NULL	DEFAULT '0'		comment 'entityName',
	pNameToEntity	double			comment 'pNameToEntity',
	pEntityToName	double			comment 'pEntityToName'
);
