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
		    	Query query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Entrega','2015/08/24','','12.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Banana','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Bergamota','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Laranja Açucar','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Limão','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Couve Flor','2015/08/24','Uni.','1.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Repolho Roxo','2015/08/24','Uni.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Repolho Verde','2015/08/24','Uni.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Beterraba Maço','2015/08/24','Uni.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Beterraba','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Cenoura Maço','2015/08/24','Uni.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Cenoura','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Chuchu','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Rabanete','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Abóbora Cabotiá','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Batata Doce','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Batata Inglesa','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Inhame','2015/08/24','Kg.','5.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Mandioca','2015/08/24','Kg.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Agrião','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Alface Americana','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Alface Crespa','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Alface Roxa','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Brocolis','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Coentro','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Couve Manteiga','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Coyve Mineira','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Espinafre','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Hortelã','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Rúcula','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Salsinha','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Cebolinha','2015/08/24','Uni.','1.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Acelga','2015/08/24','Uni.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Alho Poró','2015/08/24','Uni.','2.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Alho','2015/08/24','Kg.','4.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Tomate','2015/08/24','Kg.','7.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Batata Salva Branca','2015/08/24','Kg.','4.80');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Batata Yacon','2015/08/24','Kg.','3.80');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Berinjela','2015/08/24','Kg.','5.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Cebola','2015/08/24','Kg.','5.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Limão Siciliano','2015/08/24','Kg.','6.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Abacate','2015/08/24','Kg.','5.80');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Abacaxi','2015/08/24','Kg.','7.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Caqui','2015/08/24','Kg.','5.20');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Kiwi','2015/08/24','Kg.','12.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Laranja Açucar','2015/08/24','Kg.','3.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Laranja Valencia','2015/08/24','Kg.','3.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Maça A','2015/08/24','Kg.','8.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Maça Verde','2015/08/24','Kg.','12.80');");query.executeUpdate();	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Manga','2015/08/24','Kg.','6.50');");query.executeUpdate();  	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Maracuja','2015/08/24','Kg.','8.00');");query.executeUpdate();	    	
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Melão','2015/08/24','Kg.','6.50');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Ovos','2015/08/24','Kg.','7.00');");query.executeUpdate();	  
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Pão Integral','2015/08/24','Uni.','9.00');");query.executeUpdate();
					  query = em.createNativeQuery("insert into produtos (nome,data,tipo,valor) values ('Sabonete','2015/08/24','Uni.','5.00');");query.executeUpdate();
		    	em.getTransaction().commit();
	    }
	    
	    public List<Produto> createProdutos(int size) {
	        List<Produto> list = new ArrayList<Produto>(size);
	        for(int i = 0 ; i < size ; i++) {

	        	Produto produto = new Produto(getRandomNome(), getRandomDatas(), getRandomTipos(), getRandomValores());
	            list.add(produto);
	            EntityManager em = JPA.getEM();
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
	     
	    private String getRandomTipos() {
	        return tipos[(int) (Math.random() * 2)];
	    }
	    
	    private Date getRandomDatas() {
	        return datas[(int) (Math.random() * 3)];
	    }
	     
	    private Double getRandomValores() {
	        return valores[(int) (Math.random() * 6)];
	    }
}
