Please make note of these MySQL credentials again:
  Root User: adminw4n9wQj
  Root Password: YZjJls6VZ9DM
URL: https://feira-comendobem.rhcloud.com/phpmyadmin/
    
		<properties> 
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/jbossews" />
			<property name="javax.persistence.jdbc.user" value="root" />
			<property name="javax.persistence.jdbc.password" value="admin" />
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
			<property name="javax.persistence.schema-generation.database.action"
 				value="create" />
		</properties>
		

	<properties>
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://mysql66591-env-2739445.jelasticlw.com.br/feira" />
			<property name="javax.persistence.jdbc.user" value="root" />
			<property name="javax.persistence.jdbc.password" value="SHOsig31921" />
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
			<property name="javax.persistence.schema-generation.database.action"
 				value="create" /> 
		</properties>
		
drop database feira;
create database feira;
use feira;
show tables;

insert into cidades (id, nome) values (1,'Florianopolis');
insert into cidades (id, nome) values (2,'Sao Jose');

insert into bairros (id, nome, cidade_id) values (1,'Campeche',1);
insert into bairros (id, nome, cidade_id) values (2,'Rio Tavares',1);
insert into bairros (id, nome, cidade_id) values (3,'Centro',1);
insert into bairros (id, nome, cidade_id) values (4,'Costeira',1);

insert into bairros (id, nome, cidade_id) values (5,'Campinas',2);
insert into bairros (id, nome, cidade_id) values (6,'Barreiros',2);
insert into bairros (id, nome, cidade_id) values (7,'Estreito',2);
insert into bairros (id, nome, cidade_id) values (8,'Centro',2);


insert into usuarios (id, email, password, nome, telefone, tipo, endereco, cidade_id, bairro_id) values (1,'henrique.wilhelm@gmail.com','123456','Henrique Wilhelm','4896545864','ADMIN','Rua Moraes 211 ',1,2);



insert into produtos (nome,data,tipo,valor) values ('Entrega','2015/08/24','Uni.','8.00');
insert into produtos (nome,data,tipo,valor) values ('Banana','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Limão Galego','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Couve Flor','2015/08/24','Uni.','2.00');
insert into produtos (nome,data,tipo,valor) values ('Repolho Roxo','2015/08/24','Uni.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Repolho Verde','2015/08/24','Uni.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Beterraba Maço','2015/08/24','Uni.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Cenoura Maço','2015/08/24','Uni.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Chuchu','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Rabanete','2015/08/24','Uni.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Abóbora Cabotiá','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Batata Doce','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Batata Inglesa','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Inhame','2015/08/24','Kg.','7.00');
insert into produtos (nome,data,tipo,valor) values ('Mandioca','2015/08/24','Kg.','3.50');
insert into produtos (nome,data,tipo,valor) values ('Agrião','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Alface Americana','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Alface Crespa','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Alface Roxa','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Brócolis Ramoso','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Coentro','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Couve Manteiga','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Couve Mineira','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Espinafre','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Hortelã','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Rúcula','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Salsinha','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Cebolinha','2015/08/24','Uni.','1.50');
insert into produtos (nome,data,tipo,valor) values ('Acelga','2015/08/24','Uni.','3.00');
insert into produtos (nome,data,tipo,valor) values ('Alho Poró','2015/08/24','Uni.','3.00');
insert into produtos (nome,data,tipo,valor) values ('Alho Comun','2015/08/24','Gr.','5.50');
insert into produtos (nome,data,tipo,valor) values ('Tomate','2015/08/24','Kg.','9.00');
insert into produtos (nome,data,tipo,valor) values ('Laranja Lima','2015/08/24','Kg.','5.00');
insert into produtos (nome,data,tipo,valor) values ('Ovos','2015/08/24','Duzia','9.00');
insert into produtos (nome,data,tipo,valor) values ('Pão Integral','2015/08/24','Uni.','9.00');
insert into produtos (nome,data,tipo,valor) values ('Sabonete Babosa com Capim Limão','2015/08/24','Uni.','5.00');
insert into produtos (nome,data,tipo,valor) values ('Sabonete Argila Verde com Eucalipto','2015/08/24','Uni.','5.00');
insert into produtos (nome,data,tipo,valor) values ('Sabonete Urucum com Bergamota','2015/08/24','Uni.','5.00');
insert into produtos (nome,data,tipo,valor) values ('Sabonete Aveia Mel e Própolis','2015/08/24','Uni.','5.00');
insert into produtos (nome,data,tipo,valor) values ('Sabonete Alecrim','2015/08/24','Uni.','5.00');

insert into usuarios (id, email, password, nome, telefone, tipo, endereco, numero, complemento) values (1,'henrique.wilhelm@gmail.com','123456','Henrique Wilhelm','4896545864','ADMIN','Rua Moraes',205,'Casa');
insert into usuarios (id, email, password, nome, telefone, tipo, endereco, cidade, bairro) values (2,'lnborim@hotmail.com','123456','Leandro Nadaleto Borin','4896545864','ADMIN','Rua Coruja 555','Florianopolis','Campeche');
insert into usuarios (id, email, password, nome, telefone, tipo, endereco, cidade, bairro) values (3,'teste@teste.com','123456','Fulaninho de Tal','4896545864','USER','Rua Coral 205','Florianopolis','Itacurubi');

select * from usuarios;
select * from produtos;
select * from pedidos;
select * from pedido_itens;
select * from itens;

