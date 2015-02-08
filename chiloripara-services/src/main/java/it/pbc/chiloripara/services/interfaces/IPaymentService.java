package it.pbc.chiloripara.services.interfaces;

import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface IPaymentService {

	/*********************************************************************************
	 * SetExpressCheckout: Function to perform the SetExpressCheckout API call
	 * 
	 * Inputs: paymentAmount: Total value of the purchase currencyCodeType:
	 * Currency code value the PayPal API paymentType: 'Sale' for Digital Goods
	 * returnURL: the page where buyers return to after they are done with the
	 * payment review on PayPal cancelURL: the page where buyers return to when
	 * they cancel the payment review on PayPal
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	public abstract HashMap setExpressCheckout(String paymentAmount, String returnURL, String cancelURL, Map item);

	public abstract HashMap checkoutCetegorie(Artigiano art, List<Categoria> categorie, String returnURL, String cancelURL);

	/*********************************************************************************
	 * GetShippingDetails: Function to perform the GetExpressCheckoutDetails API
	 * call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	public abstract HashMap getPaymentDetails(String token);

	/*********************************************************************************
	 * ConfirmPayment: Function to perform the DoExpressCheckoutPayment API call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	public abstract HashMap confirmPayment(String token, String payerID, String finalPaymentAmount, String serverName, Map item);

	/*********************************************************************************
	 * httpcall: Function to perform the API call to PayPal using API signature @
	 * methodName is name of API method. @ nvpStr is nvp string. returns a NVP
	 * string containing the response from the server.
	 *********************************************************************************/
	public abstract HashMap httpcall(String methodName, String nvpStr);

	/*********************************************************************************
	 * deformatNVP: Function to break the NVP string into a HashMap pPayLoad is
	 * the NVP string. returns a HashMap object containing all the name value
	 * pairs of the string.
	 *********************************************************************************/

	public abstract HashMap deformatNVP(String pPayload);

	/*********************************************************************************
	 * RedirectURL: Function to redirect the user to the PayPal site token is
	 * the parameter that was returned by PayPal returns a HashMap object
	 * containing all the name value pairs of the string.
	 *********************************************************************************/
	public abstract void RedirectURL(HttpServletResponse response, String token);

}