package handlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import models.Bairro;
import models.Categoria;
import models.Cidade;
import models.Item;
import models.Mail;
import models.Produto;
import models.Pedido;
import models.ProdutoEspecial;
import models.StatusPedido;
import models.Tipo;
import models.UserTipo;
import models.Usuario;
import util.JPA;

@ManagedBean
@SessionScoped
public class PedidoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4200623463565962506L;
	
	private Tipo tipo;
	private Produto produtoSelecionado;
	private Item itemSelecionado;
	private Pedido pedido;
	private Item item;
	private ProdutoEspecial produtoEspecialSelecionado;
	
	private List<Categoria> categorias;
	private Categoria categoria;
	private ArrayList<ProdutoEspecial> listProdutoEspecial;
	
	private Usuario usuario;
	private List<Pedido> pedidosPorUsuario;
	private Mail mail;
	private Cidade cidade;
	private Bairro bairro;
	private Double quantidade;
	private List<Double> quantidades;
	private Integer quantidadeItens = 0;
	
	private StatusPedido statusPedido = StatusPedido.AGUARDANDO;
	private StatusPedido statusPedidoPara;
	
	public PedidoBean() {
		pedido = new Pedido();
		item = new Item();
		quantidade = item.getQuantidade();
		
        usuario =  SessionBean.getUser();
        pedido.setUsuario(usuario);
        cidade = usuario.getCidade();
		bairro = usuario.getBairro();	
		
		carregaCesta(pedido);
	} 
	
	public void carregaCesta(Pedido pedido){
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> queryEntrega = em.createQuery("Select c from Produto c WHERE c.nome = :nome",
				Produto.class);
		queryEntrega.setParameter("nome", "Entrega");
		Produto entrega = queryEntrega.getSingleResult();
		
		pedido.getListItens().add(new Item(1d, entrega));
		pedido.setTotal(entrega.getValor());
	}
	
	public void addQntdProdutoEspecial(ActionEvent event){
		ProdutoEspecial produtoEspecial = (ProdutoEspecial) event.getComponent().getAttributes().get("selected");
		produtoSelecionado = produtoEspecial.getProduto();
		if (getProdutoEspecialSelecionado()!=null){
			if (getProdutoEspecialSelecionado().getProduto().getId() == produtoEspecial.getProduto().getId()){
				
			}
			else 
				setProdutoEspecialSelecionado(produtoEspecial);
		}
		else 
			setProdutoEspecialSelecionado(produtoEspecial);
		
		getProdutoEspecialSelecionado().setQuantidade(getProdutoEspecialSelecionado().getQuantidade() + produtoSelecionado.getTipo().getMinimo());
		quantidade = getProdutoEspecialSelecionado().getQuantidade();
		System.out.println("Adicionando Qntd do Produto: "+ produtoEspecial.getProduto().getNome()+" Total: "+quantidade);
		addProduto();
	}
	
	public void removeQntdProdutoEspecial(ActionEvent event){
		ProdutoEspecial produtoEspecial = (ProdutoEspecial) event.getComponent().getAttributes().get("selected");
		produtoSelecionado = produtoEspecial.getProduto();
		if (getProdutoEspecialSelecionado()!=null){
			if (getProdutoEspecialSelecionado().getProduto().getId() == produtoEspecial.getProduto().getId()){
				
			}
			else {
				for (int i=0; i<pedido.getListItens().size(); i++){
					if (pedido.getListItens().get(i).getProduto().getId() == produtoEspecial.getProduto().getId()){	
						setProdutoEspecialSelecionado(produtoEspecial);
						getProdutoEspecialSelecionado().setQuantidade(pedido.getListItens().get(i).getQuantidade());
					}
				}
			}
		}
		else 
			setProdutoEspecialSelecionado(produtoEspecial);
		
		getProdutoEspecialSelecionado().setQuantidade(getProdutoEspecialSelecionado().getQuantidade() - produtoSelecionado.getTipo().getMinimo());
		quantidade = getProdutoEspecialSelecionado().getQuantidade();
		if (quantidade<=0){
			removeProduto(produtoSelecionado);
			quantidade = 0d;
		}
		else{
			System.out.println("Removendo Qntd do Produto"+produtoEspecial.getProduto().getNome()+" Total: "+quantidade);
			addProduto();
		}
	}
	
	public void addProduto(){
		boolean verificacao = true;
				if (produtoSelecionado !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getProduto().getId() == produtoSelecionado.getId()){					
							pedido.setTotal(pedido.getTotal() - pedido.getListItens().get(i).getTotal() + (getQuantidade() * pedido.getListItens().get(i).getProduto().getValor()) );
							pedido.getListItens().get(i).setQuantidade(getQuantidade());
							EntityManager em = JPA.getEM();
							em.getTransaction().begin();
							em.merge(pedido.getListItens().get(i));
							em.getTransaction().commit();
							
							verificacao=false;
						}
					}
					if (verificacao){
						item.setProduto(produtoSelecionado);
						if (getQuantidade()!=null)
							item.setQuantidade(getQuantidade());
						
						pedido.getListItens().add(item);
						pedido.setTotal(pedido.getTotal() + item.getTotal());
						
						EntityManager em = JPA.getEM();
						em.getTransaction().begin();
						em.persist(item);
						em.getTransaction().commit();
						
						System.out.println("Add produto "+item.getId()+ " - " + produtoSelecionado.getNome()+" no Pedido "+getPedido().getNome());
						
						item = new Item();
						quantidade = item.getQuantidade();

						this.quantidadeItens++;
					}
					else{
						if (!produtoSelecionado.getNome().equals("Entrega")){
							FacesContext facesContext = FacesContext.getCurrentInstance(); 
							
							facesContext.addMessage(null, new FacesMessage( 
				            FacesMessage.SEVERITY_INFO, "O produto só deve ser cadastrado uma vez, sua quantidade será alterada...", null));
							System.out.println("Nao add, produto ja cadastrado...");
						}
					}
				}
				else
					System.out.println("Nao Add - Produto nao selecionado...");
	}
	

	public void removeProduto(Produto produto){
		boolean verificacao = true;
		 		Item itemSelecionado = null;
				if (produto !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getProduto().getId() == produto.getId() || 
								pedido.getListItens().get(i).getProduto().getNome().equals("Entrega")){
										verificacao=false;
										itemSelecionado = pedido.getListItens().get(i);
						}
					}
					if (!verificacao){
						pedido.setTotal(pedido.getTotal() - itemSelecionado.getTotal());
						
						pedido.getListItens().remove(itemSelecionado);
						System.out.println("Remove produto "+itemSelecionado.getProduto().getNome()+" do Pedido "+getPedido().getNome());
						this.quantidadeItens--;
					}
					else{
						FacesContext facesContext = FacesContext.getCurrentInstance(); 

						facesContext.addMessage(null, new FacesMessage( 
			            FacesMessage.SEVERITY_ERROR, "Ops, erro ao remover produto, tente outra vez...", null));
					
					}
				}
				else
					System.out.println("Nao remove - Produto nao selecionado...");		
	}
	
	public void removeProduto(){
		boolean verificacao = true;
				if (itemSelecionado !=null){
					for (int i=0; i<pedido.getListItens().size(); i++){
						if (pedido.getListItens().get(i).getId() == itemSelecionado.getId() || 
								pedido.getListItens().get(i).getProduto().getNome().equals("Entrega"))
							verificacao=false;
					}
					if (!verificacao){
						pedido.setTotal(pedido.getTotal() - itemSelecionado.getTotal());
						
						pedido.getListItens().remove(getItemSelecionado());
						System.out.println("Remove Produto "+itemSelecionado.getProduto().getNome()+" no Pedido "+getPedido().getNome());
						this.quantidadeItens--;
					}
					else{
						FacesContext facesContext = FacesContext.getCurrentInstance(); 

						facesContext.addMessage(null, new FacesMessage( 
			            FacesMessage.SEVERITY_ERROR, "Ops, erro ao remover produto, tente outra vez...", null));
					
					}
				}
				else
					System.out.println("Nao remove - Produto nao selecionado...");			
	}
	
	public String addPedido() {
		System.out.println("Add Pedido "+pedido.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(pedido);
		em.getTransaction().commit();
//		setPedido(new Pedido());
		
		usuario =  SessionBean.getUser();
        pedido.setUsuario(usuario);
        cidade = usuario.getCidade();
		bairro = usuario.getBairro();	        
        
		mail = new Mail();
        mail.setAssunto("LNB - Pedido realizado");
		mail.setDestino(pedido.getUsuario().getEmail());
		
		String msg = "Olá "+pedido.getNome()+", seu pedido foi realizado com sucesso!\n\nQuantidade de Produtos: "+pedido.getListItens().size() +" \n";
		for (int i = 0; i< pedido.getListItens().size(); i++){
			msg = msg + " " + pedido.getListItens().get(i).getProduto().getNome() + " - " + pedido.getListItens().get(i).getQuantidade() + "x " + pedido.getListItens().get(i).getProduto().getTipo() + " = " + pedido.getListItens().get(i).getTotal()+ "\n";
		}
		msg = msg + "Até a entrega, facilete o troco! Qualquer dúvida entraremos em contato!";
		mail.setMsg(msg);
		mail.setNomeDestino(pedido.getNome());
		mail.sendMail();
        
		//setPedido(new Pedido());
		if (usuario.getTipo().equals(UserTipo.ADMIN))
			return "/gerenciador/pedido/listar";
		else
			return "/loja/pedido/listar";
	}
	
	public String clearPedidos() {
		System.out.println("Clear All");
		getPedido().setObs("");
		getPedido().getListItens().clear();
		getProdutosCadastrados();
		carregaCesta(pedido);

		return "";
	}
	
	public void  deletePedido(ActionEvent event){
		Pedido selected = (Pedido) event.getComponent().getAttributes().get("selected");
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			for (int j=0; j<selected.getListItens().size(); j++){
				
				//em.remove(selected.getListItens().get(j).getProduto());
				selected.getListItens().get(j).setProduto(null);
				em.remove(selected.getListItens().get(j));
				selected.getListItens().remove(j);
			}
			selected.setListItens(null);
			selected.setUsuario(null);
			em.remove(selected);
		em.getTransaction().commit();
		
		System.out.println("Delete Pedido "+ selected.getNome());
	}
	
	public void deleteProduto(ActionEvent event){
		Pedido selected = (Pedido) event.getComponent().getAttributes().get("selected");

		boolean aux = selected.getListItens().remove(getItemSelecionado());
		if (aux){
			System.out.println("Deletando do "+selected.getNome()+" o "
					+ "Produto Selecionado "+getItemSelecionado().getProduto().getNome()+" !");
			EntityManager em = JPA.getEM();
			em.getTransaction().begin();
			em.persist(selected);
			em.getTransaction().commit();
		}
		else
			System.out.println("Nao Deletou Produto do Pedido: "+selected.getNome());
	}

	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProdutoSelecionado() {
		if (produtoSelecionado!=null){	
			System.out.println("*** ProdutoSelecionado: "+produtoSelecionado.getNome());
		}
		return produtoSelecionado;
	}
	
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;

	}

	public Item getItemSelecionado() {
		if (itemSelecionado!=null)
			System.out.println("*** ItemSelecionado: "+itemSelecionado.getId());
		return itemSelecionado;
	}

	public void setItemSelecionado(Item itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public Double getQuantidade() {

		System.out.println("get" +quantidade);
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {

		System.out.println("set" +quantidade);
		this.quantidade = quantidade;
	}
	

	public void quantidadeChanged(ValueChangeEvent e){
		//assign new value to localeCode
		quantidade = (Double) e.getNewValue();
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}
	

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
		getBairros();
	}
	
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public Integer getQuantidadeItens() {
		return quantidadeItens;
	}

	public void setQuantidadeItens(Integer quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
	}
	
	public List<Double> getQuantidades() {
		return this.quantidades;
	}
	
	public void setQuantidades(List<Double> quantidades) {
		this.quantidades = quantidades;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}
	
	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}
	
	public StatusPedido getStatusPedidoPara() {
		return statusPedidoPara;
	}

	public void setStatusPedidoPara(StatusPedido statusPedidoPara) {
		this.statusPedidoPara = statusPedidoPara;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public ProdutoEspecial getProdutoEspecialSelecionado() {
		return produtoEspecialSelecionado;
	}

	public void setProdutoEspecialSelecionado(
			ProdutoEspecial produtoEspecialSelecionado) {
		this.produtoEspecialSelecionado = produtoEspecialSelecionado;
	}

	public String proximaCategoria () {
		
		 
		Integer quantidadeCategorias = categorias.size();
		
		if (categoria.getId()+1 <= quantidadeCategorias)
			categoria = categorias.get(categoria.getId());
		else
			categoria = categorias.get(0);
		return "/loja/pedido/listarProdutos.xhtml";
	}
	
	public List<Categoria> getCategorias() {

		EntityManager em = JPA.getEM();
		TypedQuery<Categoria> query = em.createQuery("Select c from Categoria c",
				Categoria.class);
		return query.getResultList();
	}
	
	public List<ProdutoEspecial> getProdutosPorCategoria() {

		
		categorias = getCategorias();
		
		listProdutoEspecial = new ArrayList<ProdutoEspecial>();
		
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select p from Produto p left join fetch p.categoria c where c.id = :id",
				Produto.class);
		if (categoria!=null)
			query.setParameter("id", categoria.getId());
		
		List<Produto> listProduto = query.getResultList();
		ArrayList<Double> quantidades = new ArrayList<Double>();
		
		for (int i= 0; i< listProduto.size(); i++){
			
			Double minimo = 1d;
			Double maximo = 1d;
			Double de = 0.5d;
 		
			if (listProduto.get(i)!=null){
				
				Query queryMinMax = em.createQuery("select t.minimo,t.maximo from Tipo t where t.id = :id");
				queryMinMax.setParameter("id", listProduto.get(i).getTipo().getId());
				List<Object[]> rows = queryMinMax.getResultList();
				
				for (Object[] row: rows) {
				    minimo = (Double) row[0];
				    maximo = (Double) row[1];
				}
				
				if (minimo < 1){
					quantidades = new ArrayList<Double>();
					for (Double contador = minimo; contador <= maximo;){
						quantidades.add(contador);
						contador = contador + de;
					}
				}
				else{
					quantidades = new ArrayList<Double>();
					for (Double contador = minimo; contador <= maximo; contador++){
						quantidades.add(contador);
					}
				}
			}
			
			listProdutoEspecial.add(new ProdutoEspecial(listProduto.get(i), 0d, quantidades));
		}
		return listProdutoEspecial;
	}
	
	public List<Produto> getProdutosCadastrados() {
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select c from Produto c",
				Produto.class);
		List<Produto> listProdutos = (List<Produto>) query.getResultList();
		
		TypedQuery<Produto> queryEntrega = em.createQuery("Select c from Produto c WHERE c.nome = :nome",
				Produto.class);
		queryEntrega.setParameter("nome", "Entrega");
		Produto entrega = queryEntrega.getSingleResult();
		
		listProdutos.remove(entrega);
		
		return listProdutos;
	}
	
	public List<Pedido> getPedidos() {
		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query;
		if (getStatusPedido()!=null){
			query = em.createQuery("Select p from Pedido p WHERE p.status = :status", Pedido.class);
			query.setParameter("status", getStatusPedido());
		}
		else {
			query = em.createQuery("Select p from Pedido p",	Pedido.class);
		}
		return query.getResultList();
	}
	
	public List<Cidade> getCidades() {

		EntityManager em = JPA.getEM();
		TypedQuery<Cidade> query = em.createQuery("Select c from Cidade c",
				Cidade.class);
		
		return query.getResultList();
	}
	
	public List<Bairro> getBairros() {
		try{ 				
			EntityManager em = JPA.getEM();
			TypedQuery<Bairro> query = em.createQuery("Select b from Bairro b where b.cidade.id = :id",
					Bairro.class);
			query.setParameter("id", cidade.getId());
			
			return query.getResultList();
		
		}catch (Exception e){
			return new ArrayList<Bairro>();
		}
	}
	
	public List<Tipo> getTipos() {
		Double minimo = 1d;
		Double maximo = 1d;
		Double de = 0.5d;
//		try{ 			
		if (getProdutoSelecionado()!=null){
			EntityManager em = JPA.getEM();
			TypedQuery<Tipo> query = em.createQuery("Select t from Tipo t where t.id = :id",
					Tipo.class);
			query.setParameter("id", getProdutoSelecionado().getTipo().getId());
			
			Query queryMinMax = em.createQuery("select t.minimo,t.maximo from Tipo t where t.id = :id");
			queryMinMax.setParameter("id", getProdutoSelecionado().getTipo().getId());
			List<Object[]> rows = queryMinMax.getResultList();

			for (Object[] row: rows) {
			    minimo = (Double) row[0];
			    maximo = (Double) row[1];
			}
			if (minimo < 1){
				quantidades = new ArrayList<Double>();
				for (Double contador = minimo; contador <= maximo;){
					quantidades.add(contador);
					contador = contador + de;
				}
			}
			else{
				quantidades = new ArrayList<Double>();
				for (Double contador = minimo; contador <= maximo; contador++){
					quantidades.add(contador);
				}
			}
			return query.getResultList();
		}
		else 
			return new ArrayList<Tipo>();
//		}catch (Exception e){
//			return new ArrayList<Tipo>();
//		}
	}
	
	public Usuario getUsuarioPorEmail(String email) {
		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email",
				Usuario.class);
		query.setParameter("email", email);
		Usuario usuario = query.getSingleResult();
		return usuario;
	}
	
	public List<Pedido> getPedidosPorUsuario() {

		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query = em.createQuery("Select p from Pedido p left join fetch p.usuario u where u.email = :email",
				Pedido.class);
		query.setParameter("email", SessionBean.getUser().getEmail());
		pedidosPorUsuario = query.getResultList();
		return pedidosPorUsuario;
	}
	
	public List<StatusPedido> getListStatusPedido() {

		 List<StatusPedido> list = new ArrayList<StatusPedido>();
		 list.add(StatusPedido.AGUARDANDO);
		 list.add(StatusPedido.CONFIRMADO);
		 list.add(StatusPedido.ENVIADO);
		 list.add(StatusPedido.CANCELADO);
		 return list;
	}
	
	public void mudaStatus(AjaxBehaviorEvent abe){
		System.out.println("mudaStatus");
		List<Pedido> pedidos = getPedidos();
		for (int i=0; i< pedidos.size(); i++){
			pedidos.get(i).setStatus(getStatusPedidoPara());
			EntityManager em = JPA.getEM();
			em.getTransaction().begin();
			em.merge(pedidos.get(i));
			em.getTransaction().commit();
		}
	} 
	
	public String list() {
		return "/gerenciador/pedido/listar";
	}

    public void onRowEdit(RowEditEvent event) {
    	ProdutoEspecial produtoEspecial = ((ProdutoEspecial) event.getObject());
        FacesMessage msg = new FacesMessage("Produto Adicionado", produtoEspecial.getProduto().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        produtoSelecionado = produtoEspecial.getProduto();
		System.out.println("Add: "+produtoEspecial.getProduto().getNome()+" "+getQuantidade());
		addProduto();
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Adicao Cancelada", ((ProdutoEspecial) event.getObject()).getProduto().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void sendMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Successful",  "Your message ") );
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}