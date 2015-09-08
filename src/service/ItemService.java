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
	
		private final static Double[] quantidade;
	    
	    static {
	        
	        quantidade = new Double[6];
	        quantidade[0] = 1d;
	        quantidade[1] = 2d;
	        quantidade[2] = 3d;
	        quantidade[3] = 4d;
	        quantidade[4] = 5d;
	        quantidade[5] = 1d;
	    }

	    public List<Item> createItens(int size) {
	    	produtoService = new ProdutoService();
	    	produtos = produtoService.selectProdutos();
	        List<Item> list = new ArrayList<Item>(size);
	        list.add(new Item(1d, produtos.get(0)));
	       
		    for(int i = 0 ; i < size ; i++) {
		    	 	int contador = 0;
		    		Produto produto = produtos.get( (int) (Math.random() * produtos.size()) );
		        	while ( contador < list.size() ){
		        		if (produto.getId() != list.get(contador).getProduto().getId())
		        			contador++;
		        		else {
		        			produto = produtos.get( (int) (Math.random() * produtos.size()) );
		        			contador = 0;
		        		}
		        	}
		        	Item item = new Item(getRandomQuantidade(), produto);
		        	
		        	
		        	EntityManager em = JPA.getEM();
		    		em.getTransaction().begin();
		    		em.persist(item);
		    		em.getTransaction().commit();
		    		
		        	list.add(item);
		     }
		       
	    	
	        return list;
	    }
	    
	    private Double getRandomQuantidade() {
	        return quantidade[(int) (Math.random() * 6d)];
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
