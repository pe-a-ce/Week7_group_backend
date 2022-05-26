# Back End E-Commerce Car Shop
## “Zoom?”

## Our Group Members
- [Haining Harry Zhen]( https://github.com/hainingzhen )
- [Peace Akib]( https://github.com/pe-a-ce )  
- [Katya Grenier](https://github.com/katyagr ) 
- [Edward Todd]( https://github.com/edward1432 ) 

## Our Project Description

### What our application does

Our application manages the database for products and stock held by an online car dealership. The front-end UI can view and manage the database, which includes tables for registered customers, purchases and cars stocked by the seller. 

![image]( https://user-images.githubusercontent.com/99202770/170467976-f10bc211-23e7-4ae4-85da-cd17e0412c94.jpeg )

### Customers

Customers are able to create an account, which stores their personal information and log in with their username and password. With this, they can purchase cars, review their purchase history and see how much credit is in their wallet. Customers are also able to delete their accounts or update any of their personal information if needed. Customers are also able to delete their accounts or update any of their personal information if needed. 


### Purchases

In purchases, we are able to view purchases made by the customers, either by the customer or purchase ID. Cars can be added to a customer's basket before purchasing and
when a customer purchases all the items in their basket, it will update their inventory and also update the stock quantity. Customers can keep track of when they made their purchases and how much they paid. If a customer tries to purchase an item that they either do not have sufficient funds for or there is not enough stock, they will be notified.

### Products
	
Product controller contains two distinct methods for searching for available products. The first, getProducts(), allows the front end to filter the database of cars by any combination of five properties. The second, searchAllProducts(), acts as a search engine for the table, allowing the user to search for a specific manufacturer and model within one search field. The method will then call another method in the ProductService class which splits up the string and searches the database for each specific word.

### Stock

Stock objects have a one-to-one mapping with Product objects. This allows the retailer to track the quantity of each product they sell while keeping this information more removed from the actual attributes of the product such as its manufacturer or price. Stock objects are automatically instantiated with creation of new products but can be deleted if the retailer no longer wants to sell the product. This allows for the product itself to remain in the database so records of previous purchases are not deleted. New stock objects can only be created for products which do not currently have a stock object.
 
## Technologies we used

In order to create this project, we used Java with the SpringBoot framework. We used JPA repositories with PSQL to manage our databases. 

## Challenges we faced throughout the project
[insert]

## Potential implementations for the future
- Add user authentication to ensure that only the customer with the correct username and password can access their account
- When a user is logged in, they are restricted to the functions they can access (i.e a customer should not need to be able to update any of the products in the store)
- Create a  Basket table that is separate from the Purchase table
![image]( https://user-images.githubusercontent.com/99202770/170468083-cf68368a-591f-4e2e-bd39-27ee0f471831.jpeg )


## How to install and run the project 

In order to run this project, you will need to clone the files and ensure you are working with Java version 18. You will also need to ensure that your application properties are updated to allow for either a Postman or H2 connection.

