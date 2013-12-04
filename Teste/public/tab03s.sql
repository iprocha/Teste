-- STEMS

UPDATE im_lattes.iae_tab02s
set
stems = TRIM(UCASE(stems)),
stems = REPLACE(stems ,'Á','A'),
stems = REPLACE(stems ,'À','A'), 
stems = REPLACE(stems ,'Ã','A'),  
stems = REPLACE(stems ,'Â','A'),  
stems = REPLACE(stems ,'É','E'),  
stems = REPLACE(stems ,'È','E'),  
stems = REPLACE(stems ,'Ê','E'),  
stems = REPLACE(stems ,'Í','I'),  
stems = REPLACE(stems ,'Ì','I'),  
stems = REPLACE(stems ,'Î','I'),  
stems = REPLACE(stems ,'Ó','O'),  
stems = REPLACE(stems ,'Ò','O'),  
stems = REPLACE(stems ,'Ô','O'),  
stems = REPLACE(stems ,'Õ','O'),  
stems = REPLACE(stems ,'Ú','U'),  
stems = REPLACE(stems ,'Ù','U'),  
stems = REPLACE(stems ,'Û','U'),  
stems = REPLACE(stems ,'Ü','U'),  
stems = REPLACE(stems ,'Ç','C');


DROP TABLE IF EXISTS im_lattes.iae_tab03s;

CREATE  TABLE im_lattes.iae_tab03s (
  `id_pesquisador` VARCHAR(16) NOT NULL ,
  `AllStems` LONGTEXT NULL ,
  PRIMARY KEY (`id_pesquisador`) );

SET group_concat_max_len = 10000000;

INSERT im_lattes.iae_tab03s
SELECT id_pesquisador, GROUP_CONCAT(stems separator ' ') AS AllStems
FROM im_lattes.iae_tab02s
WHERE id_tipo_producao_cientifica < 4
GROUP BY id_pesquisador;


