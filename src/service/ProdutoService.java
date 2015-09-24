package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import util.JPA;
import models.Categoria;
import models.Produto;
import models.Tipo;
	 
@ManagedBean(name = "produtoService")
@ApplicationScoped
public class ProdutoService implements Serializable {
	     
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1780587696308041647L;
		private final static String[] nomes;
	    private final static String[] tipos; 
	    private final static Date[] datas;
	    private final static Double[] valores;
	    
	    static {
	        nomes = new String[3];
	        nomes[0] = "Banana";
	        nomes[1] = "Pepino";
	        nomes[2] = "Inhame";
	         
	        tipos = new String[3];
	        tipos[0] = "Uni.";
	        tipos[1] = "Kg.";
	        
	        datas = new Date[3];
	        datas[0] = new Date(2015, 8, 21);
	        datas[1] = new Date(2015, 8, 22);
	        datas[2] = new Date(2015, 8, 21);
	        
	        valores = new Double[6];
	        valores[0] = 2.50;
	        valores[1] = 3.00;
	        valores[2] = 4.50;
	        valores[0] = 5.00;
	        valores[1] = 8.00;
	        valores[2] = 7.50;
	    }
	     
	    public void createProdutos() {
	     
	    		EntityManager em = JPA.getEM();
	    		em.getTransaction().begin();
		    	Query query = em.createNativeQuery("insert into categorias (nome) values ('Frutas');");query.executeUpdate();
		    		  query = em.createNativeQuery("insert into categorias (nome) values ('Raizes & Tubérculos');");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into categorias (nome) values ('Legumes');");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into categorias (nome) values ('Hortaliças');");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into categorias (nome) values ('Outros');");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into categorias (nome) values ('Grãos');");query.executeUpdate();
			  	  	  
			  	  	  query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Uni.',1,5);");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Kg.',0.5,3);");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Duzia',1,3);");query.executeUpdate();
			  	  	  query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Gr.',0.1,0.5);");query.executeUpdate();
			  	  	  
			  	  	  query = em.createNativeQuery("insert into status (nome,diainicial,hrinicial,diafinal,hrfinal) values ('EMANDAMENTO',1,420,5,780);");query.executeUpdate();
			  	  	  
		    		  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Entrega','2015/08/24',1,5,'8.00','//resources//image//produtos//entrega.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Banana','2015/08/24',2,1,'3.50','//resources//image//produtos//banana.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Limão Galego','2015/08/24',2,1,'3.50','//resources//image//produtos//limaogalego.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Couve Flor','2015/08/24',1,3,'2.00','//resources//image//produtos//couveflor.jpg');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Repolho Roxo','2015/08/24',1,3,'3.50','//resources//image//produtos//repolhoroxo.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Repolho Verde','2015/08/24',1,3,'3.50','//resources//image//produtos//repolho.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Beterraba Maço','2015/08/24',1,3,'3.50','//resources//image//produtos//beterrabamaco.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Cenoura Maço','2015/08/24',1,3,'3.50','//resources//image//produtos//cenouramaco.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Chuchu','2015/08/24',2,3,'3.50','//resources//image//produtos//chuchu.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Rabanete Maço','2015/08/24',1,3,'3.50','//resources//image//produtos//rabanetemaco.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Abóbora Cabotiá','2015/08/24',2,2,'3.50','//resources//image//produtos//abobora.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Batata Doce','2015/08/24',2,2,'3.50','//resources//image//produtos//batatadoce.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Batata Inglesa','2015/08/24',2,2,'3.50','//resources//image//produtos//batatainglesa.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Inhame','2015/08/24',2,2,'7.00','//resources//image//produtos//inhame.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Mandioca','2015/08/24',2,2,'3.50','//resources//image//produtos//mandioca.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Agrião','2015/08/24',1,4,'1.50','//resources//image//produtos//agriao.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Alface Americana','2015/08/24',1,4,'1.50','//resources//image//produtos//alfaceamericana.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Alface Crespa','2015/08/24',1,4,'1.50','//resources//image//produtos//alfacecrespa.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Alface Roxa','2015/08/24',1,4,'1.50','//resources//image//produtos//alfaceroxa.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Brocolis Ramoso','2015/08/24',1,4,'1.50','//resources//image//produtos//brocolisramoso.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Coentro','2015/08/24',1,4,'1.50','//resources//image//produtos//coentro.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Couve Manteiga','2015/08/24',1,4,'1.50','//resources//image//produtos//couvemanteiga.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Couve Mineira','2015/08/24',1,4,'1.50','//resources//image//produtos//couvemineira.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Espinafre','2015/08/24',1,4,'1.50','//resources//image//produtos//espinafre.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Hortelã','2015/08/24',1,4,'1.50','//resources//image//produtos//hortela.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Rúcula','2015/08/24',1,4,'1.50','//resources//image//produtos//rucula.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Salsinha','2015/08/24',1,4,'1.50','//resources//image//produtos//salsinha.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Cebolinha','2015/08/24',1,4,'1.50','//resources//image//produtos//cebolinha.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Acelga','2015/08/24',1,4,'3.50','//resources//image//produtos//acelga.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Alho Poró','2015/08/24',1,4,'3.00','//resources//image//produtos//alhoporo.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Alho Comum','2015/08/24',4,4,'5.50','//resources//image//produtos//alho.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Tomate','2015/08/24',2,1,'9.00','//resources//image//produtos//tomate.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Ovos','2015/08/24',3,5,'9.00','//resources//image//produtos//ovos.png');");query.executeUpdate();	  
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Pão Integral','2015/08/24',1,5,'9.00','//resources//image//produtos//paointegral.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Morango Bandeja','2015/08/24',1,1,'6.00','//resources//image//produtos//morango.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Sabonete Babosa com Capim Limão','2015/08/24',1,5,'5.00','//resources//image//produtos//sabonete.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Sabonete Argila Verde com Eucalipto','2015/08/24',1,5,'5.00','//resources//image//produtos//sabonete.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Sabonete Urucum com Bergamota','2015/08/24',1,5,'5.00','//resources//image//produtos//sabonete.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Sabonete Aveia Mel e Própolis','2015/08/24',1,5,'5.00','//resources//image//produtos//sabonete.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (status,nome,data,tipo_id,categoria_id,valor,foto) values ('ATIVO','Sabonete Alecrim','2015/08/24',1,5,'5.00','//resources//image//produtos//sabonete.png');");query.executeUpdate();
				em.getTransaction().commit();
	    }
	    
	    public List<Produto> createProdutos(int size) {
	        List<Produto> list = new ArrayList<Produto>(size);
	        
	        List<Tipo> tipoList = null;
	        List<Categoria> categoriaList = null;
	        
	        EntityManager em = JPA.getEM();
	    	TypedQuery<Tipo> queryTipos = em.createQuery("select t from Tipo t",
	    			Tipo.class);
	    	tipoList = queryTipos.getResultList();	
	        
	   
	    	TypedQuery<Categoria> queryCategorias = em.createQuery("select p from Categoria p",
	    			Categoria.class);
	    	categoriaList = queryCategorias.getResultList();	

	    	
	        for(int i = 0 ; i < size ; i++) {

	        	Produto produto = new Produto(getRandomNome(), getRandomDatas(), getRandomTipos(tipoList), getRandomValores(), getRandomCategorias(categoriaList));
	            list.add(produto);
	            
	    		em.getTransaction().begin();
	    		em.persist(produto);
	    		em.getTransaction().commit();
	        }
	        return list;
	    }
	    
	    public List<Produto> selectProdutos() {
	        List<Produto> list = null;
	        	
	        EntityManager em = JPA.getEM();
	    	TypedQuery<Produto> queryProdutos = em.createQuery("select p from Produto p",
	    					Produto.class);
	    	list = queryProdutos.getResultList();	
	    	return list;
	    }
	    
	   
	    private String getRandomNome() {
	        return nomes[(int) (Math.random() * 3)];
	    }
	     
	    private Tipo getRandomTipos(List<Tipo> tipoList) {
	    	return tipoList.get((int) (Math.random() * 2));
	    }
	    
	    private Categoria getRandomCategorias(List<Categoria> tipoList) {
	    	return tipoList.get((int) (Math.random() * 2));
	    }
	    
	    
	    private Date getRandomDatas() {
	        return datas[(int) (Math.random() * 3)];
	    }
	     
	    private Double getRandomValores() {
	        return valores[(int) (Math.random() * 6)];
	    }
}
