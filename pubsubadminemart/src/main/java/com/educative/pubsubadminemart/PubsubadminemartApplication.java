package com.educative.pubsubadminemart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.cloud.spring.pubsub.PubSubAdmin;

@SpringBootApplication
public class PubsubadminemartApplication implements CommandLineRunner  {

	@Autowired
	private PubSubAdmin pubSubAdmin;
	
	public static void main(String[] args) {
		SpringApplication.run(PubsubadminemartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/* Create topics and subscriptions starts */
		
		// Use case #1 - Order service publishes messages to the Packaging service and Notification service
		createNewTopic("order");
		createSubscription("order-packaging", "order");
		createSubscription("order-notification", "order");
		
		// Use case #2 - Package service publishes messages to the Shipping service and Notification service
		createNewTopic("package");
		createSubscription("package-shipping", "package");
		createSubscription("package-notification", "package");
		
		// Use case #3 - Shipping service publishes messages to the Notification service
		createNewTopic("shipping");
		createSubscription("shipping-notification", "shipping");
		
		/* Create topics and subscriptions ends */
		
		/* Delete topics and subscriptions starts */
		
		// Use case #3 - Shipping service publishes messages to the Notification service
		deleteSubscription("shipping-notification");
		deleteTopic("shipping");
		
		// Use case #2 - Package service publishes messages to the Shipping service and Notification service
		deleteSubscription("package-notification");
		deleteSubscription("package-shipping");
		deleteTopic("package");
		
		// Use case #1 - Order service publishes messages to the Packaging service and Notification service
		deleteSubscription("order-notification");
		deleteSubscription("order-packaging");
		deleteTopic("order");
	
		/*Delete topics and subscriptions ends */
		
	}
	
	private void createNewTopic(String topicName) {
		System.out.println("Creating a new topic: ["+topicName+"]");
		pubSubAdmin.createTopic(topicName);
		System.out.println("Topic ["+topicName+"] created successfully");
	}

	private void createSubscription(String subscriptionName, String topicName) {
		System.out.println("Creating a new subscription: ["+subscriptionName+"] for the topic: ["+topicName+"]");
		pubSubAdmin.createSubscription(subscriptionName, topicName);
		System.out.println("Subscription ["+subscriptionName+"] created successfully");
	}
	
	private void deleteSubscription(String subscriptionName) {
		System.out.println("Deleting subscription: ["+subscriptionName+"]");
		pubSubAdmin.deleteSubscription(subscriptionName);
		System.out.println("Subscription ["+subscriptionName+"] deleted successfully");
	}
	
	private void deleteTopic(String topicName) {
		System.out.println("Deleting topic: ["+topicName+"]");
		pubSubAdmin.deleteTopic(topicName);
		System.out.println("Deleted topic: ["+topicName+"]");
	}
}
