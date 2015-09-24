package eventos;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import handlers.PedidoBean;

@ManagedBean
public class PedidoEvento {

	
    @ManagedProperty("#{pedidoBean}")
    private PedidoBean pedidoBean;
		
    @PostConstruct
    public void init() {
    	
    }
    
	public void verifica(ActionEvent e) {
		if (pedidoBean.getPedido()!= null){
			if (pedidoBean.getPedido().getStatus().equals("AGUARDANDO")){
				UIComponent opcao1 = e.getComponent().findComponent("botaoPedido");
				opcao1.getAttributes().put("value", "ATUALIZAR PEDIDO");
				opcao1.getAttributes().put("disabled", false);
				opcao1.getAttributes().put("styleClass", "btn btn-primary");
			}
			else if (pedidoBean.getPedido().getStatus().equals("CONFIRMADO")){
				UIComponent opcao1 = e.getComponent().findComponent("botaoPedido");
				opcao1.getAttributes().put("value", "?");
				opcao1.getAttributes().put("disabled", true);
				opcao1.getAttributes().put("disabled", true);
				opcao1.getAttributes().put("styleClass", "btn btn-btn-info");
			}
			else {
				UIComponent opcao1 = e.getComponent().findComponent("botaoPedido");
				opcao1.getAttributes().put("value", "CONFIRMAR PEDIDO");
				opcao1.getAttributes().put("disabled", false);
				opcao1.getAttributes().put("styleClass", "btn-lg btn-default");
			}
		}
	}
}