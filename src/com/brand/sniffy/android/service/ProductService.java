package com.brand.sniffy.android.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.service.SearchProductResult.ResultStatus;

public class ProductService {
	
	private Context context;
	
	public ProductService(Context context){
		this.context = context;
	}

	private LocalProductService localProductService;
	
	private RemoteProductService remoteProductService;
	
	public Product findProduct(String barcode) {
		
		Product product = getLocalProductService().findProduct(barcode);
		if(product == null || product.isLocal()){
			Log.i(this.getClass().getName(), "Product not present in local storage. Trying to search online.");
			SearchProductResult result = getRemoteProductService().findProduct(barcode);
			if(result.getStatus() == ResultStatus.OK){
				product = result.getResult();
				getLocalProductService().cache(product);
			}
			else{
				String errorMessage = null;
				switch(result.getStatus()){
				case INVALID_RESULT:
					errorMessage = "Data returned from back office is not valid.";
					break;
				case OFFLINE:
					errorMessage = "Don't have internet connection.";
					break;
				case SERVER_ERROR:
					errorMessage = "Back office service error.";
					break;
				case NOT_FOUND:

					errorMessage = "Product not found.";
						//Toast.makeText(context, "not found",Toast.LENGTH_SHORT).show();
				default:
					break;
				
				}
				//Toast.makeText(context, errorMessage,Toast.LENGTH_SHORT).show();
				Log.e(this.getClass().getName(), errorMessage);
			}
		}
		return product;
	}

	public LocalProductService getLocalProductService() {
		if(localProductService == null){
			localProductService = new LocalProductService(context);
		}
		
		return localProductService;
	}

	public RemoteProductService getRemoteProductService() {
		if(remoteProductService == null){
			remoteProductService = new RemoteProductService(context);
		}
		
		return remoteProductService;
	}

	public Product createLocalProduct(String baredoce) {
		return getLocalProductService().createProduct(baredoce);
	}

}
