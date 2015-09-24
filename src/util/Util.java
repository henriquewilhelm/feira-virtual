package util;

import java.util.ArrayList;
import java.util.List;

import models.StatusPedido;

public class Util {
	public static List<StatusPedido> getListStatusPedido() {

		 List<StatusPedido> list = new ArrayList<StatusPedido>();
		 
		 list.add(StatusPedido.EMANDAMENTO);
		 list.add(StatusPedido.CONFIRMADO);
		 list.add(StatusPedido.ENTREGUE);
		 list.add(StatusPedido.CANCELADO);
		 return list;
	}
}
