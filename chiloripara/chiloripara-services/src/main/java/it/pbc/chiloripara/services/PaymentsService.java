package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.interfaces.IPaymentService;
import it.pbc.chiloripara.web.model.entities.Artigiano;
import it.pbc.chiloripara.web.model.entities.Categoria;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentsService implements IPaymentService {

	private String gv_APIUserName;
	private String gv_APIPassword;
	private String gv_APISignature;

	private String gv_APIEndpoint;
	private String gv_BNCode;

	private String gv_Version;
	private String gv_nvpHeader;
	private String gv_ProxyServer;
	private String gv_ProxyServerPort;
	private int gv_Proxy;
	private boolean gv_UseProxy;
	private String PAYPAL_URL;

	public PaymentsService() {// lhuynh - Actions to be Done on init of this
								// class

		gv_APIUserName = "chiloripara-facilitator_api1.gmail.com";
		gv_APIPassword = "3NEAE75WHNNGBGJX";
		gv_APISignature = "AFcWxV21C7fd0v3bYYYRCpSSRl31Ayd8YVacPSFUmJgJdXS-5WbSnyKG";

		boolean bSandbox = true;

		/*
		 * Servers for NVP API Sandbox: https://api-3t.sandbox.paypal.com/nvp
		 * Live: https://api-3t.paypal.com/nvp
		 */

		/*
		 * Redirect URLs for PayPal Login Screen Sandbox:
		 * https://www.sandbox.paypal
		 * .com/webscr&cmd=_express-checkout&token=XXXX Live:
		 * https://www.paypal.
		 * com/cgi-bin/webscr?cmd=_express-checkout&token=XXXX
		 */
		String PAYPAL_DG_URL = null;
		if (bSandbox == true) {
			gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
			PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
			PAYPAL_DG_URL = "https://www.sandbox.paypal.com/incontext?token=";
		} else {
			gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
			PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
			PAYPAL_DG_URL = "https://www.paypal.com/incontext?token=";
		}

		String HTTPREQUEST_PROXYSETTING_SERVER = "proxy.mib-is.org";
		String HTTPREQUEST_PROXYSETTING_PORT = "8080";
		boolean USE_PROXY = true;

		gv_Version = "119";

		// WinObjHttp Request proxy settings.
		gv_ProxyServer = HTTPREQUEST_PROXYSETTING_SERVER;
		gv_ProxyServerPort = HTTPREQUEST_PROXYSETTING_PORT;
		gv_Proxy = 2; // 'setting for proxy activation
		gv_UseProxy = USE_PROXY;

	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#setExpressCheckout(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public HashMap setExpressCheckout(String paymentAmount, String returnURL, String cancelURL, Map item) {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */

		String currencyCodeType = "EUR";
		String paymentType = "Sale";

		/*
		 * Construct the parameter string that describes the PayPal payment the
		 * varialbes were set in the web form, and the resulting string is
		 * stored in $nvpstr
		 */
		String nvpstr = "&PAYMENTREQUEST_0_AMT=" + paymentAmount + "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType + "&RETURNURL=" + URLEncoder.encode(returnURL) + "&CANCELURL=" + URLEncoder.encode(cancelURL) + "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType + "&REQCONFIRMSHIPPING=0"
				+ "&NOSHIPPING=1" + "&L_PAYMENTREQUEST_0_NAME0=" + item.get("name") + "&L_PAYMENTREQUEST_0_AMT0=" + item.get("amt") + "&L_PAYMENTREQUEST_0_QTY0=" + item.get("qty") + "&L_PAYMENTREQUEST_0_ITEMCATEGORY0=Digital";

		/*
		 * Make the call to PayPal to get the Express Checkout token If the API
		 * call succeded, then redirect the buyer to PayPal to begin to
		 * authorize payment. If an error occured, show the resulting errors
		 */
		logger.debug("NVP " + nvpstr);
		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);

		// String strAck = nvp.get("ACK").toString();
		// if (strAck != null && strAck.equalsIgnoreCase("Success")) {
		//
		// return nvp;
		// }
		//
		// return null;
		return nvp;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#checkoutCetegorie(it.pbc.chiloripara.web.model.entities.Artigiano, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap checkoutCetegorie(Artigiano art,List<Categoria> categorie, String returnURL, String cancelURL) {

		String currencyCodeType = "EUR";
		String paymentType = "Sale";
		BigDecimal vat= new BigDecimal("0.22");
		BigDecimal prezzoPrima= new BigDecimal("200");
		BigDecimal prezzoAltre= new BigDecimal("20");

		
		String nvpstr = this.generateNpvString(currencyCodeType, returnURL, cancelURL, paymentType, art, categorie, prezzoPrima, prezzoAltre, vat);
		logger.debug("NVP " + nvpstr);
		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);

		return nvp;
	}

	private String generateNpvString(String currency, String returnUrl,String cancelUrl,String paymentType,Artigiano art,List<Categoria> categorie, BigDecimal prezzoPrima, BigDecimal prezzoAltre, BigDecimal vatRate) {
		StringBuffer items = new StringBuffer();
		BigDecimal totalItemAmount = new BigDecimal(0);
		BigDecimal totalVat	=	new BigDecimal(0);
		for (int i = 0; i < categorie.size(); i++) {
			
			Categoria cat = categorie.get(i);
			items.append("&L_PAYMENTREQUEST_0_NAME" + i + "=Categoria: " + cat.getDescrizione());
			items.append("&L_PAYMENTREQUEST_0_DESC" + i + "=Abbonamento annuale alla categoria: " + cat.getDescrizione());
			
			if (i == 0) {
				items.append("&L_PAYMENTREQUEST_0_AMT" + i + "=" + prezzoPrima.setScale(2, BigDecimal.ROUND_UP).toString());
				totalItemAmount=totalItemAmount.add(prezzoPrima);
				items.append("&L_PAYMENTREQUEST_0_TAXAMT" + i + "=" + prezzoPrima.multiply(vatRate).setScale(2, BigDecimal.ROUND_UP).toString());
				totalVat=totalVat.add(prezzoPrima.multiply(vatRate).setScale(2, BigDecimal.ROUND_UP));
			} else {
				items.append("&L_PAYMENTREQUEST_0_AMT" + i + "=" + prezzoAltre.setScale(2, BigDecimal.ROUND_UP).toString());
				totalItemAmount=totalItemAmount.add(prezzoAltre);
				items.append("&L_PAYMENTREQUEST_0_TAXAMT" + i + "=" + prezzoAltre.multiply(vatRate).setScale(2, BigDecimal.ROUND_UP).toString());
				totalVat=totalVat.add(prezzoAltre.multiply(vatRate).setScale(2, BigDecimal.ROUND_UP));
			}
			items.append("&L_PAYMENTREQUEST_0_QTY" + i + "=1");
			items.append("&L_PAYMENTREQUEST_0_ITEMCATEGORY" + i + "=Digital");
		}
		StringBuffer paymentRequest = new StringBuffer();
		
		paymentRequest.append("&PAYMENTREQUEST_0_AMT="+totalItemAmount.add(totalVat).setScale(2, BigDecimal.ROUND_UP).toString());
		
		paymentRequest.append("&PAYMENTREQUEST_0_PAYMENTACTION="+paymentType);
		paymentRequest.append("&RETURNURL="+URLEncoder.encode(returnUrl));
		paymentRequest.append("&CANCELURL="+URLEncoder.encode(cancelUrl));
		paymentRequest.append("&PAYMENTREQUEST_0_CURRENCYCODE="+currency);
		paymentRequest.append("&REQCONFIRMSHIPPING=0");
		paymentRequest.append("&NOSHIPPING=1");
		paymentRequest.append("&LOCALECODE=IT");
		paymentRequest.append("&CHANNELTYPE=Merchant");
		paymentRequest.append("&PAYMENTREQUEST_0_DESC=Abbonamento annuale al portale ChiLoRipara.it");
		paymentRequest.append("&PAYMENTREQUEST_0_TAXAMT="+totalVat.setScale(2, BigDecimal.ROUND_UP).toString());
		paymentRequest.append("&PAYMENTREQUEST_0_ITEMAMT="+totalItemAmount.setScale(2, BigDecimal.ROUND_UP).toString());
		paymentRequest.append("&L_BILLINGAGREEMENTDESCRIPTION0=Abbonamento annuale al portale ChiLoRipara.it");
	
		
		paymentRequest.append(items.toString());
		
		
		
		return paymentRequest.toString();
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#getPaymentDetails(java.lang.String)
	 */
	@Override
	public HashMap getPaymentDetails(String token) {
		/*
		 * Build a second API request to PayPal, using the token as the ID to
		 * get the details on the payment authorization
		 */

		String nvpstr = "&TOKEN=" + token;
		// String nvpstr = "&TRANSACTIONID=" + token;

		/*
		 * Make the API call and store the results in an array. If the call was
		 * a success, show the authorization details, and provide an action to
		 * complete the payment. If failed, show the error
		 */

		HashMap nvp = httpcall("GetExpressCheckoutDetails", nvpstr);
		// HashMap nvp = httpcall("GetTransactionDetails", nvpstr);
		String strAck = nvp.get("ACK").toString();

		if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {

			return nvp;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#confirmPayment(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public HashMap confirmPayment(String token, String payerID, String finalPaymentAmount, String serverName, Map item) {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */
		String currencyCodeType = "EUR";
		String paymentType = "Sale";

		/*
		 * '----------------------------------------------------------------------------
		 * '---- Use the values stored in the session from the previous SetEC
		 * call
		 * '----------------------------------------------------------------------------
		 */
		String nvpstr = "&TOKEN=" + token + "&PAYERID=" + payerID + "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType + "&PAYMENTREQUEST_0_AMT=" + finalPaymentAmount;

		nvpstr = nvpstr + "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType + "&IPADDRESS=" + serverName;

		nvpstr = nvpstr + "&L_PAYMENTREQUEST_0_NAME0=" + item.get("name") + "&L_PAYMENTREQUEST_0_AMT0=" + item.get("amt") + "&L_PAYMENTREQUEST_0_QTY0=" + item.get("qty") + "&L_PAYMENTREQUEST_0_ITEMCATEGORY0=Digital";

		/*
		 * Make the call to PayPal to finalize payment If an error occured, show
		 * the resulting errors
		 */
		HashMap nvp = httpcall("DoExpressCheckoutPayment", nvpstr);
		String strAck = nvp.get("ACK").toString();
		if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
			return nvp;
		}
		return null;

	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#httpcall(java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap httpcall(String methodName, String nvpStr) {

		String version = "2.3";
		String agent = "Mozilla/4.0";
		String respText = "";
		HashMap nvp = null; // lhuynh not used?

		// deformatNVP( nvpStr );
		String encodedData = "METHOD=" + methodName + "&VERSION=" + gv_Version + "&PWD=" + gv_APIPassword + "&USER=" + gv_APIUserName + "&SIGNATURE=" + gv_APISignature + nvpStr;

		try {
			URL postURL = new URL(gv_APIEndpoint);

			HttpURLConnection conn = null;
			if (!gv_UseProxy)
				conn = (HttpURLConnection) postURL.openConnection();
			else {
				logger.debug("SETTO PROXY");
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(gv_ProxyServer, Integer.parseInt(gv_ProxyServerPort)));
				conn = (HttpURLConnection) postURL.openConnection(proxy);
			}

			// Set connection parameters. We need to perform input and output,
			// so set both as true.
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// Set the content type we are POSTing. We impersonate it as
			// encoded form data
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent", agent);

			// conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
			conn.setRequestMethod("POST");

			logger.debug("DATA: " + encodedData);
			conn.setReadTimeout(300000);
			conn.setConnectTimeout(300000);
			logger.debug("timeout impostati");
			logger.debug("user proxy " + conn.usingProxy());

			// get the output stream to POST to.
			DataOutputStream output = new DataOutputStream(conn.getOutputStream());
			output.writeBytes(encodedData);
			output.flush();
			output.close();

			// Read input from the input stream.
			DataInputStream in = new DataInputStream(conn.getInputStream());
			int rc = conn.getResponseCode();
			if (rc != -1) {
				BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String _line = null;
				while (((_line = is.readLine()) != null)) {
					respText = respText + _line;
				}
				nvp = deformatNVP(respText);
			}
			logger.debug("NVP: " + nvp);
			return nvp;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#deformatNVP(java.lang.String)
	 */

	@Override
	public HashMap deformatNVP(String pPayload) {
		HashMap nvp = new HashMap();
		StringTokenizer stTok = new StringTokenizer(pPayload, "&");
		while (stTok.hasMoreTokens()) {
			StringTokenizer stInternalTokenizer = new StringTokenizer(stTok.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2) {
				String key = URLDecoder.decode(stInternalTokenizer.nextToken());

				String value = URLDecoder.decode(stInternalTokenizer.nextToken());
				nvp.put(key.toUpperCase(), value);
			}
		}
		return nvp;
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IPaymentService#RedirectURL(javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	public void RedirectURL(HttpServletResponse response, String token) {
		String payPalURL = PAYPAL_URL + token;

		// response.sendRedirect( payPalURL );
		response.setStatus(302);
		response.setHeader("Location", payPalURL);
		response.setHeader("Connection", "close");
	}

	// end class
}
