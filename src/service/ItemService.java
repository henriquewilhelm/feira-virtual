package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;

import util.JPA;
import models.Item;
import models.Produto;
	 
@ManagedBean(name = "itemService")
@ApplicationScoped
public class ItemService implements Serializable{
	     
	/**
	 * 
	 */
	private static final long serialVersionUID = -3418441511903269362L;

	@ManagedProperty("#{produtoService}")
	private ProdutoService produtoService;
	
	private List<Produto> produtos;
	
		private final static Integer[] quantidade;
	    
	    static {
	        
	        quantidade = new Integer[6];
	        quantidade[0] = 1;
	        quantidade[1] = 2;
	        quantidade[2] = 3;
	        quantidade[3] = 4;
	        quantidade[4] = 5;
	        quantidade[5] = 1;
	    }
	     
	    public List<Item> createItens(int size) {
	    	produtoService = new ProdutoService();
	    	produtos = produtoService.selectProdutos();
	        List<Item> list = new ArrayList<Item>(size);
	        for(int i = 0 ; i < size ; i++) {
	        	Item item = new Item(getRandomQuantidade(), produtos.get((int) (Math.random() * produtos.size())));
	        	EntityManager em = JPA.getEM();
	    		em.getTransaction().begin();
	    		em.persist(item);
	    		em.getTransaction().commit();
	
	        	list.add(item);
	        }
	         
	        return list;
	    }
	    
	    private Integer getRandomQuantidade() {
	        return quantidade[(int) (Math.random() * 6)];
	    }
	    
	    public List<Produto> getRandomListProdutos() {
	    	List<Produto> listProduto =  new ProdutoService().createProdutos(3); 
	    	for (int i=0; i< listProduto.size(); i++){
	    		EntityManager em = JPA.getEM();
	    		em.getTransaction().begin();
	    		em.persist(listProduto.get(i));
	    		em.getTransaction().commit();
	    	}
	    	
	    	return listProduto;
	    }
}
