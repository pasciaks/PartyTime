package com.pasciak.partytime.controllers;

import static spark.Spark.port;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pasciak.partytime.services.AuthService;
import com.pasciak.partytime.services.EmailService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
//@RequestMapping("api")
public class SiteController {

	private AuthService authService;
	private EmailService emailService;

	@Value("${stripe.test-key}")
	private String stripeTestKey;

	@Value("${stripe.domain-config}")
	private String stripeDomainConfig;

	@Value("${stripe.webhook-signing-secret}")
	private String stripeWebhookSigningSecret;

	@PostMapping("/webhook")
	public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader, HttpServletResponse response,
			HttpServletResponse request) {
		Event event = null;

		try {
			event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSigningSecret);
		} catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("");
		}

		System.out
				.println(event.getId() + " " + event.getType() + " " + event.getLivemode() + " " + event.getCreated());
		System.out.println(event.toString());

		// Handle the event
		switch (event.getType()) {
		case "payment_intent.succeeded":
			// Then define and call a method to handle the successful payment intent.
			// handlePaymentIntentSucceeded(event);
			System.out.println("Payment succeeded!");
			break;
		case "payment_method.attached":
			// Then define and call a method to handle the successful attachment of a
			// PaymentMethod.
			// handlePaymentMethodAttached(event);
			System.out.println("Payment method attached!");
			break;
		// ... handle other event types
		default:
			System.out.println("Unhandled event type: " + event.getType());
			break;
		}

		return ResponseEntity.ok("");
	}

	@PostMapping("/create-checkout-session/{amount}")
	public void payment(@PathVariable("amount") long amount, Principal principal, HttpServletResponse request,
			HttpServletResponse response) throws StripeException, IOException {

		port(8088);

		// This is your test secret API key.
		Stripe.apiKey = stripeTestKey;

//		PriceCreateParams params2 = PriceCreateParams.builder().setCurrency("usd").setUnitAmount(10000L)
//				.setProductData(PriceCreateParams.ProductData.builder().setName("Gold Plan One Time Payment").build())
//				.build();

		PriceCreateParams params2 = PriceCreateParams.builder().setCurrency("usd").setUnitAmount(amount)
				.setProductData(PriceCreateParams.ProductData.builder().setName("Gold Plan One Time Payment").build())
				.build();

		Price price = Price.create(params2);

		// staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());

		String YOUR_DOMAIN = stripeDomainConfig;
		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl(YOUR_DOMAIN + "/success.html").setCancelUrl(YOUR_DOMAIN + "/cancel.html")
				.addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L).setPrice(price.getId()).build())
				.build();
		Session session = Session.create(params);

		// redirect to the checkout page
		response.sendRedirect(session.getUrl());

	}

	public SiteController(AuthService authService, EmailService emailService) {
		super();
		this.authService = authService;
		this.emailService = emailService;
	}

	@GetMapping("api/geo/{lat}/{lng}") // api/geo/lat/lng
	public String authenticate(@PathVariable("lat") String lat, @PathVariable("lng") String lng, Principal principal,
			HttpServletResponse res) {
		if (principal == null) { // no Authorization header sent
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 - SC_UNAUTHORIZED
			res.setHeader("WWW-Authenticate", "Basic");
			return "Unauthorized";
		}
		System.out.println(principal.getName());
		res.setStatus(HttpServletResponse.SC_OK);
		System.out.println("lat: " + lat + " lng: " + lng);
		return "lat: " + lat + " lng: " + lng;
	}
}
