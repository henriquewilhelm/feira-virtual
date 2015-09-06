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
		    	Query query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Uni.',1,5);");query.executeUpdate();
				  	  query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Kg.',0.5,3);");query.executeUpdate();
				  	  query = em.createNativeQuery("insert into tipos (nome,minimo,maximo) values ('Maço',1,5);");query.executeUpdate();
		    	
		    		  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Entrega','2015/08/24',1,'12.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Banana','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Bergamota','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Laranja Açucar','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Limão','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Couve Flor','2015/08/24',2,'1.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Repolho Roxo','2015/08/24',2,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Repolho Verde','2015/08/24',2,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Beterraba Maço','2015/08/24',2,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Beterraba','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Cenoura Maço','2015/08/24',2,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Cenoura','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Chuchu','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Rabanete','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Abóbora Cabotiá','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Batata Doce','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Batata Inglesa','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Inhame','2015/08/24',1,'5.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Mandioca','2015/08/24',1,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Agrião','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Alface Americana','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Alface Crespa','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Alface Roxa','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Brocolis','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Coentro','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Couve Manteiga','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Coyve Mineira','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Espinafre','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Hortelã','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Rúcula','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Salsinha','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Cebolinha','2015/08/24',2,'1.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Acelga','2015/08/24',2,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Alho Poró','2015/08/24',2,'2.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Alho','2015/08/24',1,'4.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Tomate','2015/08/24',1,'7.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Batata Salva Branca','2015/08/24',1,'4.80','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Batata Yacon','2015/08/24',1,'3.80','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Berinjela','2015/08/24',1,'5.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Cebola','2015/08/24',1,'5.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Limão Siciliano','2015/08/24',1,'6.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Abacate','2015/08/24',1,'5.80','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Abacaxi','2015/08/24',1,'7.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Caqui','2015/08/24',1,'5.20','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Kiwi','2015/08/24',1,'12.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Laranja Açucar','2015/08/24',1,'3.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Laranja Valencia','2015/08/24',1,'3.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Maça A','2015/08/24',1,'8.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Maça Verde','2015/08/24',1,'12.80','//resources//image//produtos//produto.png');");query.executeUpdate();	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Manga','2015/08/24',1,'6.50','//resources//image//produtos//produto.png');");query.executeUpdate();  	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Maracuja','2015/08/24',1,'8.00','//resources//image//produtos//produto.png');");query.executeUpdate();	    	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Melão','2015/08/24',1,'6.50','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Ovos','2015/08/24',1,'7.00','//resources//image//produtos//produto.png');");query.executeUpdate();	  
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Pão Integral','2015/08/24',2,'9.00','//resources//image//produtos//produto.png');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor,foto) values ('Sabonete','2015/08/24',2,'5.00','//resources//image//produtos//produto.png');");query.executeUpdate();
		    	em.getTransaction().commit();
	    }
	    
	    public List<Produto> createProdutos(int size) {
	        List<Produto> list = new ArrayList<Produto>(size);
	        
	        List<Tipo> tipoList = null;
        	
	        EntityManager em = JPA.getEM();
	    	TypedQuery<Tipo> queryTipos = em.createQuery("select t from Tipo t",
	    			Tipo.class);
	    	tipoList = queryTipos.getResultList();	
	        
	        for(int i = 0 ; i < size ; i++) {

	        	Produto produto = new Produto(getRandomNome(), getRandomDatas(), getRandomTipos(tipoList), getRandomValores());
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
	    
	    private Date getRandomDatas() {
	        return datas[(int) (Math.random() * 3)];
	    }
	     
	    private Double getRandomValores() {
	        return valores[(int) (Math.random() * 6)];
	    }
}
