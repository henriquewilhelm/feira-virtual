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
		    	Query query = em.createNativeQuery("insert into tipos (nome) values ('Uni.');");query.executeUpdate();
				  	  query = em.createNativeQuery("insert into tipos (nome) values ('Kg.');");query.executeUpdate();
				  	  query = em.createNativeQuery("insert into tipos (nome) values ('Maço');");query.executeUpdate();
		    	
		    		  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Entrega','2015/08/24',1,'12.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Banana','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Bergamota','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Laranja Açucar','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Limão','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Couve Flor','2015/08/24',2,'1.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Repolho Roxo','2015/08/24',2,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Repolho Verde','2015/08/24',2,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Beterraba Maço','2015/08/24',2,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Beterraba','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Cenoura Maço','2015/08/24',2,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Cenoura','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Chuchu','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Rabanete','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Abóbora Cabotiá','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Batata Doce','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Batata Inglesa','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Inhame','2015/08/24',1,'5.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Mandioca','2015/08/24',1,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Agrião','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Alface Americana','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Alface Crespa','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Alface Roxa','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Brocolis','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Coentro','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Couve Manteiga','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Coyve Mineira','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Espinafre','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Hortelã','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Rúcula','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Salsinha','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Cebolinha','2015/08/24',2,'1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Acelga','2015/08/24',2,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Alho Poró','2015/08/24',2,'2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Alho','2015/08/24',1,'4.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Tomate','2015/08/24',1,'7.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Batata Salva Branca','2015/08/24',1,'4.80');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Batata Yacon','2015/08/24',1,'3.80');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Berinjela','2015/08/24',1,'5.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Cebola','2015/08/24',1,'5.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Limão Siciliano','2015/08/24',1,'6.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Abacate','2015/08/24',1,'5.80');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Abacaxi','2015/08/24',1,'7.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Caqui','2015/08/24',1,'5.20');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Kiwi','2015/08/24',1,'12.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Laranja Açucar','2015/08/24',1,'3.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Laranja Valencia','2015/08/24',1,'3.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Maça A','2015/08/24',1,'8.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Maça Verde','2015/08/24',1,'12.80');");query.executeUpdate();	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Manga','2015/08/24',1,'6.50');");query.executeUpdate();  	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Maracuja','2015/08/24',1,'8.00');");query.executeUpdate();	    	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Melão','2015/08/24',1,'6.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Ovos','2015/08/24',1,'7.00');");query.executeUpdate();	  
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Pão Integral','2015/08/24',2,'9.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo_id,valor) values ('Sabonete','2015/08/24',2,'5.00');");query.executeUpdate();
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
