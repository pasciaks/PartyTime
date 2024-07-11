package com.pasciak.partytime.controllers;

import static spark.Spark.port;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasciak.partytime.entities.User;
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

	private final ObjectMapper objectMapper = new ObjectMapper();

	private AuthService authService;
	private EmailService emailService;

	@Value("${stripe.test-key}")
	private String stripeTestKey;

	@Value("${stripe.domain-config}")
	private String stripeDomainConfig;

	@Value("${stripe.webhook-signing-secret}")
	private String stripeWebhookSigningSecret;

	@PostMapping("webhook")
	public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader, HttpServletResponse response,
			HttpServletResponse request) {

		System.out.println("WEBHOOK received...");

		// System.out.println(payload);

//		<com.stripe.model.Event@1434758675 id=evt_1PamokHmHfK4ejpsDhcS1c8m> JSON: {
//			  "account": null,
//			  "api_version": "2024-06-20",
//			  "created": 1720565245,
//			  "data": {
//			    "previous_attributes": null,
//			    "object": {
//			      "id": "cs_test_a1ebl0NjhwSxLiqpwI9hPE4zWKXfZqAGVCcdM77RA3hWRIoCSL0nsB1WPh",
//			      "object": "checkout.session",
//			      "after_expiration": null,
//			      "allow_promotion_codes": null,
//			      "amount_subtotal": 1500,
//			      "amount_total": 1500,
//			      "automatic_tax": {
//			        "enabled": false,
//			        "liability": null,
//			        "status": null
//			      },
//			      "billing_address_collection": null,
//			      "cancel_url": "http://localhost:8088/cancel.html",
//			      "client_reference_id": null,
//			      "client_secret": null,
//			      "consent": null,
//			      "consent_collection": null,
//			      "created": 1720565223,
//			      "currency": "usd",
//			      "currency_conversion": null,
//			      "custom_fields": [],
//			      "custom_text": {
//			        "after_submit": null,
//			        "shipping_address": null,
//			        "submit": null,
//			        "terms_of_service_acceptance": null
//			      },
//			      "customer": null,
//			      "customer_creation": "if_required",
//			      "customer_details": {
//			        "address": {
//			          "city": null,
//			          "country": "US",
//			          "line1": null,
//			          "line2": null,
//			          "postal_code": "84129",
//			          "state": null
//			        },
//			        "email": "test@test.com",
//			        "name": "test test",
//			        "phone": null,
//			        "tax_exempt": "none",
//			        "tax_ids": []
//			      },
//			      "customer_email": null,
//			      "expires_at": 1720651622,
//			      "invoice": null,
//			      "invoice_creation": {
//			        "enabled": false,
//			        "invoice_data": {
//			          "account_tax_ids": null,
//			          "custom_fields": null,
//			          "description": null,
//			          "footer": null,
//			          "issuer": null,
//			          "metadata": {},
//			          "rendering_options": null
//			        }
//			      },
//			      "livemode": false,
//			      "locale": null,
//			      "metadata": {},
//			      "mode": "payment",
//			      "payment_intent": "pi_3PamoiHmHfK4ejps1g4ECBsD",
//			      "payment_link": null,
//			      "payment_method_collection": "if_required",
//			      "payment_method_configuration_details": null,
//			      "payment_method_options": {
//			        "card": {
//			          "request_three_d_secure": "automatic"
//			        }
//			      },
//			      "payment_method_types": [
//			        "card"
//			      ],
//			      "payment_status": "paid",
//			      "phone_number_collection": {
//			        "enabled": false
//			      },
//			      "recovered_from": null,
//			      "saved_payment_method_options": null,
//			      "setup_intent": null,
//			      "shipping_address_collection": null,
//			      "shipping_cost": null,
//			      "shipping_details": null,
//			      "shipping_options": [],
//			      "status": "complete",
//			      "submit_type": null,
//			      "subscription": null,
//			      "success_url": "http://localhost:8088/success.html",
//			      "total_details": {
//			        "amount_discount": 0,
//			        "amount_shipping": 0,
//			        "amount_tax": 0
//			      },
//			      "ui_mode": "hosted",
//			      "url": null
//			    }
//			  },
//			  "id": "evt_1PamokHmHfK4ejpsDhcS1c8m",
//			  "livemode": false,
//			  "object": "event",
//			  "pending_webhooks": 2,
//			  "request": {
//			    "id": null,
//			    "idempotency_key": null
//			  },
//			  "type": "checkout.session.completed"
//			}

		Event event = null;

		try {
			event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSigningSecret);
			// System.out.println("----------------");
			// System.out.println("Event constructed successfully");
			// System.out.println("----------------");
			// System.out.println(event.toString());
			// System.out.println(event.getData().toString());

			// Manually parse the event payload
			JsonNode jsonNode;
			try {
				jsonNode = objectMapper.readTree(payload);
			} catch (IOException e) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid payload");
			}

			if ("checkout.session.completed".equals(event.getType())) {
				handleCheckoutSessionCompleted(jsonNode);
			} else {
				System.out.println("Unhandled event type: " + event.getType());
			}

		} catch (Exception e) {
			System.out.println("----------------");
			System.out.println("Error caught here in .constructEvent");
			System.out.println("----------------");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
					.body("Error, could not construct event. Maybe the stripe Webhook signing secret is incorrect.");
		}

		// System.out.println(event.toString());

		// Handle the event
		switch (event.getType()) {
		case "payment_intent.succeeded":
			// System.out.println("Payment succeeded!");
			break;
		case "payment_method.attached":
			// System.out.println("Payment method attached!");
			break;
		case "charge.succeeded":
			// System.out.println("Charge succeeded!");
			break;
		case "checkout.session.completed":
			// System.out.println("Checkout session completed!");
			break;
		default:
			// System.out.println("Failed to handled event type: " + event.getType());
			break;
		}

		response.setStatus(HttpServletResponse.SC_OK);

		return response.isCommitted() ? ResponseEntity.ok("Event processed") : ResponseEntity.ok("Event processed");

	}

	private void handleCheckoutSessionCompleted(JsonNode jsonNode) {
		JsonNode sessionNode = jsonNode.get("data").get("object");

		String sessionId = sessionNode.get("id").asText();
		int amountTotal = sessionNode.get("amount_total").asInt();
		String customerEmail = sessionNode.get("customer_details").get("email").asText();

		System.out.println("Session ID: " + sessionId);
		System.out.println("Amount Total: " + amountTotal);
		System.out.println("Customer Email: " + customerEmail);

		// Additional processing as needed

		User user = authService.getUserByUsername(customerEmail);

		if (user != null) {
			BigDecimal amount = new BigDecimal(amountTotal).divide(new BigDecimal(100));
			String formattedAmount = String.format("%.2f", amount);
			System.out.println(user.getUsername() + " " + "Payment Confirmation" + "Thank you for your payment of $"
					+ formattedAmount + "!");
			emailService.sendEmail(user.getUsername(), "Payment Confirmation",
					"Thank you for your payment of $" + formattedAmount + "!");
		} else {
			System.out.println("User not found: " + customerEmail);
		}

	}

	@PostMapping("create-checkout-session/{amount}")
	public void payment(@PathVariable("amount") long amount, Principal principal, HttpServletResponse request,
			HttpServletResponse response) throws StripeException, IOException {

		port(8088);

		Stripe.apiKey = stripeTestKey;

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
