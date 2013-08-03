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
		product1.setName("�ywiec zdr�j woda mineralna 1.5l, �rednio gazowana.");
		product1.setDescription("Woda �r�dlana wydobywana z uj�cia S2 w Rzeniszowie. Producent �ywiec Zdr�j. Woda �r�dlana �rednio nasycona dwutlenkiem w�gla.");
		testProducts.add(product1);
		Product product2 = new Product();
		product2.setId(1);
		product2.setLocal(false);
		product2.setName("BoboVita 100% owoc�w Mus z banan�w brzoskwi� i truskawek po 9 miesi�cu 4 x 100 g.");
		product2.setDescription("�ywienie z my�l� o przysz�o�ci, zawiera naturalnie wyst�puj�ce cukry z owoc�w, bez sztucznych barwnik�w, dodatk�w aromatycznych i konserwant�w - zgodnie z przepisami prawa, 100% owoc�w, zawiera tylko naturalne cukry z owoc�w, produkt bezglutenowy, nie zawiera bia�ka mleka krowiego i laktozy.");
		testProducts.add(product2);
		Product product3 = new Product();
		product3.setId(1);
		product3.setLocal(false);
		product3.setName("Dr. Oetker Galaretka o smaku agrestowym 77 g");
		product3.setDescription("Galaretka o smaku agrestowym. Pyszne, owocowe galaretki Dr. Oetkera to najbardziej elegancki deser. S� wspania�e jako dekoracja lod�w, ciast z owocami i na zimno. Ich weso�e kolory, owocowy aromat oraz orze�wiaj�cy smak sprawiaj�, �e s� wspania�ym pomys�em na letnie przyj�cia i dzieci�ce bale. Je�eli masz ochot� na co� oryginalnego i nowego - Dr. Oetker poleca specjalne przepisy na galaretkowe drinki i inne szalone desery.");
		testProducts.add(product3);
		Product product4 = new Product();
		product4.setId(1);
		product4.setLocal(false);
		product4.setName("Milka Lila Stars Snax Hazelnuts and Crispy Orzechy laskowe w czekoladzie z dodatkiem chrupek 60 g");
		product4.setDescription("Orzechy laskowe (16%) w mlecznej czekoladzie z mleka alpejskiego i bia�ej czekoladzie z dodatkiem chrupek pszenno-ry�owych (4,5%).");
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
