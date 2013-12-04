-- STEMS

DROP TABLE IF EXISTS im_lattes.iae_tab10s3;

CREATE TABLE im_lattes.iae_tab10s3
LIKE im_lattes.iae_tab10s1;

ALTER TABLE im_lattes.iae_tab10s3 
  ADD COLUMN `id_pesquisador` VARCHAR(2) NULL AFTER `cluster`;

INSERT im_lattes.iae_tab10s3
SELECT *,SUBSTR(cluster,9) 
FROM im_lattes.iae_tab10s1;

ALTER TABLE im_lattes.iae_tab10s3 
  DROP COLUMN `cluster`;

ALTER TABLE im_lattes.iae_tab10s3
  CHANGE COLUMN `id_pesquisador` `id_pesquisador` VARCHAR(2) NULL DEFAULT NULL FIRST, 
  ADD COLUMN `id_tipo_producao_cientifica` VARCHAR(1) NULL  AFTER `id_pesquisador` ;


-- STEMS

DROP TABLE IF EXISTS im_lattes.iae_tab11s;

CREATE  TABLE im_lattes.iae_tab11s (
  `cluster` VARCHAR(10) NOT NULL,
  `seq` INT(11) NOT NULL,
  PRIMARY KEY (`cluster`, `seq`));

INSERT im_lattes.iae_tab11s
SELECT cluster, id AS seq 
FROM im_lattes.iae_tab10s2
ORDER BY cluster, id;


DROP TABLE IF EXISTS im_lattes.iae_tab12s;

CREATE  TABLE im_lattes.iae_tab12s (
  `cluster` VARCHAR(10) NOT NULL,
  `id_pesquisador1` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`cluster`, `id_pesquisador1`));

INSERT im_lattes.iae_tab12s
SELECT cluster, id_pesquisador AS id_pesquisador1  
FROM im_lattes.iae_tab11s A, im_lattes.iae_tab06s B
WHERE A.seq = B.seq;






