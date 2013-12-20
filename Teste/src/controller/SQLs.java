/*
 * Data:
 * Dezembro de 2013
 * 
 * Descricao:
 * Programa de integracao de processos do Programa Rapid Miner, que compoem as etapas para a 
 * geracao de resultados de similaridades e agrupamentos entre pesquisadores em determinado dominio.
 * 
 * Creditos:
 * Processos Rapid Miner e processamentos de rotina SQL. 
 * Romualdo Alves Pereira Júnior - romualdoalves@gmail.com
 * 
 * Programa de integracao dos processos e  elaboracao de interface grafica.
 * Igor Pessoa Rocha - iprocha@gmail.com
 * 
 */

package controller;

public class SQLs {
	//Sequencia de arquivos a serem executados.
	//Tab 01
	public String SQL_TAB01; 

	//Tab03
	public String SQL_TAB03;
	
	//Tab05 a 09
	public String SQL_TAB05A09;
	
	//Tab10_3_a12
	public String SQL_TAB10_03A12;
	
	//Tab14a15
	public String SQL_TAB14A15;
	
	//Tab16
	public String SQL_TAB16;
	
	//Parametros de schema e dominio de cada tabela. 
	//domain1 eh fixo e eh usado apenas na criacao da tabela01. 
	//Para todas as outras tabelas o dominio eh o domain, ou seja, eh dinamico.
	private String schema;
	private String domain;
	private String domain1 = "iae";
	
	public SQLs(String schema, String domain){
		this.schema = schema;
		this.domain = domain;
	}
	
	//Cria todo o código SQL e DDL para cada tabela chamada e armazena nas costantes
	public void createSQL01(){
		SQL_TAB01 = "DROP TABLE IF EXISTS "+ schema + "."+ domain +"_tab01; "
				  +" CREATE TABLE "+ schema + "."+ domain +"_tab01 ("
		  		  +"`id_pesquisador` INT(11) NOT NULL,"
		  		  +"`id_tipo_producao_cientifica` VARCHAR(1) NOT NULL,"
		  		  +"`id_producao_cientifica` INT(11) NOT NULL,"
		  		  +"`titulo` MEDIUMTEXT,"
		  		  +" PRIMARY KEY (`id_pesquisador`, `id_tipo_producao_cientifica`, `id_producao_cientifica`));"
		  		  +" INSERT " + schema + "." + domain + "_tab01 " 
		  		  +" SELECT B.PESQUISADOR_ID AS id_pesquisador, B.ContributionType AS id_tipo_producao_cientifica, B.ID AS id_producao_cientifica, TITULO AS titulo "  
		  		  +" FROM "+schema+".pesquisador A, "+schema+".basecontribution B "
		  		  +" WHERE A.INFO = '" + domain + "' AND A.ID = B.PESQUISADOR_ID "
		  		  +" ORDER BY id_pesquisador;";
		  	
		
	}
	
	public void createSQL03(){
		SQL_TAB03 = "UPDATE "+ schema +"."+domain+"_tab02s"
				+" set "
				+" stems = TRIM(UCASE(stems)), "
				+" stems = REPLACE(stems ,'Á','A'), "
				+" stems = REPLACE(stems ,'À','A'), "
				+" stems = REPLACE(stems ,'Ã','A'),  "
				+" stems = REPLACE(stems ,'Â','A'),  "
				+" stems = REPLACE(stems ,'É','E'),  "
				+" stems = REPLACE(stems ,'È','E'),  "
				+" stems = REPLACE(stems ,'Ê','E'),  "
				+" stems = REPLACE(stems ,'Í','I'),  "
				+" stems = REPLACE(stems ,'Ì','I'),  "
				+" stems = REPLACE(stems ,'Î','I'),  "
				+" stems = REPLACE(stems ,'Ó','O'),  "
				+" stems = REPLACE(stems ,'Ò','O'),  "
				+" stems = REPLACE(stems ,'Ô','O'),  "
				+" stems = REPLACE(stems ,'Õ','O'),  "
				+" stems = REPLACE(stems ,'Ú','U'),  "
				+" stems = REPLACE(stems ,'Ù','U'),  "
				+" stems = REPLACE(stems ,'Û','U'),  "
				+" stems = REPLACE(stems ,'Ü','U'),  "
				+" stems = REPLACE(stems ,'Ç','C');"
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab03s; "
				+" CREATE  TABLE "+schema+"."+domain+"_tab03s ("
				+"  `id_pesquisador` VARCHAR(16) NOT NULL ,"
				+"  `AllStems` LONGTEXT NULL ,"
				+"  PRIMARY KEY (`id_pesquisador`) ); "
				+" SET group_concat_max_len = 10000000;"
				+" INSERT "+schema+"."+domain+"_tab03s"
				+" SELECT id_pesquisador, GROUP_CONCAT(stems separator ' ') AS AllStems"
				+" FROM "+schema+"."+domain+"_tab02s"
				+" GROUP BY id_pesquisador;";
	}
	
	public void createSQL05a09(){
		SQL_TAB05A09 = "DROP TABLE IF EXISTS "+schema+"."+domain+"_tab05s;"
				+""
				+"CREATE  TABLE "+schema+"."+domain+"_tab05s ("
				+"  `seq1` INT(11) NOT NULL,"
				+"  `seq2` INT(11) NOT NULL,"
				+"  `similarity` DOUBLE NOT NULL,"
				+"  PRIMARY KEY (`seq1`, `seq2`));"
				+""
				+"INSERT "+schema+"."+domain+"_tab05s"
				+" SELECT FIRST_ID AS seq1, SECOND_ID AS seq2, SIMILARITY AS similarity"
				+" FROM "+schema+"."+domain+"_tab04s"
				+" WHERE FIRST_ID <> SECOND_ID AND SIMILARITY > 0;"
				+""
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab06s;"
				+""
				+" CREATE  TABLE "+schema+"."+domain+"_tab06s ("
				+"  `seq` INT(11) NOT NULL,"
				+"  `id_pesquisador` VARCHAR(16) NOT NULL,"
				+"  PRIMARY KEY (`seq`, `id_pesquisador`));"
				+""
				+" INSERT "+schema+"."+domain+"_tab06s"
				+" SELECT @row := @row+1 as seq, id_pesquisador"
				+" FROM "+schema+"."+domain+"_tab03s A, (SELECT @row := 0) r;"
				+""
				+""
				+""
				+""
				+""
				+""
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab07s;"
				+""
				+" CREATE  TABLE "+schema+"."+domain+"_tab07s ("
				+"  `id_pesquisador1` VARCHAR(16) NOT NULL,"
				+"  `seq2` INT(11) NOT NULL,"
				+"  `similarity` DOUBLE NOT NULL,"
				+"  PRIMARY KEY (`id_pesquisador1`, `seq2`));"
				+""
				+" INSERT "+schema+"."+domain+"_tab07s"
				+" SELECT id_pesquisador AS id_pesquisador1, seq2, similarity "
				+" FROM "+schema+"."+domain+"_tab05s A, "+schema+"."+domain+"_tab06s B"
				+" WHERE seq = seq1;"
				+""
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab08s;"
				+""
				+" CREATE  TABLE "+schema+"."+domain+"_tab08s ("
				+"  `id_pesquisador1` VARCHAR(16) NOT NULL,"
				+"  `id_pesquisador2` VARCHAR(16) NOT NULL,"
				+"  `similarity_s` DOUBLE NOT NULL,"
				+"  PRIMARY KEY (`id_pesquisador1`, `id_pesquisador2`));"
				+""
				+" INSERT "+schema+"."+domain+"_tab08s"
				+" SELECT id_pesquisador1, id_pesquisador AS id_pesquisador2, "
				+"       similarity AS similarity_s"
				+" FROM "+schema+"."+domain+"_tab06s, "+schema+"."+domain+"_tab07s"
				+" WHERE seq = seq2;"
				+""
				+""
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab09s;"
				+""
				+" CREATE  TABLE "+schema+"."+domain+"_tab09s ("
				+"  `id_pesquisador1` VARCHAR(16) NOT NULL,"
				+"  `id_pesquisador2` VARCHAR(16) NOT NULL,"
				+"  `similarity_s` DOUBLE NOT NULL,"
				+"  PRIMARY KEY (`id_pesquisador1`, `id_pesquisador2`));"
				+""
				+" INSERT "+schema+"."+domain+"_tab09s"
				+" SELECT * FROM "+schema+"."+domain+"_tab08s"
				+" ORDER BY id_pesquisador1, id_pesquisador2;";
	}
	
	public void createSQL10_03a12() {
		SQL_TAB10_03A12 = " DROP TABLE IF EXISTS "+schema+"."+domain+"_tab10s3;"
				+""
				+" CREATE TABLE "+schema+"."+domain+"_tab10s3"
				+" LIKE "+schema+"."+domain+"_tab10s1;"
				+""
				+" ALTER TABLE "+schema+"."+domain+"_tab10s3 "
				+"  ADD COLUMN `id_pesquisador` VARCHAR(2) NULL AFTER `cluster`;"
				+""
				+" INSERT "+schema+"."+domain+"_tab10s3"
				+" SELECT *,SUBSTR(cluster,9) "
				+" FROM "+schema+"."+domain+"_tab10s1;"
				+""
				+" ALTER TABLE "+schema+"."+domain+"_tab10s3 "
				+"  DROP COLUMN `cluster`;"
				+""
				+" ALTER TABLE "+schema+"."+domain+"_tab10s3"
				+"  CHANGE COLUMN `id_pesquisador` `id_pesquisador` VARCHAR(2) NULL DEFAULT NULL FIRST, "
				+"  ADD COLUMN `id_tipo_producao_cientifica` VARCHAR(1) NULL  AFTER `id_pesquisador` ;"
				+""
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab11s;"
				+""
				+" CREATE  TABLE "+schema+"."+domain+"_tab11s ("
				+"  `cluster` VARCHAR(10) NOT NULL,"
				+"  `seq` INT(11) NOT NULL,"
				+"  PRIMARY KEY (`cluster`, `seq`));"
				+""
				+" INSERT "+schema+"."+domain+"_tab11s"
				+" SELECT cluster, id AS seq "
				+" FROM "+schema+"."+domain+"_tab10s2"
				+" ORDER BY cluster, id;"
				+""
				+""
				+" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab12s;"
				+""
				+" CREATE  TABLE "+schema+"."+domain+"_tab12s ("
				+"  `cluster` VARCHAR(10) NOT NULL,"
				+"  `id_pesquisador1` VARCHAR(16) NOT NULL,"
				+"  PRIMARY KEY (`cluster`, `id_pesquisador1`));"
				+""
				+" INSERT "+schema+"."+domain+"_tab12s"
				+" SELECT cluster, id_pesquisador AS id_pesquisador1  "
				+" FROM "+schema+"."+domain+"_tab11s A, "+schema+"."+domain+"_tab06s B"
				+" WHERE A.seq = B.seq;";
	}
	
	public void createSQL14a15(){
//		A tabela resultado nao eh mais usada. Os dados sao gravados direto na tabela 14,
//		por isso nao ha necessidade de dropa-la.
//		 " DROP TABLE IF EXISTS "+schema+"."+domain+"_tab14s;"
//				+""
//				+" ALTER TABLE "+schema+".tabela_resultado "
//					" RENAME TO "+schema+"."+domain+"_tab14s ;"
		
		SQL_TAB14A15 =	" DROP TABLE IF EXISTS "+schema+"."+domain+"_tab15s;"
				+" CREATE  TABLE "+schema+"."+domain+"_tab15s ("
				+"  `id_pesquisador1` VARCHAR(16) NOT NULL,"
				+"  `stems` VARCHAR(25) NOT NULL,"
				+"  `occur` INT(11));"
				+""
				+" INSERT "+schema+"."+domain+"_tab15s"
				+" SELECT id AS id_pesquisador1, words AS stems, value AS occur "
				+" FROM "+schema+"."+domain+"_tab14s"
				+" ORDER BY id_pesquisador1, stems;";
	}
   
	public void createSQL16(){
		SQL_TAB16 = " ALTER TABLE "+schema+".tabela_resultado"
				+" RENAME TO  "+schema+"."+domain+"_tab16s ;";
	}
	
}
