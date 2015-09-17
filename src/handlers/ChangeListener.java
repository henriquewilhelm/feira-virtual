package handlers;

	import javax.faces.context.FacesContext;
	import javax.faces.event.AbortProcessingException;
	import javax.faces.event.ValueChangeEvent;
	import javax.faces.event.ValueChangeListener;
	 
	public class ChangeListener implements ValueChangeListener{

		@Override
		public void processValueChange(ValueChangeEvent event)
				throws AbortProcessingException {
			
			//access country bean directly
			PedidoBean pedidoBean = (PedidoBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("pedidoBean");

			pedidoBean.setQuantidade((Double) event.getNewValue());
			System.out.println("pedidoBena"+pedidoBean.getQuantidade());
		}
//		<h:selectOneMenu value="#{produtoEspecial.quantidade}" onchange="submit()">
//			<f:valueChangeListener type="handlers.ChangeListener" />
//			  <f:selectItem itemLabel="Adicione" />
//        <f:selectItems value="#{produtoEspecial.quantidades}"  var="quantidade" itemValue="#{quantidade}" itemLabel="#{quantidade}"/>
//    </h:selectOneMenu>	
	}
