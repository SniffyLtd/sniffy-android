package com.brand.applicationname.android.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.brand.applicationname.android.model.Product;

public class RemoteProductService {
	
	List<Product> testProducts = new ArrayList<Product>();
	
	public RemoteProductService(){
		Product product1 = new Product();
		product1.setId(1);
		product1.setLocal(false);
		product1.setName("¯ywiec zdrój woda mineralna 1.5l, œrednio gazowana.");
		product1.setDescription("Woda Ÿródlana wydobywana z ujêcia S2 w Rzeniszowie. Producent ¯ywiec Zdrój. Woda Ÿródlana œrednio nasycona dwutlenkiem wêgla.");
		testProducts.add(product1);
		Product product2 = new Product();
		product2.setId(1);
		product2.setLocal(false);
		product2.setName("BoboVita 100% owoców Mus z bananów brzoskwiñ i truskawek po 9 miesi¹cu 4 x 100 g.");
		product2.setDescription("¿ywienie z myœl¹ o przysz³oœci, zawiera naturalnie wystêpuj¹ce cukry z owoców, bez sztucznych barwników, dodatków aromatycznych i konserwantów - zgodnie z przepisami prawa, 100% owoców, zawiera tylko naturalne cukry z owoców, produkt bezglutenowy, nie zawiera bia³ka mleka krowiego i laktozy.");
		testProducts.add(product2);
		Product product3 = new Product();
		product3.setId(1);
		product3.setLocal(false);
		product3.setName("Dr. Oetker Galaretka o smaku agrestowym 77 g");
		product3.setDescription("Galaretka o smaku agrestowym. Pyszne, owocowe galaretki Dr. Oetkera to najbardziej elegancki deser. S¹ wspania³e jako dekoracja lodów, ciast z owocami i na zimno. Ich weso³e kolory, owocowy aromat oraz orzeŸwiaj¹cy smak sprawiaj¹, ¿e s¹ wspania³ym pomys³em na letnie przyjêcia i dzieciêce bale. Je¿eli masz ochotê na coœ oryginalnego i nowego - Dr. Oetker poleca specjalne przepisy na galaretkowe drinki i inne szalone desery.");
		testProducts.add(product3);
		Product product4 = new Product();
		product4.setId(1);
		product4.setLocal(false);
		product4.setName("Milka Lila Stars Snax Hazelnuts and Crispy Orzechy laskowe w czekoladzie z dodatkiem chrupek 60 g");
		product4.setDescription("Orzechy laskowe (16%) w mlecznej czekoladzie z mleka alpejskiego i bia³ej czekoladzie z dodatkiem chrupek pszenno-ry¿owych (4,5%).");
		testProducts.add(product4);
	}

	public Product findProduct(String barecode) {
		Random rand = new Random();
		int index = rand.nextInt(4);
		if(index == 4){
			return null;
		}
		Product product = testProducts.get(index);
		product.setBarecode(barecode);
		return product;
	}

}
